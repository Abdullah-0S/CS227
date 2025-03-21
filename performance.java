import java.util.Queue;

public class performance {
    private double avgTurnAroundTime;
    private double avgWaitingTime;
    private double avgFirstResponseTime;
    private double avgFinishResponseTime;

    private long totalTurnAroundTime;
    private long totalWaitingTime;
    private long totalFirstResponseTime;
    private long totalFinishResponseTime;
    private State state;
    
    private Queue<PCB> FinishedQueue;
    
    public performance(State state , Queue<PCB> FinishedQueue)
    {
        this.state = state;
        this.FinishedQueue = FinishedQueue;
        fill();
        calculate();
    }

    public void fill()
    {
        for (int i = 0; i < FinishedQueue.size(); i++) 
        {
            PCB e = FinishedQueue.poll();
            totalTurnAroundTime += e.getBurstTime() + e.getWaitingTime();
            totalWaitingTime += e.getWaitingTime();
            totalFirstResponseTime += e.getFirstResponseTime();
            totalFinishResponseTime += e.getFinishTime();
            FinishedQueue.add(e);         
        }
    }

    public void calculate()
    {
        avgTurnAroundTime = (double) totalTurnAroundTime / FinishedQueue.size();
        avgWaitingTime = (double) totalWaitingTime / FinishedQueue.size();
        avgFirstResponseTime = (double) totalFirstResponseTime / FinishedQueue.size();
        avgFinishResponseTime = (double) totalFinishResponseTime / FinishedQueue.size();
    }

    //getters
    public double getAvgTurnAroundTime() {
        return avgTurnAroundTime;
    }

    public double getAvgWaitingTime() {
        return avgWaitingTime;
    }

    public double getAvgFirstResponseTime() {
        return avgFirstResponseTime;
    }

    public double getAvgFinishResponseTime() {
        return avgFinishResponseTime;
    }

    public long getTotalTurnAroundTime() {
        return totalTurnAroundTime;
    }

    public long getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public long getTotalFirstResponseTime() {
        return totalFirstResponseTime;
    }

    public long getTotalFinishResponseTime() {
        return totalFinishResponseTime;
    }

    public State getState() {
        return state;
    }
    
    public performance BetterPerformanceAt(performance p1 , performance p2, performance p3, performance p4, status s)
    {
        double i = 0;
        performance p = null;
        switch (s) {
            case TurnAroundTime:
            
            i = Math.min(Math.min(p1.getAvgTurnAroundTime(), p2.getAvgTurnAroundTime()), Math.min(p3.getAvgTurnAroundTime(), p4.getAvgTurnAroundTime()));
            
            if(i == p1.getAvgTurnAroundTime())
                p = p1;
            else if(i == p2.getAvgTurnAroundTime())
                p = p2;
            else if(i == p3.getAvgTurnAroundTime())
                p = p3;
            else if(i == p4.getAvgTurnAroundTime())
                p = p4;
            
            break;
            
            case WaitingTime:
            
            i = Math.min(Math.min(p1.getAvgWaitingTime(), p2.getAvgWaitingTime()), Math.min(p3.getAvgWaitingTime(), p4.getAvgWaitingTime()));
            
            if(i == p1.getAvgWaitingTime())
                p = p1;
            else if(i == p2.getAvgWaitingTime())
                p = p2;
            else if(i == p3.getAvgWaitingTime())
                p = p3;
            else if(i == p4.getAvgWaitingTime())            
                p = p4;
            break;
            
            case FirstResponseTime:
                i = Math.min(Math.min(p1.getAvgFirstResponseTime(), p2.getAvgFirstResponseTime()), Math.min(p3.getAvgFirstResponseTime(), p4.getAvgFirstResponseTime()));

                if(i == p1.getAvgFirstResponseTime())
                    p = p1;
                else if(i == p2.getAvgFirstResponseTime())
                    p = p2;
                else if(i == p3.getAvgFirstResponseTime())
                    p = p3;
                else if(i == p4.getAvgFirstResponseTime())
                    p = p4;
            break;

            case FinishResponseTime:
                
                i = Math.min(Math.min(p1.getAvgFinishResponseTime(), p2.getAvgFinishResponseTime()), Math.min(p3.getAvgFinishResponseTime(), p4.getAvgFinishResponseTime()));

                if(i == p1.getAvgFinishResponseTime())
                    p = p1;
                else if(i == p2.getAvgFinishResponseTime())
                    p = p2;
                else if(i == p3.getAvgFinishResponseTime())
                    p = p3;
                else if(i == p4.getAvgFinishResponseTime())
                    p = p4;
            break;

            default:
                System.out.println("Invalid status");
            break;
        }
        System.out.println("The Best Performance of " + s + " is: " + i + "ms in " + s +
        " with status represented by {AvgTurnAroundTime: " + getAvgTurnAroundTime() + "ms, " +
        "AvgWaitingTime: " + getAvgWaitingTime() + "ms, " +
        "AvgFirstResponseTime: " + getAvgFirstResponseTime() + "ms, " +
        "AvgFinishResponseTime: " + getAvgFinishResponseTime() + "ms}");

        return p;
    }
    @Override
    public String toString()
    {
        return "The Performance of " + state + " is: " +
        "{AvgTurnAroundTime: " + avgTurnAroundTime + "ms, " +
        "AvgWaitingTime: " + avgWaitingTime + "ms, " +
        "AvgFirstResponseTime: " + avgFirstResponseTime + "ms, " +
        "AvgFinishResponseTime: " + avgFinishResponseTime + "ms}";
    }
    
}
enum status {
    TurnAroundTime,
    WaitingTime,
    FirstResponseTime,
    FinishResponseTime
}