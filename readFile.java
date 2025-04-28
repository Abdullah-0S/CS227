import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class readFile {

    // This Function will read from filePath and return a list of the value of each line in this order 
    // {pid "Process ID", burstTime_In_ms "burst Time In ms", priority of process, reqMemory "Memory Required in MB"}
    public static List<int[]> read(String filePath) {    
        List<int[]> list = new LinkedList<>();
        list.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (!line.contains(";")) {
                    System.out.println("Skip line not same pattern "+line);
                    continue;
                }
                // 1:2:3:4;1024
                try {  
                    String[] parts = line.split(";"); // { 1:2:3:4 , 1024 }

                    String[] values = parts[0].split(":"); // { 1, 2 ,3 ,4 }

                    int pid = Integer.parseInt(values[0]);
                    int burstTime_In_ms = Integer.parseInt(values[1]);
                    int priority = Integer.parseInt(values[2]);
                    int reqMemory = Integer.parseInt(parts[1]);
                    if (pid < 0)
                    {
                        System.out.println("Skip line "+line+ ": Process ID must be greater than 0");
                        continue;
                    }
                    if (burstTime_In_ms <= 0 )
                    {
                        System.out.println("Skip line "+line+ ": burst time must be greater than 0");
                        continue;
                    }
                    if (reqMemory <= 0 )
                    {
                        System.out.println("Skip line "+line+ ":required memory should be greater than 0");
                        continue;
                    }
                    if (priority <= 0)
                    {

                        System.out.println("Skip line "+line+ ": priority must be greater than 0");
                        continue;
                    }
                   
                    list.add(new int[]{pid, burstTime_In_ms, priority, reqMemory});
                } catch (NumberFormatException e) {  
                    System.out.println("Error parsing numbers in line: " + line);
                } catch (ArrayIndexOutOfBoundsException e) {  
                    System.out.println("Error: Incorrect format in line: " + line);
                }
            }
        } catch (IOException e) {  
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
        return list; 
    }

    public static List<PCB> read_returnPcbs(String filePath) throws IOException {
        List<int[]> list = read(filePath);
        List<PCB> PcbList = new LinkedList<PCB>();
        for (int[] pcb : list) {   
            PcbList.add(new PCB(pcb));
        }
        return PcbList; 
    } 

    // Testing
    public static void main(String[] args) {
       read("job.txt");
    }
    
}
