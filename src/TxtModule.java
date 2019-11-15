/**
 * Created by Jan on 15.11.2019.
 */
import java.io.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtModule {

    public static String Console(){

        String output="";
        Scanner scan = new Scanner(System.in);

        System.out.print("Do you want to input data manually, or from file?\n");
        System.out.print("(press 'M' for Manually, or 'F' for file)\n");

        String s1 = scan.next();
        if(s1.toUpperCase().contains("F") && s1.length()==1){
            System.out.print("Do you want to give a name of particular file in the program directory, or want to search for default?\n");
            System.out.print("(press 'P' for particular file, or 'D' for default)\n");

            String s2 = scan.next();
            if(s2.toUpperCase().contains("P") && s2.length()==1){
                System.out.print("name of the file, please: \n");

                String s3 = scan.next();
                output = selectParticularTxt(s3);
            }else if(s2.toUpperCase().contains("D") && s2.length()==1){
                output = selectDefaultTxt();
            }else{
                System.out.print("unknown option chosen, giving default values\n");
            }
        }else if(s1.toUpperCase().contains("M") && s1.length()==1){
            output = manualInput();
        }else{

            System.out.print(" unknown option chosen, giving default values\n");
        }
        return output;
    }

    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        /*data = new String(Files.readAllBytes(Path.get(fileName)));
        return data;*/
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;

        while((st = br.readLine()) != null){
            data = data + st + "\n";
        }
        return data;
    }

    public static File[] getFileList(){
        File f = new File(System.getProperty("user.dir"));
        File[] matchingFiles = f.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name){
                return name.endsWith("txt");
            }
        });
        return matchingFiles;
    }

    public static String selectDefaultTxt()
    {
        String output = "";
        File[] files = getFileList();
        if(files[0] != null) {
            try {
                output = readFileAsString(files[0].toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    public static String selectParticularTxt( String name){

        String output = "";
        File[] files = getFileList();
        boolean existsFile = false;

        for(int i=0;i<files.length;i++){
            if(files[i].getName().contains(name))
            {
                existsFile = true;
                name=files[i].getName();
            }
        }

        if(existsFile) {
            String directory = System.getProperty("user.dir") + "\\" + name;
            try {
                output = readFileAsString(directory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    public static String manualInput(){
        String output="";
        Scanner scan = new Scanner(System.in);

        System.out.print("input format: knapsack's length; ks's width; amount of boxes\n");
        String s = scan.nextLine();
        output = output + s + "\n";
        Integer amountOfBoxes = Integer.parseInt(s.substring(s.lastIndexOf(" ")+1));
        for(int i=0;i<amountOfBoxes;i++)
        {
            System.out.print("box no. " + (i+1) + "\n");
            System.out.print("input format: length; width; value\n");
            s = scan.nextLine();
            output = output + s + "\n";
        }

        return output;
    }

    public static Knapsack getKnapSack (String output){
        String[] parts = output.split(" ");
        Knapsack result = new Knapsack(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
        return result;
    }

    public static List<Rectangle> getRectangles(String output){
        List<Rectangle> result = new ArrayList<Rectangle>();
        String[] lines = output.split("\n");
        int ammountOfBoxes = Integer.parseInt(lines[0].substring(lines[0].lastIndexOf(" ")+1));
        for(int i=1;i<=ammountOfBoxes;i++){
            String[] attributes = lines[i].split(" ");
            Rectangle rec = new Rectangle(
                    i,
                    Integer.parseInt(attributes[0]),
                    Integer.parseInt(attributes[1]),
                    Integer.parseInt(attributes[2])
            );
            result.add(rec);
        }
        return result;
    }

}
