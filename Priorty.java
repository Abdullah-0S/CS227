
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
// This class was done by abdulmalik 

public class Priorty {
	long time; // This is waiting time in queue set for each process    
    model systemModel;
    public Priorty(model m) {
        systemModel = m;
    }


	public Queue<PCB> PQ(Queue<PCB> runningQueue) {

        List<eachStep> steps = new LinkedList<>();

		PriorityQueue<PCB> priorityQueue = new PriorityQueue<>();
		Queue<PCB> finishedQueue = new LinkedList<>(); // Queue to store finished processes

		// Step 2: Move processes from Ready Queue to Priority Queue
		priorityQueue.addAll(runningQueue);
		System.out.println("\nExecuting Non-Preemptive Priority Scheduling:");
		System.out.println("Priority Queue Size: " + priorityQueue.size());

		while (!priorityQueue.isEmpty()) {
			PCB process = priorityQueue.poll(); // Get highest priority process


			// Set first response time (if not already set)
			if (process.getFirstResponseTime() == -1) {
				process.setFirstResponseTime( time);
			}

			// Calculate waiting time (total time spent before execution starts)
			process.setWaitingTime(time - 0); //Todo: Check this

			// Execute process
			System.out.println("Executing Process ID: " + process.getId() + ", Priority: " + process.getPriority());
			System.out.println(
					"Process " + process.getId() + " executed from " + time + " to " + (time + process.getBurstTime()));
            steps.addLast(new eachStep(process.getId() , time, time + process.getBurstTime())); //For Gant Chart

			time += process.getBurstTime(); // Update total execution time
			process.setState(State.Terminated);
			process.setFinishTime(time); // Store finish time

			systemModel.free(process);

			finishedQueue.add(process);
		}
            RR.gantChart(steps); //Print Gant Chart
		return finishedQueue; // Return the queue containing finished processes
	}
	public Queue<PCB> PQWithoutFree(Queue<PCB> runningQueue) {

        List<eachStep> steps = new LinkedList<>();

		PriorityQueue<PCB> priorityQueue = new PriorityQueue<>();
		Queue<PCB> finishedQueue = new LinkedList<>(); // Queue to store finished processes

		// Step 2: Move processes from Ready Queue to Priority Queue
		priorityQueue.addAll(runningQueue);
		System.out.println("\nExecuting Non-Preemptive Priority Scheduling:");
		System.out.println("Priority Queue Size: " + priorityQueue.size());

		while (!priorityQueue.isEmpty()) {
			PCB process = priorityQueue.poll(); // Get highest priority process


			// Set first response time (if not already set)
			if (process.getFirstResponseTime() == -1) {
				process.setFirstResponseTime( time);
			}

			// Calculate waiting time (total time spent before execution starts)
			process.setWaitingTime(time - 0); //Todo: Check this

			// Execute process
			System.out.println("Executing Process ID: " + process.getId() + ", Priority: " + process.getPriority());
			System.out.println(
					"Process " + process.getId() + " executed from " + time + " to " + (time + process.getBurstTime()));
            steps.addLast(new eachStep(process.getId() , time, time + process.getBurstTime())); //For Gant Chart

			time += process.getBurstTime(); // Update total execution time
			process.setState(State.Terminated);
			process.setFinishTime(time); // Store finish time

			//systemModel.free(process);

			finishedQueue.add(process);
		}
            RR.gantChart(steps); //Print Gant Chart
		return finishedQueue; // Return the queue containing finished processes
	}

	// Testing
	public static void main(String[] args) {
		model systemModel = new model(); // Create system model
        systemModel.print_Memory();

		List<PCB> process = null;
        try {
            process = readFile.read_returnPcbs("./job.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } // Read process data

		for (int i = 0; i < process.size(); i++) {
			systemModel.createProcess(process.get(i)); // Add process to system
		}
		process = null; // Clear process list

		while (!systemModel.JobQueue.isEmpty()) {
			systemModel.load();
		}

		Priorty scheduler = new Priorty(systemModel);
		Queue<PCB> finishedQueue = scheduler.PQ(systemModel.readyQueue);

		// Step 4: Print finished processes information
		System.out.println("\nFinished Processes:");
		for (PCB process1 : finishedQueue) {
			System.out.println("PID: " + process1.getId() + ", Waiting Time: " + process1.getWaitingTime()
					+ ", First Response Time: " + process1.getFirstResponseTime() + ", Finish Time: "
					+ process1.getFinishTime());
		}
	}
}