import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RR {

    static model m = new model();
        // I Want u to implement the following algorithim and when process is started set its waiting time and finish time 
        public static Queue<PCB> RRsechdual(Queue<PCB> readyQueue,int numOfProccess, int quantum) {
                    long time = 0; // This is waiting time in queue set for each process
                    Queue<PCB> FinishedQueue = new LinkedList<PCB>(); // PCB with waiting time , first response ,and turn around info 
                    List<eachStep> eachStep = new LinkedList<>(); // Each PCB steps in the schdual algorithm to print gant chart
                     
                    Queue<PCB> RunningQueue = null;
                    for (int i = 0; i < numOfProccess; i++)
                    {
                        //model will insure the ready queue isn't empty so it will not be null
                        RunningQueue.add(readyQueue.poll());
                    }

                    System.out.println("\nExecuting Round-Robin Scheduling:");
            
                    while (!RunningQueue.isEmpty())
                    {
                        PCB runningProcess = RunningQueue.poll();
                        if ( runningProcess.getFirstResponseTime() == -1 ) { 
                            runningProcess.setFirstResponseTime(time); // This if statment to set the first response of a proces
                        }  
                        runningProcess.addWaitingTime( time - runningProcess.getWaitingTime());
            
                        if ( runningProcess.getBurstTime() > quantum)
                        {
                            System.out.println("Process " + runningProcess.getId() + " executed from " + time + " to " + (time + quantum ));
                            eachStep.addLast(new eachStep(runningProcess.getId(), time , time + quantum) );
                            runningProcess.setWaitingTime(time);
                            time += quantum;
                            runningProcess.setBurstTime(runningProcess.getBurstTime() - quantum);
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

                    return FinishedQueue;
                }

                public static Queue<PCB> RRsechdualWithoutFree(Queue<PCB> readyQueue,int numOfProccess, int quantum) {
                    long time = 0; // This is waiting time in queue set for each process
                    Queue<PCB> FinishedQueue = new LinkedList<PCB>(); // PCB with waiting time , first response ,and turn around info 
                    List<eachStep> eachStep = new LinkedList<>(); // Each PCB steps in the schdual algorithm to print gant chart
                    
                    Queue<PCB> RunningQueue = new LinkedList<PCB>();
                    for (int i = 0; i < numOfProccess; i++)
                    {
                        //model will insure the ready queue isn't empty so it will not be null
                        RunningQueue.add(readyQueue.poll());
                    }

                    System.out.println("\nExecuting Round-Robin Scheduling:");
            
                    while (!RunningQueue.isEmpty())
                    {
                        PCB runningProcess = RunningQueue.poll();
                        if ( runningProcess.getFirstResponseTime() == -1 ) { 
                            runningProcess.setFirstResponseTime(time); // This if statment to set the first response of a proces
                        }  
                        runningProcess.addWaitingTime( time - runningProcess.getWaitingTime());
            
                        if ( runningProcess.getBurstTime() > quantum)
                        {
                            System.out.println("Process " + runningProcess.getId() + " executed from " + time + " to " + (time + quantum ));
                            eachStep.addLast(new eachStep(runningProcess.getId(), time , time + quantum) );
                            runningProcess.setWaitingTime(time);
                            time += quantum;
                            runningProcess.setBurstTime(runningProcess.getBurstTime() - quantum);
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
                            //m.free(runningProcess);
                        }
                    }
                    
                    gantChart(eachStep);

                    return FinishedQueue;
                }

                public static void gantChart(List<eachStep> eachSteps)
                {
                    String hashtag = "##########";
                    String stars =  "#********#";
                    String empty = "";
                    System.out.println("----------------\nGant Chart\n---------------- " );
                    for (int i = 0; i < eachSteps.size();i++)
                    {
                        String s = String.format("PID: %d, Start at %d, End at %d",eachSteps.get(i).pid,eachSteps.get(i).startTime,eachSteps.get(i).endTime);
                        //System.out.printf("PID: %i"+ eachSteps.get(i).pid + ", Start at %l" + eachSteps.get(i).startTime +", End at %l" + eachSteps.get(i).endTime +"%5s\n" , hashtag );
                        System.out.printf("%s%18s\n",s,hashtag);
                        for (long j = eachSteps.get(i).startTime ; j < eachSteps.get(i).endTime; j++)
                        {
                            empty = new String(" ").repeat(s.length());
                            System.out.printf("%s%18s\n",empty,stars);
                        }
                        
                    }
                    System.out.printf("%s%18s\n",empty,hashtag);
            
                }
            
                // Testing
                public static void main(String[] args) {
                    List<int[]> processInfo = readFile.read("job.txt");
                    for (int i = 0 , size = processInfo.size() ; i < size ;i++)
                    {
                        int[] a = processInfo.removeFirst();
                        m.createProcess(new PCB(a));
                        m.load();
                    }
                    m.print_allQueue();
                    RRsechdual(m.readyQueue , m.readyQueue.size(), 7);
            
            
    }
}

//class Round_Robin_Result
//{
//    Queue<PCB> FinishedQueue;
//    List<eachStep> eachStep;
//
//    public Round_Robin_Result(Queue<PCB> FinishedQueue, List<eachStep> eachStep) {
//        this.FinishedQueue = FinishedQueue;
//        this.eachStep = eachStep;
//    }
//
//
//}