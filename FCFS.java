import java.util.Queue;

public class FCFS {
    private model systemModel;
    private long time = 0; // يمثل وقت الانتظار في الطابور لكل عملية

    public FCFS(model systemModel) {
        this.systemModel = systemModel;
    }

    public void schedule() {
        Queue<PCB> readyQueue = systemModel.readyQueue;
        
        System.out.println("\nExecuting FCFS Scheduling:");

        while (!readyQueue.isEmpty()) {
            PCB process = readyQueue.poll();
            
            // حساب زمن الانتظار لكل عملية
            process.setTime(time);
            process.setState(State.RUNNING);
            process.setFirstResponseTime((int) time);

            System.out.println("Process " + process.getId() + " executed from " + time + " to " + (time + process.getBurstTime()));

            // تحديث الزمن
            time += process.getBurstTime();
            
            process.setState(State.Terminated);
            systemModel.free(process);
        }
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
        FCFS fcfsScheduler = new FCFS(systemModel);
        fcfsScheduler.schedule();
    }
}
