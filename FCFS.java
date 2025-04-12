import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
//This class was done by Mohammed

public class FCFS {
    private model systemModel;
    private long time = 0; // يمثل وقت الانتظار في الطابور لكل عملية

    Queue<PCB> finishedQueue = new LinkedList<>();
    List<eachStep> eachSteps = new LinkedList<>();

    public FCFS(model systemModel) {
        this.systemModel = systemModel;
    }

    public void schedule(Queue<PCB> readyQueue, Queue<PCB> jobQueue) 
    {
        clearEachSteps();
        clearFinishedQueue();
        
        System.out.println("\nExecuting FCFS Scheduling:");

        while (! (jobQueue.isEmpty() && readyQueue.isEmpty()) ) {
            PCB process = readyQueue.poll();
            if (process == null)
            {
                continue;
            }
            // حساب زمن الانتظار لكل عملية
            process.setFirstResponseTime( time);
            process.setWaitingTime(time);
            process.setState(State.RUNNING);

            System.out.println("Process " + process.getId() + " executed from " + time + " to " + (time + process.getBurstTime()));
            eachSteps.addLast(new eachStep(process.getId(), time, time + process.getBurstTime()));
            // تحديث الزمن
            time += process.getBurstTime();

            process.setFinishTime(time);
            process.setBurstTime(0);
            process.setState(State.Terminated);

            finishedQueue.add(process);
            systemModel.free(process);
        }
    }
    public Queue<PCB> getFinishedQueue() {
        return finishedQueue;
    }
    public List<eachStep> getEachSteps() {
        return eachSteps;
    }
    public void clearFinishedQueue(){
        finishedQueue.clear();
    }
    public void clearEachSteps(){
        eachSteps.clear();
    }
    public void printGantChart(){
        RR.gantChart(eachSteps);
    }
    
    public Queue<PCB> scheduleWithoutFree(Queue<PCB> readyQueue, int numOfProccess) {
        
        Queue<PCB> finishedQueue = new LinkedList<>();
        List<eachStep> eachSteps = new LinkedList<>();
        
        System.out.println("\nExecuting FCFS Scheduling:");

        while (numOfProccess > 0) {
            PCB process = readyQueue.poll();
            
            // حساب زمن الانتظار لكل عملية
            process.setFirstResponseTime( time);
            process.setWaitingTime(time);
            process.setState(State.RUNNING);

            System.out.println("Process " + process.getId() + " executed from " + time + " to " + (time + process.getBurstTime()));
            eachSteps.addLast(new eachStep(process.getId(), time, time + process.getBurstTime()));
            // تحديث الزمن
            time += process.getBurstTime();

            process.setFinishTime(time);
            process.setBurstTime(0);
            process.setState(State.Terminated);

            finishedQueue.add(process);
            //systemModel.free(process);
            numOfProccess--;
        }
        RR.gantChart(eachSteps);
        return finishedQueue;
    }
    
    // Testing the FCFS Scheduler
    public static void main(String[] args) {
        model systemModel = new model();

        // Read processes from file and load into the system
        for (int[] processInfo : readFile.read("job.txt")) {
            PCB process = new PCB(processInfo);
            systemModel.createProcess(process);
        }

        // Move jobs to ready queue
        while (!systemModel.JobQueue.isEmpty()) {
            systemModel.load();
        }

        // Run FCFS Scheduler
        systemModel.execute(Algorithm.FCFS,systemModel.readyQueue.size());
    }
}
