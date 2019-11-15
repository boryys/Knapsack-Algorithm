/**
 * Created by Jan on 15.11.2019.
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    public  void CreateLog(){

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Log" + timeStamp, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        writer.println("log for computation on " + timeStamp);
        writer.close();
    }

    public void addToLog(){
        String logAddition=null;//TODO
        try
        {
            String filename= "Log" + timeStamp + ".txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(logAddition + "\n");//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
