// This interface will have all the system calls Change the return type if needed

import java.util.List;

public interface SystemCalls 
{
    public void createProcess(PCB pcb); // create Process
    public void killProcess(PCB pcb); // Kill Process
    public int malloc(PCB pcb); // This function will allocate memory to the process  if process have 2048 memory and 1024 is already allocated then it will allocate the remaining 1024
    public void free(PCB pcb); // This function will free the memory allocated to the process
    public List<int[]> read(String filePath); // This function will read from the file
    public void load(); // load the process from the job queue to the ready queue
    public void loadpq(); // load the process from the job queue to the priorty ready queue
    public void execute(Algorithm algo); // This function will execute the process
    public void print_ReadyQueue(); // This function will print the ready queue
    public void print_JobQueue(); // This function will print the job queue
    public void print_RunningQueue(); // This function will print the running queue
    public void print_PriorityReadyQueue(); // This function will print the priority queue
    public void print_Memory(); // This function will print the memory
    public void print_allQueue(); // This function will print all the queues
}
