package ebs.lab.Lab07;
import java.io.*;

public class LoadBalancerClass {
	 public static void readFile(String filename) throws IOException{
	        FileInputStream in = null;
	        //FileOutputStream out = null;

	      try {
	         in = new FileInputStream(filename);
	         
	         int c;
	         while ((c = in.read()) != -1) {
	            System.out.print((char)c);
	         }
	      }finally {
	         if (in != null) {
	            in.close();
	         }
	      }
	        
	    }
	    
	    public static void readFileLine(String filename){
	         // The name of the file to open.
	        // This will reference one line at a time
	        String line = null;

	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(filename);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	                System.out.println(line);
	            }   

	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                filename + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + filename + "'");                  
	            // Or we could just do this: 
	            // ex.printStackTrace();
	        }
	    }
	    
	    public static void writeFileLine(String filename){
	        // The name of the file to open.

	        try {
	            // Assume default encoding.
	            FileWriter fileWriter =
	                new FileWriter(filename);

	            // Always wrap FileWriter in BufferedWriter.
	            BufferedWriter bufferedWriter =
	                new BufferedWriter(fileWriter);

	            // Note that write() does not automatically
	            // append a newline character.
	            bufferedWriter.write("Hello there,");
	            bufferedWriter.write(" here is some text.");
	            bufferedWriter.newLine();
	            bufferedWriter.write("We are writing");
	            bufferedWriter.write(" the text to the file.");

	            // Always close files.
	            bufferedWriter.close();
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error writing to file '"
	                + filename + "'");
	            // Or we could just do this:
	            // ex.printStackTrace();
	        }
	    }
	    public static void runApplication(String path) throws IOException{
	        
	        Process process = Runtime.getRuntime().exec("rs.exe");
	        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line = null;
	        while((line = in.readLine()) != null) {
	            System.out.println(line);
	        }

	    }
}
