import java.util.Scanner;

public class View{
    model m = new model();
    Scanner s = new Scanner(System.in);
    String lineOfStars = new String("*").repeat(41); // 30 stars 

    public void display_mainMenu()
    {   
        int option = 0;
        do
        {
            System.out.println(lineOfStars);
            System.out.println("*  Welcome to the application           *");
            System.out.println("*                                       *");
            System.out.println("*  Please select an option:             *");
            System.out.println("*    1.read file(Create Set Of Process) *"); // process
            System.out.println("*    2.Creat Process                    *");
            System.out.println("*    3.Enter System Call                *");
            System.out.println("*    -1.exit                            *");
            System.out.println("*                                       *");
            System.out.println(lineOfStars);
            System.out.print("-->");
            option = s.nextInt();
            System.out.println();
        
            switch(option)
            {
                case 1:
                    System.out.println("Please Enter the file name:");
                    System.out.print("-->");
                    String fileName = s.next();
                    System.out.println();
                    try 
                    {   
                        m.read(fileName);
                    }
                    catch(Exception e)
                    {
                        System.out.println("Error in reading the file");
                    }
                    break;
                
                case 2:
                    System.out.println("Create Process");
                    System.out.println();
                    System.out.println("Enter the Process ID:");
                    System.out.print("-->");
                    int pid = s.nextInt();
                    System.out.println();
                    if (pid < 0 ) 
                    {
                        do
                        {
                            System.out.println("Enter a positive number");
                            pid = s.nextInt();
                        } while (pid > 0);                   
                    }
                    System.out.println("Enter the Burst Time");
                    System.out.print("-->");
                    int burstTime = s.nextInt();
                    System.out.println();
                    if (burstTime < 0 ) 
                    {
                        do
                        {
                            System.out.println("Enter a positive number");
                            System.out.print("-->");
                            burstTime = s.nextInt();
                            System.out.println();
                        } while (burstTime > 0);                   
                    }
                    System.out.println("Enter the Priority");
                    System.out.print("-->");
                    int priority = s.nextInt();
                    System.out.println();
                    if (priority < 0 ) 
                    {
                        do
                        {
                            System.out.println("Enter a positive number");
                            System.out.print("-->");
                            priority = s.nextInt();
                            System.out.println();
                        } while (priority > 0);                   
                    }
                    System.out.println("Enter the Required Memory");
                    System.out.print("-->");
                    int reqMemory = s.nextInt();
                    System.out.println();
                    if (reqMemory < 0 ) 
                    {
                        do
                        {
                            System.out.println("Enter a positive number");
                            System.out.print("-->");
                            reqMemory = s.nextInt();
                            System.out.println();
                        } while (reqMemory > 0);                   
                }
                    m.createProcess(new PCB(pid, burstTime, priority, reqMemory));
                    System.out.println("Process created and loadded to the job queue  with PID: " + pid);
                break;
                
                case 3:
                    display_systemcall();
                break;

                default:
                    System.out.println("Invalid option");
                break;
            }
            
        }while (option != 3);
    
    }
    public void display_systemcall()
    {
        int option = 0;
        do
        {
            System.out.println(lineOfStars);
            System.out.println("*             System calls              *");
            System.out.println("*                                       *");
            System.out.println("*  Please select an option:             *");
            System.out.println("*   1.Load a process to ready queue     *");
            System.out.println("*   2.Load all process to ready queue   *");
            System.out.println("*   3.Kill a process                    *");
            System.out.println("*   4.Print Job queue                   *");
            System.out.println("*   5.Print Ready queue                 *");
            System.out.println("*   6.Print Running queue               *");
            System.out.println("*   7.Print All queue                   *");
            System.out.println("*   8.Print Memory                      *");
            System.out.println("*   9.Execute # of process (pick algo)  *");
            System.out.println("*   10.Execute all process (pick algo)  *");
            System.out.println("*   11.Back To Main Menu                *");
            System.out.println("*                                       *");
            System.out.println("*   -1.exit                             *");
            System.out.println(lineOfStars);
            System.out.print("-->");
            option = s.nextInt();
            System.out.println();

             switch(option)
            {
                case 1:
                    m.load();
                break;

                 case 2:
                    m.loadAll_Process();
                break;

                 case 3:
                    System.out.println("Enter the Process ID to kill:");
                    System.out.print("-->");
                    int pid = s.nextInt();
                    System.out.println();
                    m.killProcess(pid);
                break;
                
                case 4:
                    m.print_JobQueue();
                break;
                
                case 5:
                    m.print_ReadyQueue();
                break;

             case 6:
                        m.print_RunningQueue();
                break;

             case 7:
                    m.print_allQueue();
                break;
                    
                case 8:
                    m.print_Memory();
                break;
                    
                case 9: //Execute # of process (pick algo)  *");
                        int numOfProccess = 0;
                        System.out.println("Enter the number of process to execute:");
                        System.out.print("-->");
                        numOfProccess = s.nextInt();
                        System.out.println();
                        System.out.println("Please select an option:");
                        System.out.println("1. First Come First Serve");
                        System.out.println("2. Priority");
                        System.out.println("3. Round Robin");
                        System.out.println("4 Back to System Calls");
                        System.out.println("5. Exit");
                        System.out.print("-->");
                        int option2 = s.nextInt();
                        System.out.println();
                        
                        switch (option2) {
                                case 1:
                                m.execute(Algorithm.FCFS, numOfProccess);
                            break;
                            
                            case 2:
                                m.execute(Algorithm.Priority, numOfProccess);
                            break;
                            
                            case 3:
                                m.execute(Algorithm.Round_Robin, numOfProccess);
                            break;

                            case 4:

                            break;

                                                
                            case -1:
                                System.out.println("Exiting the application");
                                System.out.println("Goodbye");
                                System.exit(0);
                            break;

                            default:
                                System.out.println("Invalid option");
                            break;
                        }
                    break;
                                    
                    case 10: //10.Execute all process (pick algo) 
                    numOfProccess = m.readyQueue.size();
                        
                    System.out.println();
                    System.out.println("Please select an option:");
                    System.out.println("1. First Come First Serve");
                    System.out.println("2. Priority");
                    System.out.println("3. Round Robin");
                    System.out.println("4 Back to System Calls");
                    System.out.println("5. Exit");
                   System.out.print("-->");
                   option2 = s.nextInt();
                   System.out.println();
                   switch (option2) {
                           case 1:
                               m.execute(Algorithm.FCFS, numOfProccess);
                           break;

                           case 2:
                               m.execute(Algorithm.Priority, numOfProccess);
                        break;

                        case 3:
                            m.execute(Algorithm.Round_Robin, numOfProccess);
                        break;

                        case 4:

                        break;


                        case -1:
                            System.out.println("Exiting the application");
                            System.out.println("Goodbye");
                            System.exit(0);
                        break;

                        default:
                            System.out.println("Invalid option");
                        break;
                    }
                    break;
                                    
                    case 11: //11.Back To Main Menu 
                    
                    break;
                                    
                    case -1:
                        System.out.println("Exiting the application");
                        System.out.println("Goodbye");
                        System.exit(0);
                    break;
                                    
                    default:
                        System.out.println("Invalid option");
                    break;
                }
        }while (option != -1);
    }

    public static void main(String[] args) 
    {
        View v  = new View();
        v.display_mainMenu();
    }
}
