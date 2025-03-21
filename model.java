import java.io.IOError;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
public class model implements SystemCalls
{
    Queue<PCB> readyQueue = new LinkedList<PCB>();
    Queue<PCB> JobQueue = new LinkedList<PCB>();
    Queue<PCB> RunningQueue = new LinkedList<PCB>();

    private int currentmemory = 2048;
    private final int MaxMemory;

    public model()
    {
        this.MaxMemory = currentmemory;
    }

    public void createProcess(PCB pcb) // create Process and add it to the Job Queue
    {
        if (!UniqueID(pcb.getId()))
        {
            System.out.println("Process with PID: " + pcb.getId() + " already exists");
            return;
        }
        if (pcb.getId() < 0)
        {
            System.out.println("PID can't be negative");
            return;
        }
        if (pcb.getReqMemory() > MaxMemory)
        {
            System.out.println("Memory required by the process is greater than the available memory");
            return;
        }
         
        pcb.setState(State.JobQueue);
        JobQueue.add(pcb);
        System.out.println("Process created with PID: " + pcb.getId());
    }

    public boolean UniqueID(Integer ID)
    {
        boolean Unique = true;
        PCB e;
        for (int i = 0; i < readyQueue.size(); i++) 
        {
            e = readyQueue.poll();
            if (ID == e.getId()) 
                Unique = false;
            readyQueue.add(e);
        }

        if(!Unique) return Unique;

        for (int i = 0; i < RunningQueue.size(); i++) 
        {
            e = RunningQueue.poll();
            if (ID == e.getId()) 
                Unique = false;
                RunningQueue.add(e);
        }
        
        if(!Unique) return Unique;

        for (int i = 0; i < JobQueue.size(); i++)
        {
            e = JobQueue.poll();
            if (ID == e.getId()) 
                Unique = false;
            JobQueue.add(e);            
        }
        return Unique;
    }

    public void killProcess(int pid)
    {
        for (PCB pcb : JobQueue) 
        {
            if (pcb.getId() == pid) 
            {
                JobQueue.remove(pcb);
                free(pcb);
                System.out.println("Process killed with PID: " + pcb.getId());
                pcb = null;
                return;
            }
        }
        for (PCB pcb : readyQueue) 
        {
            if (pcb.getId() == pid) 
            {
                readyQueue.remove(pcb);
                free(pcb);
                System.out.println("Process killed with PID: " + pcb.getId());
                pcb = null;
                return;
            }
        }
               

        for (PCB pcb : RunningQueue) 
        {
            if (pcb.getId() == pid) 
            {
                RunningQueue.remove(pcb);
                free(pcb);
                System.out.println("Process killed with PID: " + pcb.getId());
                pcb = null;
                return;
            }
        }       
    }
    public void load() // load the process from the job queue to the ready queue
    {
        if (JobQueue.isEmpty())
        {
            System.out.println("Job Queue is empty");
            return;
        }
        PCB pcb = JobQueue.poll();
        if (malloc(pcb) == -1) // if memory is full
        {
            JobQueue.add(pcb); // add process back to try again later
            return;
        }
        pcb.setState(State.READY);
        readyQueue.add(pcb);
        System.out.println("Process loaded with PID: " + pcb.getId());
    }
    public void loadAll_Process()
    {
        for(int i = 0 ; i < JobQueue.size(); i++)
        {
            load();
        }

    }

    public void read(String filePath) throws IOException
    {
        List<PCB> list = readFile.read_returnPcbs(filePath);
        for (PCB pcb : list)
        {
            createProcess(pcb);
        }
    }
    public int malloc(PCB pcb)
    {
        if (currentmemory < pcb.getReqMemory())
        {
            System.out.println("Memory is full can't add process with PID: " + pcb.getId());
            return -1;
        }
        currentmemory -= pcb.getReqMemory();
        System.out.println("Memory allocated to process with PID: " + pcb.getId()+ " Memory left: " + currentmemory);
        return 0;
    }
    public void free(PCB pcb)
    {
        currentmemory += pcb.getReqMemory();
        System.out.println("Memory freed from process with PID: " + pcb.getId()+ " Memory left: " + currentmemory);
        pcb = null;
    }
    public void execute(Algorithm algo, int numOfProccess)
    {
        if (readyQueue.isEmpty())
        {
            System.out.println("ready queue is empty");
            return;
        }
        if (readyQueue.size() < numOfProccess)
        {
            System.out.println("Not enough processes in the ready queue , only " + readyQueue.size() + " processes are available");
            return;
        }
        //Adding running state also adding to the running queue
        PCB pcb = null;
        for (int i = 0; i < numOfProccess; i++) {
            pcb = readyQueue.poll();
            pcb.setState(State.RUNNING);
            RunningQueue.add(pcb);
        }   

        switch (algo) 
        {
            case FCFS:
                FCFS fcfs = new FCFS(this);
                fcfs.schedule(RunningQueue);  
            break;
            case Round_Robin:
                RR rr = new RR();
                System.out.println("Enter the quantum time: ");
                System.out.print("-->");
                Scanner s = new Scanner(System.in);
                int quantum = s.nextInt();
                rr.RRsechdual(RunningQueue, quantum);

                break;
            case Priority:
                Priorty priorty = new Priorty(this);
                priorty.PQ(RunningQueue);
            break;
            default:
                break;
        }
        //TODO need to make algorithm turn around time and waiting time
        
    }
    public void print_ReadyQueue()
    {
        System.out.println("Ready Queue: { ");
        for (PCB pcb : readyQueue)
        {
            System.out.println("PID: " + pcb.getId()+" ,");
        }
        System.out.print("}\n");
    }
    public void print_JobQueue()
    {
        System.out.println("Job Queue: { ");
        for (PCB pcb : JobQueue)
        {
            System.out.print("PID: " + pcb.getId()+" ,");
        }
        System.out.print("}\n");
    }
    public void print_RunningQueue()
    {
        System.out.println("Running Queue: { ");
        for (PCB pcb : RunningQueue)
        {
            System.out.println("PID: " + pcb.getId()+" ,");
        }
        System.out.print("}\n");
    }
    
    public void print_Memory()
    {
        System.out.println("Current Memory: " + currentmemory);
    }
   public void print_allQueue(){
        print_JobQueue();
        print_ReadyQueue();
        print_RunningQueue();
        
   } 
}
enum Algorithm
{
    FCFS, Round_Robin, Priority;
}