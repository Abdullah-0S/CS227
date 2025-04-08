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
    
    private Algorithm algo;
    
    private Queue<PCB> FinishedQueue;
    
    public performance(Algorithm algo , Queue<PCB> FinishedQueue)
    {
        this.algo = algo;
        this.FinishedQueue = FinishedQueue;
        fill();
        calculate();
    }
    public performance()
    {

    }

    public void fill()
    {
        for (int i = 0; i < FinishedQueue.size(); i++) 
        {
            PCB e = FinishedQueue.poll();
            this.setTotalTurnAroundTime(this.getTotalTurnAroundTime() + (e.getFinishTime() - e.getFirstResponseTime()));
            totalWaitingTime += e.getWaitingTime();
            totalFirstResponseTime += e.getFirstResponseTime();
            totalFinishResponseTime += e.getFinishTime();
            FinishedQueue.add(e);         
        }
    }

    public void calculate()
    {
        double size = 1.0 * FinishedQueue.size();
        avgTurnAroundTime =  this.getTotalTurnAroundTime() / size;
        avgWaitingTime =  totalWaitingTime / size;
        avgFirstResponseTime =  totalFirstResponseTime / size;
        avgFinishResponseTime =  totalFinishResponseTime / size;
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

    public Algorithm getAlgorithm() {
        return algo;
    }
    
    
    public performance BetterPerformanceAt(performance p1 , performance p2, performance p3, status s)
    {
        double i = 0;
        performance p = new performance();
        switch (s) {
            case TurnAroundTime:
            
            i = Math.min(Math.min(p1.getAvgTurnAroundTime(), p2.getAvgTurnAroundTime()), Math.min(p3.getAvgTurnAroundTime(), p3.getAvgTurnAroundTime() ));
            
            if(i == p1.getAvgTurnAroundTime())
                p = p1;
            else if(i == p2.getAvgTurnAroundTime())
                p = p2;
            else if(i == p3.getAvgTurnAroundTime())
                p = p3;

            
            break;
            
            case WaitingTime:
            
            i = Math.min(Math.min(p1.getAvgWaitingTime(), p2.getAvgWaitingTime()), Math.min(p3.getAvgWaitingTime(), p3.getAvgWaitingTime()));
            
            if(i == p1.getAvgWaitingTime())
                p = p1;
            else if(i == p2.getAvgWaitingTime())
                p = p2;
            else if(i == p3.getAvgWaitingTime())
                p = p3;

            break;
            
            case FirstResponseTime:
                i = Math.min(Math.min(p1.getAvgFirstResponseTime(), p2.getAvgFirstResponseTime()), Math.min(p3.getAvgFirstResponseTime(), p3.getAvgFirstResponseTime()));

                if(i == p1.getAvgFirstResponseTime())
                    p = p1;
                else if(i == p2.getAvgFirstResponseTime())
                    p = p2;
                else if(i == p3.getAvgFirstResponseTime())
                    p = p3;

            break;

            case FinishResponseTime:
                
                i = Math.min(Math.min(p1.getAvgFinishResponseTime(), p2.getAvgFinishResponseTime()), Math.min(p3.getAvgFinishResponseTime(), p3.getAvgFinishResponseTime()));

                if(i == p1.getAvgFinishResponseTime())
                    p = p1;
                else if(i == p2.getAvgFinishResponseTime())
                    p = p2;
                else if(i == p3.getAvgFinishResponseTime())
                    p = p3;

            break;

            default:
                System.out.println("Invalid status");
            break;
        }
        System.out.println("The Best Performance of " + s + " is: " + i + "ms in " + p.getAlgorithm()+
        " with status represented by {AvgTurnAroundTime: " + p.getAvgTurnAroundTime() + "ms, " +
        "AvgWaitingTime: " + p.getAvgWaitingTime() + "ms, " +
        "AvgFirstResponseTime: " + p.getAvgFirstResponseTime() + "ms, " +
        "AvgFinishResponseTime: " + p.getAvgFinishResponseTime() + "ms}");

        return p;
    }

    @Override
    public String toString()
    {
        return "The Performance of " + algo + " is: " +
        "{AvgTurnAroundTime: " + avgTurnAroundTime + "ms, " +
        "AvgWaitingTime: " + avgWaitingTime + "ms, " +
        "AvgFirstResponseTime: " + avgFirstResponseTime + "ms, " +
        "AvgFinishResponseTime: " + avgFinishResponseTime + "ms}";
    }

    public void setTotalTurnAroundTime(long totalTurnAroundTime) {
        this.totalTurnAroundTime = totalTurnAroundTime;
    }

    public void setTotalWaitingTime(long totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public void setTotalFirstResponseTime(long totalFirstResponseTime) {
        this.totalFirstResponseTime = totalFirstResponseTime;
    }

    public void setTotalFinishResponseTime(long totalFinishResponseTime) {
        this.totalFinishResponseTime = totalFinishResponseTime;
    }

    public void setAlgorithm(Algorithm algo) {
        this.algo = algo;
    }

    public void setFinishedQueue(Queue<PCB> FinishedQueue) {
        this.FinishedQueue = FinishedQueue;
    }

    
}
enum status {
    TurnAroundTime,
    WaitingTime,
    FirstResponseTime,
    FinishResponseTime
}