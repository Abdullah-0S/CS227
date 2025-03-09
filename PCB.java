public class PCB {
    private int id;
    private State state;
    private long time;
    private String text;

    // Constructor
    public PCB(int id, State state,String text) {
        this.id = id;
        this.state = state;
        this.text = text;
    }
    public String getText(){
        return text
    }
    public void setText(String text)
    {
        this.text = text;
    }

    // Getter for 'id'
    public int getId() {
        return id;
    }

    // Setter for 'id'
    public void setId(int id) {
        this.id = id;
    }

    // Getter for 'state'
    public State getState() {
        return state;
    }

    // Setter for 'state'
    public void setState(State state) {
        this.state = state;
    }

    // Getter for 'time'
    public long getTime() {
        return time;
    }

    // Setter for 'time'
    public void setTime(long time) {
        this.time = time;
    }

}
public enum State {
    Waiting, Ready, Finish;
}