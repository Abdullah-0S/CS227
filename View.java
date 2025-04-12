import java.util.Scanner;

public class View 
{
    Scanner scanner = new Scanner(System.in);
    
    model m = new model(2048); // Here to change Memory 
    
    
    String fileName = "random.txt"; //This is File Name 
    Thread readThread = new Thread(new MyRunnable(ThreadState.read, m ,fileName));
    Thread LoadThread = new Thread(new MyRunnable(ThreadState.LoadToJobQueue, m));               
    //Thread LoadThread = new Thread(new MyRunnable(ThreadState.LoadToJobQueueWithComments, m));  //this same as a above but with comments memory full .. 


    public void menu()
    {
        // Create a process from a file
        readThread.start();
        try{
        readThread.join();
        }catch(InterruptedException e) {
        e.printStackTrace();
        }
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
            switch (option) 
            {
                case 1: //FCFS
                    FCFS fcfs = new FCFS(m);
                    fcfs.schedule(m.readyQueue,m.JobQueue);

                    fcfs.printGantChart(); // print gantChart

                    performance p = new performance(Algorithm.FCFS,fcfs.getFinishedQueue());
                    System.out.println(p); // Print performance of a algorithm such as AvgTurnaround , AvgWaitingTime..

                break;

                case 2: //Priorty //TODO:
                //    Priorty pq = new Priorty(m);
// pq.PQ(m.readyQueue, m.JobQueue);
                   // pq.printGantChart();
                break;

                case 3://Rouund Roubin
                //TODO:
                break;
 
                case 4://Print best
                //RUN 1 ,2 ,3 AND THEN USE PERFORMANCE
                break;

                case -1:
                    // KILL THREADS BEFORE
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





    public static void main(String[] args) {
        View v = new View();
        v.menu();
    }
}