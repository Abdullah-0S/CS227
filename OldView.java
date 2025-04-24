import java.util.Scanner;

public class OldView {
    model m = new model();
    Scanner s = new Scanner(System.in);
    String lineOfStars = new String("*").repeat(41); // 41 stars 

    Thread readThread, LoadThread, ExcuteThread, killProcessThread;

    int option = 0;

    public void display_mainMenu() {
        do {
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
            if (option != -1) {
                try { 
                    System.out.print("-->");
                    option = s.nextInt();
                    System.out.println();
                } catch (Exception e) {  
                    System.out.println("Invalid input. Please enter a valid option.");
                    s.nextLine(); // Clear the buffer
                    continue;
                }
            }

            switch (option) {
                case 1:
                    System.out.println("Please Enter the file name:");
                    System.out.print("-->");
                    s.nextLine(); // Consume newline left-over
                    String fileName = s.nextLine();
                    System.out.println();
                    readThread = new Thread(new MyRunnable(ThreadState.read, m, fileName));

                    readThread.start();
                    try { 
                        readThread.join();
                    } catch (InterruptedException e) { 
                        System.out.println("Error occurred while reading file: " + e.getMessage());
                    }
                    break;

                case 2:
                    try { 
                        System.out.println("Create Process");
                        System.out.println();
                        System.out.println("Enter the Process ID:");
                        System.out.print("-->");
                        Integer pid = s.nextInt();
                        s.nextLine();
                        pid = positive_Num(pid);
                        if (!m.UniqueID(pid)) {
                            Boolean unique = true;
                            do {
                                if (pid < 0) {
                                    System.out.println("The ID must be positive Please Enter positive ID");
                                    System.out.print("-->");
                                    pid = s.nextInt();
                                } else {
                                    System.out.println("The ID isn't Unique Please Enter Unique ID");
                                    System.out.print("-->");
                                    pid = s.nextInt();
                                }
                                unique = m.UniqueID(pid);
                            } while (!unique || pid < 0);
                        }

                        System.out.println("Enter the Burst Time");
                        System.out.print("-->");
                        int burstTime = s.nextInt();
                        System.out.println();
                        if (burstTime < 0) {
                            do {
                                System.out.println("Enter a positive number");
                                System.out.print("-->");
                                burstTime = s.nextInt();
                                System.out.println();
                            } while (burstTime < 0);
                        }

                        System.out.println("Enter the Priority");
                        System.out.print("-->");
                        int priority = s.nextInt();
                        System.out.println();
                        if (priority < 0) {
                            do {
                                System.out.println("Enter a positive number");
                                System.out.print("-->");
                                priority = s.nextInt();
                                System.out.println();
                            } while (priority < 0);
                        }

                        System.out.println("Enter the Required Memory");
                        System.out.print("-->");
                        int reqMemory = s.nextInt();
                        System.out.println();
                        if (reqMemory < 0) {
                            do {
                                System.out.println("Enter a positive number");
                                System.out.print("-->");
                                reqMemory = s.nextInt();
                                System.out.println();
                            } while (reqMemory < 0);
                        }
                        if (reqMemory > m.getMaxMemory()) {
                            System.out.println("Memory required by the process is greater than the available memory");
                            do {
                                System.out.println("Enter a positive number less than " + m.getMaxMemory());
                                System.out.print("-->");
                                reqMemory = s.nextInt();
                                System.out.println();
                            } while (reqMemory < 0 && reqMemory > m.getMaxMemory());
                        }
                        m.createProcess(new PCB(pid, burstTime, priority, reqMemory));
                        System.out.println("Process created and loaded to the job queue with PID: " + pid);
                    } catch (Exception e) {  
                        System.out.println("Error occurred while creating process: " + e.getMessage());
                    }
                    break;

                case 3:
                    display_systemcall();
                    break;

                case -1:
                    try { 
                        LoadThread.interrupt(); // Use interrupt here to exit the loop and finish its code
                        LoadThread.join();
                    } catch (Exception e) {
                        System.out.println("Error occurred while exiting: " + e.getMessage());
                    }
                    System.out.println("Exiting...");
                    System.out.println("Good bye ");
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (option == -1) {
                try { 
                    readThread.join();
                    LoadThread.interrupt(); // Use interrupt here to exit the loading loop
                    LoadThread.join();
                    ExcuteThread.join();
                    killProcessThread.join();
                } catch (Exception e) {
                    System.out.println("Error occurred while handling threads: " + e.getMessage());
                }
                System.out.println("Exiting...");
                System.out.println("Good bye ");
            }
        } while (option != -1);
    }

    public int positive_Num(int num) { // Make user enter positive number
        if (num < 0) {
            do {
                System.out.println("Enter a positive number");
                System.out.print("-->");
                num = s.nextInt();
            } while (num < 0);
        }
        return num;
    }

    public void display_systemcall() {
        int option = 0;
        do {
            System.out.println(lineOfStars);
            System.out.println("*             System calls              *");
            System.out.println("*                                       *");
            System.out.println("*  Please select an option:             *");
            System.out.println("*   1.Load a process to ready queue     *");
            System.out.println("*   2.Load all process to ready queue   *");
            System.out.println("*   3.Kill a process                    *");
            System.out.println("*   4.Print Job queue                   *");
            System.out.println("*   5.Print Ready queue                 *");
            System.out.println("*   6.Print All queue                   *");
            System.out.println("*   7.Print Current Memory              *");
            System.out.println("*   8.Print Maximum Memory              *");
            System.out.println("*   9.Execute # of process (pick algo)  *");
            System.out.println("*   10.Execute all process (pick algo)  *");
            System.out.println("*   11.Execute all process (all algo)   *");
            System.out.println("*   12.Best Performance algorithm       *");
            System.out.println("*   13.Display Process Info             *");
            System.out.println("*   14.Display First Process Info       *");
            System.out.println("*                                       *");
            System.out.println("*   -2.Back To Main Menu                *");
            System.out.println("*   -1.Exit                             *");
            System.out.println(lineOfStars);
            System.out.print("-->");
            try { 
                option = s.nextInt();
            } catch (Exception e) { 
                System.out.println("Invalid input. Please enter a valid option.");
                s.nextLine(); // Clear the buffer
                continue;
            }
            System.out.println();

            switch (option) {
                case 1:
                    m.load();
                    break;

                case 2:
                    LoadThread = new Thread(new MyRunnable(ThreadState.LoadToJobQueue, m));
                    LoadThread.setDaemon(true); // if main dies it dies
                    LoadThread.start();
                    System.out.println("Starting Loading...");
                    break;

                case 3:
                    System.out.println("Enter the Process ID to kill:");
                    System.out.print("-->");
                    int pid = s.nextInt();
                    System.out.println();
                    killProcessThread = new Thread(new MyRunnable(ThreadState.KillProcess, m, pid));
                    killProcessThread.start();
                    try {  
                        killProcessThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    m.print_JobQueue();
                    break;

                case 5:
                    m.print_ReadyQueue();
                    break;

                case 6:
                    m.print_allQueue();
                    break;

                case 7:
                    m.print_Memory();
                    break;

                case 8:
                    m.printMaxmemory();
                    break;

                case 9:
                    try { 
                        System.out.println("Enter the number of process to execute:");
                        System.out.print("-->");
                        int numOfProccess = s.nextInt();
                        System.out.println();
                        System.out.println("Please select an option:");
                        System.out.println("1. First Come First Serve");
                        System.out.println("2. Priority");
                        System.out.println("3. Round Robin");
                        System.out.println("-2 Back to System Calls");
                        System.out.println("-1. Exit");
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

                            case -2:
                            case -1:
                                break;

                            default:
                                System.out.println("Invalid option");
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error occurred while executing process: " + e.getMessage());
                    }
                    break;

                // The rest of the options can follow the same structure as above.

                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option != -1 && option != -2);
    }

    public static void main(String[] args) {
        OldView v = new OldView();
        v.display_mainMenu();
    }
}
