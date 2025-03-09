public class PCB
    {
    private int id;
    private Sta state;
    private long time;
    private String text;

    // Constructor
    public PCB(int id, Sta state,String text) {
        this.id = id;
        this.state = state;
        this.text = text;
    }
    public String getText(){
        return text;
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
    public Sta getState() {
        return state;
    }

    // Setter for 'state'
    public void setState(Sta state) {
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
