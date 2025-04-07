// This interface will have all the system calls Change the return type if needed

import java.io.IOException;
import java.util.List;

public interface SystemCalls 
{
    public void read(String filePath) throws IOException;
    public void load(); // load the process from the job queue to the ready queue
    public void loadAll_Process(); // load all the process from the job queue to the ready queue
    public void killProcess(int pid); // Kill Process
    public void print_JobQueue(); // This function will print the job queue
    public void print_ReadyQueue(); // This function will print the ready queue
    public void print_allQueue(); // This function will print all the queues
    public void print_Memory(); // This function will print the memory
    public void printMaxmemory(); // This function will print the max memory
    public void execute(Algorithm algo, int numOfProccess); // This function will execute the process
    public void print_bestPerformance(status s); // This function will print the best performance
    public void displayProcessInfo(int pid); // This function will print the given pid all info
    public void displayFirstProcessInfo(); // This function will print the first process in the job queue and ready queue


}
