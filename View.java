import java.util.Scanner;

public class View 
{
    Scanner scanner = new Scanner(System.in);
    
    model m = new model(2048); // Here to change Memory 
    
    String fileName = "job.txt"; //This is File Name 
    
    //Thread LoadThread = new Thread(new MyRunnable(ThreadState.LoadToJobQueue, m));               
    Thread LoadThread = new Thread(new MyRunnable(ThreadState.LoadToJobQueueWithComments, m));  //this same as a above but with comments memory full .. 

    boolean AutoFill = true; 
    boolean WaitUntilFinishReading = true ;

    public void menu()
    {
        // Read and Create a process from a file
        runReadThreadIfQueuesEmpty();
            
        //Auto Load Thread from job Queue to ready Queue
        LoadThread.setDaemon(true); // if main dies it dies 
        LoadThread.start();
        System.out.println("Starting Loading...");   

        int option = 0;
        do
        {
            display_mainMenu();
            option = scanner.nextInt();
            System.out.println();
            if (option != -1)
                runReadThreadIfQueuesEmpty();
            HoldUntilFinish();
            System.out.println();
            try {
                Thread.sleep(125);
            } catch (Exception e) {
            }
            switch (option) 
            {
                case 1: 
                    //FCFS
                    FCFS fcfs = new FCFS(m);
                
                    Thread runFCFS = new Thread(new MyRunnable(ThreadState.Execute_FCFS, m,fcfs));
                    runFCFS.start();
                    try {
                        runFCFS.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    performance p = new performance(Algorithm.FCFS,fcfs.getFinishedQueue());
                    System.out.println(p); // Print performance of a algorithm such as AvgTurnaround , AvgWaitingTime..

                break;

                case 2: //Priorty //TODO:
                    Priorty pq = new Priorty(m);

                    Thread runPQ = new Thread(new MyRunnable(ThreadState.Execute_PQ, m,pq));
                    runPQ.start();
                    try {
                        runPQ.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    performance p2 = new performance(Algorithm.Priority,pq.getFinishedQueue());
                    System.out.println(p2); // Print performance of a algorithm such as AvgTurnaround , AvgWaitingTime..
                break;

                case 3://Rouund Roubin
                    //getting quantum time
                    System.out.println("Enter the quantum time: ");
                    System.out.print("-->");
                    int quantum = scanner.nextInt();                   
                    
                    RR rr = new RR(m);
                    Thread runRR = new Thread(new MyRunnable(ThreadState.Execute_RR, m,rr,quantum));
                    runRR.start();
                    try {
                        runRR.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    performance p3 = new performance(Algorithm.Round_Robin, rr.getFinishedQueue());
                    System.out.println(p3); // Print performance of a algorithm such as AvgTurnaround , AvgWaitingTime..
                    
                    
                break;
 
                case 4://Print best
                //RUN 1 ,2 ,3 AND THEN USE PERFORMANCE
                System.out.println(m.readyQueue.size());
                m.print_allQueue();

                break;
                case -1:
                    System.out.println("Exiting...");
                    LoadThread.interrupt();
                break;

                default:

                break;
            }
        }while(option != -1);
    }

    public void display_mainMenu()
    {
        System.out.println();
        System.out.println( "* * * * * * * * * * * * * * *");
        System.out.println("*          Welcome          *");
        System.out.println("*  Please select an option: *");
        System.out.println("*    1.FCFS                 *"); // process
        System.out.println("*    2.Priorty              *");
        System.out.println("*    3.Round Roubin         *");
        System.out.println("*    4.Best Status          *");
        System.out.println("*    -1.Exit                *");
        System.out.println( "* * * * * * * * * * * * * * *");
        System.out.println();
        System.out.print("-->");
    }
    private void runReadThreadIfQueuesEmpty() {
        if (AutoFill && m.CanFill()) {
            WaitUntilFinishReading = true;
            System.out.println("Start Reading file");
            try {
                Thread readThread = new Thread(new MyRunnable(ThreadState.read, m, fileName));
                readThread.start();
                readThread.join();
            } catch(Exception e) {
                e.printStackTrace();
            } 
            System.out.println("Job Queue Fulled");
            WaitUntilFinishReading = false;
        }
    }
    private void HoldUntilFinish()
    {
        while(WaitUntilFinishReading)
        {
            ;
        }
    }





    public static void main(String[] args) {
        View v = new View();
        v.menu();
    }
}