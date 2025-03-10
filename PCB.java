import java.util.PriorityQueue;
public class PCB implements Comparable<PCB> {
    private int id;
    private State state;
    private int burstTime;
    private int reqMemory;
    private int firstResponseTime;
    private long time;
    private String text;
    private int priority;

    public PCB(int pid, int burstTime, int priority ,int reqMemory) {
        this.id = id;
        this.burstTime = burstTime;
        this.reqMemory = reqMemory;

    }
    public PCB(int variables[])
    {
        this.id = variables[0];
        this.burstTime = variables[1];
        this.priority = variables[2];
        this.reqMemory = variables[3];
;
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

    public int getFirstResponseTime() {
        return firstResponseTime;
    }

    public void setFirstResponseTime(int firstResponseTime) {
        this.firstResponseTime = firstResponseTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    @Override
    public int compareTo(PCB other) {
        return Integer.compare(this.priority, other.priority);
    }
}

enum State {
    READY, RUNNING, JobQueue ,PriorityReadyQueue ,Terminated;
}
