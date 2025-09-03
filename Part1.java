
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;
import java.io.*;
public class Part1 {
    public void totalBirths(FileResource fr){
        int totalBirths=0;
        int numGirls=0;
        int numBoys = 0;
        int numNames=0;
        for (CSVRecord rec: fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths+=numBorn;
            numNames++;
            if(rec.get(1).equals("F")){
                numGirls++;
            }
            if(rec.get(1).equals("M")){
                numBoys++;
            }
        }
        System.out.println("total births = "+totalBirths);
        System.out.println("Number of Girl's Names = "+numGirls);
        System.out.println("Number of Boy's Names = "+numBoys);
        System.out.println("Total number of names = "+numNames);
    }
    public void testTotalBirths(){
        FileResource fr = new FileResource("yob1905.csv");
        totalBirths(fr);
    }
    //returns the rank of the name in the file
    //gender is "F" or "M"
    public int getRank(int year, String name, String gender){
        FileResource fr = new FileResource("yob"+year+".csv");
        int rank = 0;
        for(CSVRecord rec : fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                rank++;
            if(rec.get(0).equals(name)){
              return rank;  
            }
        }
        }
        
            return -1;
        }
        
    
    public void testGetRank(){
        System.out.println("Frank is ranked "+getRank(1971, "Frank", "M"));
    }
    // returns the name of the person with the given rank, gender must be in the form "M" or "F"
    public String getName(int year, int rank, String gender){
        FileResource fr = new FileResource("yob"+year+".csv");
        int currentRank=0;
        for(CSVRecord rec : fr.getCSVParser(false)){
            
            if(rec.get(1).equals(gender)){
                currentRank++;
            
                if(currentRank==rank){
                    
                    
                    return rec.get(0);
                }
            }
        }
        return "NO NAME";
    }
    public void testGetName(){
        System.out.println("The boy ranked 450 is "+getName(1982, 450, "M"));
    }
    //determines what name someone would have if they were born in a different year based on the popularity of their name
    public String whatIsNameInYear(String name, int year, int newYear, String gender){
        int rank = getRank(year, name, gender);
        String newName= getName(newYear, rank, gender);
        return newName;
    }
    public void testWhatIsNameInYear(){
        String newName= whatIsNameInYear("Owen", 1974, 2014,  "M");
        System.out.println("Owen born in 1974 would be "+newName+" if she was born in 2014");
    }
    //selects a range of files and returns the year with the highest rank for the input name and gender
    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        int currRank=-1;
        int finalRank = -1;
        int finalYear = -1;
        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f); 
            int rank = 0;
            String yearName = f.getName();
            int year = Integer.parseInt(yearName.substring(3, 7));
            for(CSVRecord rec : fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                rank++;
            if(rec.get(0).equals(name)){
                currRank = rank;
                if(finalRank == -1){
                    finalRank = rank;
                    finalYear = year;
                }
              
              else if(currRank<finalRank){
                  finalRank = currRank;
                  finalYear = year;
              }
            }
            }
        }
        
          
        }
          return finalYear;
    }
    public void testYearOfHighestRank(){
        System.out.println(yearOfHighestRank("Mich", "M"));
    }
    public double getAverageRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        double avgRank = -1.0;
        int count=0;
        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource (f);
            String yearName = f.getName();
            int year = Integer.parseInt(yearName.substring(3, 7));
            int currRank = getRank(year, name, gender);
            count++;
            if (currRank ==-1){
                break;
            }
            else if (avgRank ==-1.0){
                avgRank = currRank;
            }
            else{
                avgRank +=currRank;
            }
        }
        if(avgRank ==-1){
            return -1;
        }
        return (avgRank/count);
    }
    public void testGetAverageRank(){
        System.out.println(getAverageRank("Robert", "M"));
    }
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        FileResource fr = new FileResource("yob"+year+".csv");
        int rank = getRank(year, name, gender);
        int i = 1;
        int count = 0;
        
            for(CSVRecord rec : fr.getCSVParser(false)){
                if(i<rank){
                if(rec.get(1).equals(gender)){
                
                    int numBorn = Integer.parseInt(rec.get(2));
                    //System.out.println("Number born: "+numBorn);
                    count+=numBorn;
                    //System.out.println("CurrentTotal count: "+count);
                    i++;
                }
            }
        }
        return count;
    }
    public void testGetTotalBirthsRankedHigher(){
        System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M"));
    }
}

