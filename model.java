import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
public class model implements SystemCalls
{
    Queue<PCB> readyQueue = new LinkedList<PCB>();
    PriorityQueue<PCB> priorityReadyQueue = new PriorityQueue<PCB>();
    Queue<PCB> JobQueue = new LinkedList<PCB>();
    Queue<PCB> RunningQueue = new LinkedList<PCB>();
    private int currentmemory = 2048;

    public void createProcess(PCB pcb) // create Process and add it to the Job Queue
    {
        pcb.setState(State.JobQueue);
        JobQueue.add(pcb);
        System.out.println("Process created with PID: " + pcb.getId());
    }
    public void killProcess(PCB pcb)
    {
        if (pcb.getState() == State.READY)
        {
            readyQueue.remove(pcb);
        }
        else if (pcb.getState() == State.JobQueue)
        {
            JobQueue.remove(pcb);
        }
        else if (pcb.getState() == State.RUNNING)
        {
            RunningQueue.remove(pcb);
        }
        else if (pcb.getState() == State.PriorityReadyQueue)
        {
            priorityReadyQueue.remove(pcb);
        }
        free(pcb);
        System.out.println("Process killed with PID: " + pcb.getId());
        pcb = null;
    }
    public void load() // load the process from the job queue to the ready queue
    {
        PCB pcb = JobQueue.peek();
        if (malloc(pcb) == -1) // if memory is full
        {
            return;
        }
        pcb = JobQueue.poll();
        pcb.setState(State.READY);
        readyQueue.add(pcb);
        System.out.println("Process loaded with PID: " + pcb.getId());
    }
    public void loadpq() // load the process from the job queue to the priorty ready queue
    {
        PCB pcb = JobQueue.peek();
        if (malloc(pcb) == -1) // if memory is full
        {
            return;
        }
        pcb.setState(State.PriorityReadyQueue);
        priorityReadyQueue.add(pcb);
        System.out.println("Process loaded with PID: " + pcb.getId());
    }
    public List<int[]> read(String filePath)
    {
        return readFile.read("job.txt");
    }
    public int malloc(PCB pcb)
    {
        if (currentmemory < pcb.getReqMemory())
        {
            System.out.println("Memory is full");
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
    public void execute(Algorithm algo)
    {
        PCB pcb = readyQueue.poll();
        pcb.setState(State.RUNNING);
        RunningQueue.add(pcb);
        switch (algo) 
        {
            case FCFS:
                
                break;
            case Round_Robin:
                break;
            case Priority:
                break;
            default:
                break;
        }
        RunningQueue.poll();
        pcb.setState(State.Terminated);
        free(pcb);
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
    public void print_PriorityReadyQueue()
    {
        System.out.println("Priority Queue: { ");
        for (PCB pcb : priorityReadyQueue)
        {
            System.out.println("PID: " + pcb.getId()+" ,");
        }
        System.out.print("}\n");
    }
    public void print_Memory()
    {
        System.out.println("Memory left: " + currentmemory);
    }
   public void print_allQueue(){
        print_JobQueue();
        print_ReadyQueue();
        print_RunningQueue();
        print_PriorityReadyQueue();
   } 
}
enum Algorithm
{
    FCFS, Round_Robin, Priority;
}