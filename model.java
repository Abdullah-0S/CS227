import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
public class model implements SystemCalls
{
    Queue<PCB> readyQueue = new LinkedList<PCB>();
    Queue<PCB> JobQueue = new LinkedList<PCB>();
    performance p1 ,p2 ,p3;


    private int currentmemory = 2048;
    private final int MaxMemory;

    public model()
    {
        this.MaxMemory = currentmemory;
    }
    public model(int Memory)
    {
        currentmemory = Memory;
        MaxMemory = Memory;
    }


    public void createProcess(PCB pcb) // create Process and add it to the Job Queue
    {
        if (pcb == null )
            return;
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

        
        
        if(!Unique) return Unique;

        for (int i = 0; i < JobQueue.size(); i++)
        {
            e = JobQueue.poll();
            if (e == null)
                continue;
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
             System.out.println("No Process found with id "+pid); 
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
        if (this.JobQueue.isEmpty())
        {
           // System.out.println("empty");
            //System.out.println("Job Queue is empty");
            try {
                Thread.currentThread().sleep(100);                
            } catch (Exception e) {

            }
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
        // System.out.println("Memory allocated to process with PID: " + pcb.getId()+ " Memory left: " + currentmemory);
        return 0;
    } 

    public void read(String filePath) throws IOException
    {
        List<PCB> process= readFile.read_returnPcbs(filePath);
        for (PCB pcb : process) {
            if (pcb != null)
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
        // if (readyQueue.isEmpty())
        // {
        //     System.out.println("ready queue is empty");
        //     return;
        // }
        // if (readyQueue.size() < numOfProccess)
        // {
        //     System.out.println("Not enough processes in the ready queue , only " + readyQueue.size() + " processes are available");
        //     return;
        // }
        // if (algo == Algorithm.All)
        // {
        //     numOfProccess = readyQueue.size();
        // }

        // switch (algo) 
        // {
        //     case FCFS:
        //         // FCFS fcfs = new FCFS(this);
        //         //p1 = new performance(Algorithm.FCFS,fcfs.schedule(readyQueue , numOfProccess));
        //         System.out.println(p1);  
        //     break;
        //     case Round_Robin:
        //         System.out.println("Enter the quantum time: ");
        //         System.out.print("-->");
        //         Scanner s = new Scanner(System.in);
        //         int quantum = s.nextInt();
        //         // p2 = new performance(Algorithm.Round_Robin, RR.RRsechdual(readyQueue, numOfProccess, quantum));
        //         System.out.println(p2);
        //         break;

        //     case Priority:
        //         Priorty priorty = new Priorty(this);
        //         //p3 = new performance(Algorithm.Priority, priorty.PQ(readyQueue, numOfProccess));
        //         System.out.println(p3);
        //     break;

        //     case All:
        //     // asking user to get the quantum for RR algo
        //     System.out.println("Enter the quantum time: ");
        //     System.out.print("-->");
        //     Scanner s1 = new Scanner(System.in);
        //     int quantum1 = s1.nextInt();

        //     numOfProccess = readyQueue.size();

        //     // we used copy ready queue here becuase i dont want the original ready queue being altered
        //     // p2 = new performance(Algorithm.Round_Robin, RR.RRsechdualWithoutFree(copy(readyQueue),numOfProccess, quantum1));
        //     System.out.println(p2);
            
        //     FCFS fcfs1 = new FCFS(this);
        //     // we used copy ready queue here becuase i dont want the original ready queue being altered
        //     p1 = new performance(Algorithm.FCFS, fcfs1.scheduleWithoutFree(copy(readyQueue),numOfProccess ));
        //     System.out.println(p1);  
        
            
        //     // We used normal ready queue here because after executing this method readyqueue will be empty 
        //     Priorty priorty1 = new Priorty(this);
        //     //p3 = new performance(Algorithm.Priority, priorty1.PQ(readyQueue, numOfProccess));
        //     System.out.println(p3);
        //     break;

        //     default:
        //         break;
        // }
  
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
        //System.out.println(best);
    }

    public void print_ReadyQueue()
    {
        System.out.println("Ready Queue: { ");
        for (PCB pcb : readyQueue)
        {
            System.out.print("PID: " + pcb.getId()+" ,");
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
    
    public void print_Memory()
    {
        System.out.println("Current Memory: " + currentmemory);
    }
    
   public void print_allQueue(){
        print_JobQueue();
        print_ReadyQueue();
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
   public int getMaxMemory()
   {
    return MaxMemory;
   }
   public void printMaxmemory()
   {
        System.out.println("Max Memory: " + MaxMemory);
   }
   public void displayProcessInfo(int pid)
   {
        for (PCB pcb : JobQueue) 
        {
            if (pcb.getId() == pid) 
            {
                System.out.println("Process info with PID: " + pcb.getId());
                System.out.println(pcb);
                return;
            }
        }
        for (PCB pcb : readyQueue) 
        {
            if (pcb.getId() == pid) 
            {
                System.out.println("Process info with PID: " + pcb.getId());
                System.out.println(pcb);
                return;
            }
        }
        System.out.println("No Process found with id "+pid); 
   }

   public void displayFirstProcessInfo()
   {
        if (JobQueue.size() > 0 )
        {
            System.out.println("The First Process info in job queue is: ");
            System.out.println(JobQueue.peek());
        }
        else{
            System.out.println("Job Queue is Empty");
        }
        if(readyQueue.size() > 0)
        {
            System.out.println("The First Process info in ready queue is: ");
            System.out.println(readyQueue.peek());
        }
        else{
            System.out.println("Ready Queue is Empty");
        }
   }
    public boolean CanFill()
    {
        return (this.readyQueue.isEmpty()&& this.JobQueue.isEmpty());
    }
    public PCB pollReadyQueue(){
        PCB pcb = readyQueue.poll();
        if (pcb == null)
        {
            while (pcb == null) {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (Exception e) {
                    break;
                }   
                load();
                pcb = readyQueue.poll();   
            }
        }
        return pcb;   
    }
}

enum Algorithm
{
    FCFS, Round_Robin, Priority,All;
}