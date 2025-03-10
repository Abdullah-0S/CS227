public class PCB {
    private int id;
    private State state;
    private int burstTime;
    private int reqMemory;
    private int firstResponseTime;
    private long time;
    private String text;

    public PCB(int id, int burstTime, int reqMemory, String text) {
        this.id = id;
        this.burstTime = burstTime;
        this.reqMemory = reqMemory;
        this.text = text;
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
}

enum State {
    READY, RUNNING, WAITING, TERMINATED
}
