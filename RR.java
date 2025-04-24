import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RR {
    model m; 
    Queue<PCB> FinishedQueue = new LinkedList<PCB>(); // PCB with waiting time , first response ,and turn around info 
    List<eachStep> eachStep = new LinkedList<>(); // Each PCB steps in the schdual algorithm to print gant chart

    public RR(model m )
    {
          this.m = m;
    }

        // I Want u to implement the following algorithim and when process is started set its waiting time and finish time 
        // public  Queue<PCB> RRsechdual(Queue<PCB> readyQueue,int numOfProccess, int quantum) {
        //             long time = 0; // This is waiting time in queue set for each process
                     
        //             Queue<PCB> RunningQueue = null;
        //             for (int i = 0; i < numOfProccess; i++)
        //             {
        //                 //model will insure the ready queue isn't empty so it will not be null
        //                 // RunningQueue.add(readyQueue.poll());
        //             }

        //             System.out.println("\nExecuting Round-Robin Scheduling:");
            
        //             while (!RunningQueue.isEmpty())
        //             {
        //                 PCB runningProcess = RunningQueue.poll();
        //                 if ( runningProcess.getFirstResponseTime() == -1 ) { 
        //                     runningProcess.setFirstResponseTime(time); // This if statment to set the first response of a proces
        //                 }  
        //                 runningProcess.addWaitingTime( time - runningProcess.getWaitingTime());
            
        //                 if ( runningProcess.getBurstTime() > quantum)
        //                 {
        //                     System.out.println("Process " + runningProcess.getId() + " executed from " + time + " to " + (time + quantum ));
        //                     eachStep.addLast(new eachStep(runningProcess.getId(), time , time + quantum) );
        //                     runningProcess.setWaitingTime(time);
        //                     time += quantum;
        //                     runningProcess.setBurstTime(runningProcess.getBurstTime() - quantum);
        //                     RunningQueue.add(runningProcess); 
        //                 } 
        //                 else
        //                 {
        //                     System.out.println("Process " + runningProcess.getId() + " executed from " + time + " to " + (time + runningProcess.getBurstTime() ));
        //                     eachStep.addLast(new eachStep(runningProcess.getId(), time , time + runningProcess.getBurstTime()) );
        //                     time += runningProcess.getBurstTime();
        //                     runningProcess.setBurstTime(0);
        //                     runningProcess.setFinishTime(time);
            
        //                     FinishedQueue.add(runningProcess);
        //                     m.free(runningProcess);
        //                 }
        //             }
                    
        //             gantChart(eachStep);

        //             return FinishedQueue;
        //         }

                public  Queue<PCB> RRsechdualWithoutFree(Queue<PCB> readyQueue,int numOfProccess, int quantum) {
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

                public void printGantChart(){
                    gantChart(eachStep);
                }
                public void clearFinishedQueue(){
                    FinishedQueue.clear();
                }
                public void clearEachSteps(){
                    eachStep.clear();
                }
                public Queue<PCB> getFinishedQueue() {
                    return FinishedQueue;
                }
                public List<eachStep> getEachSteps() {
                    return eachStep;
                }

                public static void gantChart(List<eachStep> eachSteps)
                {
                    String hashtag = "##########";
                    String stars =  "#********#";
                    String empty = "";
                    System.out.println("----------------\nGant Chart\n---------------- " );
                    for (int i = 0; i < eachSteps.size();i++)
                    {
                        String s = String.format("PID: %d, Start at %d, End at %d",eachSteps.get(i).pid,eachSteps.get(i).startTime,eachSteps.get(i).endTime); //This will print pid info first line 
                        System.out.printf("%s%18s\n",s,hashtag); //This will print hashtag for first one ######
                        for (long j = eachSteps.get(i).startTime ; j < eachSteps.get(i).endTime; j += 6)
                        {
                            empty = new String(" ").repeat(s.length());
                            System.out.printf("%s%18s\n",empty,stars);
                        }
                        
                    }
                    System.out.printf("%s%18s\n",empty,hashtag);
            
                }
            public void RRsechduala(int quantum) {

                    long time = 0; // This is waiting time in queue set for each process                     
                    Queue<PCB> RunningQueue = m.readyQueue;
                    clearEachSteps();
                    clearFinishedQueue();

                    System.out.println("\nExecuting Round-Robin Scheduling:");
                    boolean canExit = false;
                    while (!canExit)
                    {

                        PCB runningProcess = m.pollReadyQueue();

                        if ( runningProcess.getFirstResponseTime() == -1 ) { 
                            runningProcess.setFirstResponseTime(time); // This if statment to set the first response of a proces
                        }  
                        runningProcess.addWaitingTime( time - runningProcess.getWaitingTime()); // this will calculate time of process waiting after first response
            
                        // PCB With bigger quantum 
                        if ( runningProcess.getBurstTime() > quantum)
                        {
                            System.out.println("Process " + runningProcess.getId() + " executed from " + time + " to " + (time + quantum ));
                            eachStep.addLast(new eachStep(runningProcess.getId(), time , time + quantum) );
                            runningProcess.setWaitingTime(time);
                            time += quantum;
                            runningProcess.setBurstTime(runningProcess.getBurstTime() - quantum);
                            RunningQueue.add(runningProcess); 
                        } 
                            // PCB With Less quantum "Last time"
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
                        System.out.println(m.readyQueue.size());
                        System.out.println(m.JobQueue.size());
                        if (m.CanFill())
                        {
                            canExit = true;
                            System.out.println("Finished");
                        }
                    } // end of while loop 
                }

                // Testing
    //             public static void main(String[] args) {
    //                 List<int[]> processInfo = readFile.read("job.txt");
    //                 for (int i = 0 , size = processInfo.size() ; i < size ;i++)
    //                 {
    //                     int[] a = processInfo.removeFirst();
    //                     m.createProcess(new PCB(a));
    //                     m.load();
    //                 }
    //                 m.print_allQueue();
    //                 RRsechdual(m.readyQueue , m.readyQueue.size(), 7);
            
            
    // }
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