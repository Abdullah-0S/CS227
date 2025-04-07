import java.io.IOException;

public class MyRunnable implements Runnable {
    private ThreadState state;
    private model m;
    private int killProcessWithPid;
    private String fileName;
    
    public MyRunnable(ThreadState state, model model){
        this.state = state;
        m = model;
    }
    
    public MyRunnable(ThreadState state, model m, int killProcessWithPid) {
        this.state = state;
        this.m = m;
        this.killProcessWithPid = killProcessWithPid;
    }

    public MyRunnable(ThreadState state, model m, String fileName) {
        this.state = state;
        this.m = m;
        this.fileName = fileName;
    }

  
    public int getKillProcessWithPid() {
        return killProcessWithPid;
    }

    public void setKillProcessWithPid(int killProcessWithPid) {
        this.killProcessWithPid = killProcessWithPid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ThreadState getState() {
        return state;
    }

    public void setState(ThreadState state) {
        this.state = state;
    }

    public model getM() {
        return m;
    }

    public void setM(model m) {
        this.m = m;
    }

    @Override
    public void run(){
        switch(state)
        {
            case read:
            try
            {
                m.read(this.fileName);
            }
            catch (IOException e)
            {
                System.out.println("Error in reading");
            }
            break;

            case LoadToJobQueue:
                  while(!Thread.currentThread().isInterrupted())
                  {
                    try{
                        Thread.sleep(2000); 
                        m.loadAll_ProcessWithoutPrinting();
                    }catch(InterruptedException e)
                    {
                        break;                   
                    }
                  }
                  System.out.println("End of loadding");
            break;

            case LoadToJobQueueWithComments:
                  while(!Thread.currentThread().isInterrupted())
                  {
                    try{
                        Thread.sleep(4000); 
                        m.loadAll_Process();
                    }catch(InterruptedException e)
                    {
                        break;                   
                    }
                  }
                  System.out.println("End of loadding");    
            break;

            case Execute_all_algorithms:
                m.execute(Algorithm.All, -1); 
            break;

            case KillProcess:
                m.killProcess(killProcessWithPid);
            break;
        }  
    }
}
enum ThreadState
{
    read,
    LoadToJobQueue,
    LoadToJobQueueWithComments,
    Execute_all_algorithms,
    KillProcess
}
