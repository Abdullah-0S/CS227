public class PCB implements Comparable<PCB> {
    private int id;
    private int burstTime; 
    private int priority; 
    private int reqMemory;
    
    private State state; //
    private long FinishTime; 
    private long FirstResponseTime; 
    private long WaitingTime;

    public PCB(int pid, int burstTime, int priority ,int reqMemory) {
        this.id = pid;
        this.burstTime = burstTime;
        this.priority = priority;
        this.reqMemory = reqMemory;
        
        this.FirstResponseTime = -1; // to know if the process has not executed
    }
    public PCB(int variables[])
    {
        this.id = variables[0];
        this.burstTime = variables[1];
        this.priority = variables[2];
        this.reqMemory = variables[3];
        
        this.FirstResponseTime = -1; // to know if the process has not executed

    }
    public PCB(PCB pcb)
    {
        this.id = pcb.id;
        this.burstTime = pcb.burstTime;
        this.priority = pcb.priority;
        this.reqMemory = pcb.reqMemory;
        
        this.state = pcb.state;
        this.FinishTime = pcb.FinishTime;
        this.FirstResponseTime = pcb.FirstResponseTime;
        this.WaitingTime = pcb.WaitingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getReqMemory() {
        return reqMemory;
    }

    public void setReqMemory(int reqMemory) {
        this.reqMemory = reqMemory;
    }

    public long getFinishTime() {
        return this.FinishTime;
    }
    
    public void setFinishTime(long FinishTime) {
        this.FinishTime = FinishTime;
    }
    public long getFirstResponseTime() {
        return this.FirstResponseTime;
    }
    public void setFirstResponseTime(long FirstResponseTime) {
        this.FirstResponseTime = FirstResponseTime;
    }   

    public long getWaitingTime() {
        return WaitingTime;
    }

    public void setWaitingTime(long WaitingTime) {
        this.WaitingTime = WaitingTime;
    }
    public void addWaitingTime(long time){
        this.WaitingTime += time;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    
    @Override
    public String toString() {
        return "PCB [id=" + id + ", state=" + state + ", burstTime=" + burstTime + ", reqMemory=" + reqMemory
                + ", FinishTime=" + FinishTime + ", FirstResponseTime=" + FirstResponseTime + ", WaitingTime="
                + WaitingTime + ", priority=" + priority + "]";
    }

    @Override // For Priorty Schduling
    public int compareTo(PCB other) {
        if (this.priority == other.priority) {
            return Integer.compare(this.id, other.id); // FIFO order for same priority
        }
        return Integer.compare(other.priority, this.priority); // Higher priority first
    }

}

enum State {
    READY, RUNNING, JobQueue ,PriorityReadyQueue ,Terminated;
}
