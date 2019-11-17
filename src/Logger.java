/**
 * Created by Jan on 15.11.2019.
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
     static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());


    public static void CreateLog(){

        new File(System.getProperty("user.dir") +  "\\Logs").mkdir();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Logs\\Log" + timeStamp + ".txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        writer.println("log for computation on " + timeStamp);
        writer.close();
    }

    public static void  addToLog(String addition){
        try
        {
            String filename= "Logs\\Log" + timeStamp + ".txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(addition + "\n");//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
