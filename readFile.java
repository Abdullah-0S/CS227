import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
public class readFile {

    // This Function will read from filePath and return a list of the value of each line in this order 
    //{pid "Proccess ID", burstTime_In_ms "burst Time In ms" , priority of proccess, reqMemory " Memory Required in MB"}
    public static List<int[]> read(String filePath)
    {    
        List<int[]> list = new LinkedList<>();
        list.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() ) {
                    continue;
                }
                                                        // 1:2:3:4;1024
                String[] parts = line.split(";"); // { 1:2:3:4 , 1024 }
                
                String[] values = parts[0].split(":"); // { 1, 2 ,3 ,4}
                
                int pid = Integer.parseInt(values[0]);
                int burstTime_In_ms = Integer.parseInt(values[1]);
                int priority = Integer.parseInt(values[2]);
                int reqMemory = Integer.parseInt(parts[1]);

                list.add(new int[] {pid, burstTime_In_ms, priority, reqMemory});

            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        
        }
        return list; 
    }
// Testing
    public static void main(String[] args) {
        List<int[]> l = read("job.txt");
        for (int i = 0 ; i < l.size(); i++)
        {
            if(l.get(i).length != 4)
            {
                System.out.println("Error in line " + i + " in the file");
                continue;
            }
            System.out.println("ID: " + l.get(i)[0] + ", burst In ms: " + l.get(i)[1] + ", Priority: " + l.get(i)[2] + ", Required Memory in MB : " + l.get(i)[3]);
        }
    }

}