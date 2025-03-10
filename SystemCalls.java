// This interface will have all the system calls Change the return type if needed
public interface SystemCalls 
{
    public void createProcess(PCB pcb); // create Process
    public void killProcess(PCB pcb); // Kill Process
    public void malloc(); // This function will allocate memory to the process  if process have 2048 memory and 1024 is already allocated then it will allocate the remaining 1024
    public void free(); // This function will free the memory allocated to the process
    public void read(); // This function will read from the file
    public void load(); // This function will load the process to the memory
    public void execute(); // This function will execute the process
}
