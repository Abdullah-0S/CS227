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
    performance p1 ,p2 ,p3;


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
    public void loadWithoutPrinting() // load the process from the job queue to the ready queue
    {
        if (JobQueue.isEmpty())
        {
            //System.out.println("Job Queue is empty");
            return;
        }
        PCB pcb = JobQueue.poll();
        if (mallocWithoutPrinting(pcb) == -1) // if memory is full
        {
            JobQueue.add(pcb); // add process back to try again later
            return;
        }
        pcb.setState(State.READY);
        readyQueue.add(pcb);
        //System.out.println("Process loaded with PID: " + pcb.getId());
    }
    public void loadAll_ProcessWithoutPrinting()
    {
        for(int i = 0 ; i < JobQueue.size(); i++)
        {
            loadWithoutPrinting();
        }

    }
    public int mallocWithoutPrinting(PCB pcb)
    {
        if (currentmemory < pcb.getReqMemory())
        {
            //System.out.println("Memory is full can't add process with PID: " + pcb.getId());
            return -1;
        }
        currentmemory -= pcb.getReqMemory();
        //System.out.println("Memory allocated to process with PID: " + pcb.getId()+ " Memory left: " + currentmemory);
        return 0;
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
        if (algo == Algorithm.All)
        {
            numOfProccess = readyQueue.size();
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
                p1 = new performance(Algorithm.FCFS,fcfs.schedule(RunningQueue));
                System.out.println(p1);  
            break;
            case Round_Robin:
                System.out.println("Enter the quantum time: ");
                System.out.print("-->");
                Scanner s = new Scanner(System.in);
                int quantum = s.nextInt();
                p2 = new performance(Algorithm.Round_Robin, RR.RRsechdual(RunningQueue, quantum));
                System.out.println(p2);
                break;

            case Priority:
                Priorty priorty = new Priorty(this);
                p3 = new performance(Algorithm.Priority, priorty.PQ(RunningQueue));
                System.out.println(p3);
            break;

            case All:
            System.out.println("Enter the quantum time: ");
            System.out.print("-->");
            Scanner s1 = new Scanner(System.in);
            int quantum1 = s1.nextInt();
            p2 = new performance(Algorithm.Round_Robin, RR.RRsechdualWithoutFree(copy(RunningQueue), quantum1));
            System.out.println(p2);
            
            FCFS fcfs1 = new FCFS(this);
            p1 = new performance(Algorithm.FCFS, fcfs1.scheduleWithoutFree(copy(RunningQueue)));
            System.out.println(p1);  
        
            
        
            Priorty priorty1 = new Priorty(this);
            p3 = new performance(Algorithm.Priority, priorty1.PQWithoutFree(copy(RunningQueue)));
            System.out.println(p3);
            break;

            default:
                break;
        }
  
    }
    public void print_bestPerformance(status s)
    {
        if (p1 == null || p2 == null || p3 == null)
        {
            System.out.println("No performance data available , try executing all algorithms first");
            return;
        }
        performance best = new performance();
        best = best.BetterPerformanceAt(p1, p2, p3, s);
        System.out.println(best);
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

   public Queue<PCB> copy(Queue<PCB> q)
   {
       Queue<PCB> copy = new LinkedList<PCB>();
       for (PCB pcb : q)
       {
           copy.add(new PCB(pcb));
       }
       return copy;
   }
}
enum Algorithm
{
    FCFS, Round_Robin, Priority,All;
}