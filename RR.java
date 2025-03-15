import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RR {
    static model m = new model();
        // I Want u to implement the following algorithim and when process is started set its waiting time and finish time 
        public static Round_Robin_Result RRsechdual(Queue<PCB> RunningQueue, int quantum) {
                    long time = 0; // This is waiting time in queue set for each process
                    Queue<PCB> FinishedQueue = new LinkedList<PCB>();
                    List<eachStep> eachStep = new LinkedList<>();
                     
                    System.out.println("\nExecuting Round-Robin Scheduling:");
            
                    while (!RunningQueue.isEmpty())
                    {
                        PCB runningProcess = RunningQueue.poll();
                        if ( runningProcess.getFirstResponseTime() == -1 ) { //First Response
                            runningProcess.setFirstResponseTime(time); 
                        }  
                        runningProcess.addWaitingTime( time - runningProcess.getWaitingTime());
            
                        if ( runningProcess.getBurstTime() > quantum)
                        {
                            System.out.println("Process " + runningProcess.getId() + " executed from " + time + " to " + (time + quantum ));
                            eachStep.addLast(new eachStep(runningProcess.getId(), time , time + quantum) );
                            time += quantum;
                            runningProcess.setBurstTime(runningProcess.getBurstTime() - quantum);
                            runningProcess.setWaitingTime(time);
                            RunningQueue.add(runningProcess); 
                        } 
                        else
                        {
                            System.out.println("Process " + runningProcess.getId() + " executed from " + time + " to " + (time + runningProcess.getBurstTime() ));
                            eachStep.addLast(new eachStep(runningProcess.getId(), time , time + runningProcess.getBurstTime()) );
                            time += runningProcess.getBurstTime();
                            runningProcess.setBurstTime(0);
                            runningProcess.setFinishTime(time);
            
                            FinishedQueue.add(runningProcess);
                            m.free(runningProcess);
                        }
                    }
                    gantChart(eachStep);
                    return new Round_Robin_Result(FinishedQueue, eachStep);
                }
                public static void gantChart(List<eachStep> eachSteps)
                {
                    String hashtag = "##########";
                    String stars =  "#********#";
                    String empty = "";
                    System.out.println("----------------\nGant Chart\n---------------- " );
                    for (int i = 1; i <= eachSteps.size();i++)
                    {
                        for (int j = 1 ; j <= eachSteps.get(i).endTime; j++)
                        {
                            String s = String.format("PID: %d, Start at %d, End at %d",eachSteps.get(i).pid,eachSteps.get(i).startTime,eachSteps.get(i).endTime);
                            
                            if (j == 1 ) 
                            {
                                //System.out.printf("PID: %i"+ eachSteps.get(i).pid + ", Start at %l" + eachSteps.get(i).startTime +", End at %l" + eachSteps.get(i).endTime +"%5s\n" , hashtag );
                                System.out.printf("%s%3s\n",s,hashtag);
                            }
                            empty = new String(" ").repeat(s.length());
                            System.out.printf("%s%3s\n",empty,stars);
                        }
                        
                    }
                    System.out.printf("%s%3s\n",empty,hashtag);
            
                }
            
                // Testing
                public static void main(String[] args) {
                    // TODO Auto-generated method stub 
                    List<int[]> processInfo = readFile.read("job.txt");
                    for (int i = 0 , size = processInfo.size() ; i < size ;i++)
                    {
                        int[] a = processInfo.removeFirst();
                        m.createProcess(new PCB(a));
                        m.load();
                    }
                    RRsechdual(m.readyQueue , 7);
            
            
    }
}
class eachStep {
    int pid;
    long startTime;
    long endTime;

    public eachStep(int pid, long startTime, long endTime) {
        this.pid = pid;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
class Round_Robin_Result
{
    Queue<PCB> FinishedQueue;
    List<eachStep> eachStep;

    public Round_Robin_Result(Queue<PCB> FinishedQueue, List<eachStep> eachStep) {
        this.FinishedQueue = FinishedQueue;
        this.eachStep = eachStep;
    }


}