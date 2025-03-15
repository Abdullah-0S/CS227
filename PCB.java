public class PCB implements Comparable<PCB> {
    private int id;
    private State state; //
    private int burstTime; 
    private int reqMemory;
    private long FinishTime; 
    private long FirstResponseTime; 
    private long WaitingTime;
    private int priority; 

    public PCB(int pid, int burstTime, int priority ,int reqMemory) {
        this.id = pid;
        this.burstTime = burstTime;
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

    
    @Override
    public String toString() {
        return "PCB [id=" + id + ", state=" + state + ", burstTime=" + burstTime + ", reqMemory=" + reqMemory
                + ", FinishTime=" + FinishTime + ", FirstResponseTime=" + FirstResponseTime + ", WaitingTime="
                + WaitingTime + ", priority=" + priority + "]";
    }
    @Override
    public int compareTo(PCB other) {
        return Integer.compare(this.priority, other.priority);
    }

}

enum State {
    READY, RUNNING, JobQueue ,PriorityReadyQueue ,Terminated;
}
