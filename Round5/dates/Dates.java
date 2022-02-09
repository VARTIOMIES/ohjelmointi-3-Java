/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */
import java.time.LocalDate;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.time.format.ResolverStyle;

public class Dates {
    
    
    public static DateDiff[] dateDiffs(String[] dateStrs){
        
        ArrayList<LocalDate> validDate = new ArrayList<>();
        // Go through all given date inputs and check if they are valid. If so
        // then add that LocalDate to validDate ArrayList. Prints error message
        // if date is not valid.
        for (String s : dateStrs){
            try{
                // Creates formatter to accept all wanted formats
                DateTimeFormatter formatteri = new DateTimeFormatterBuilder()
                        .appendOptional(DateTimeFormatter.ofPattern("d.M.yyyy"))
                        .appendOptional(DateTimeFormatter.ofPattern("dd.M.yyyy"))
                        .appendOptional(DateTimeFormatter.ofPattern("d.MM.yyyy"))
                        .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
                        .toFormatter();
                
                LocalDate temp = LocalDate.parse(s,formatteri);
                
                if(temp.getDayOfMonth() == 28 && temp.getMonthValue() == 2 && 
                        !temp.isLeapYear()){
                    throw new DateTimeException(null,null);
                }
                validDate.add(temp);
            }
            catch(DateTimeException e){
                System.out.format("The date \"%s\" is illegal!%n",s);
            }
        }
        
        // Sort all valid dates in order from oldest to newest
        validDate.sort(LocalDate::compareTo);
        
        // Create arrayList for DateDiffs to be created in the next loop
        ArrayList<DateDiff> dateDiffs = new ArrayList<>();
        
        for (int i = 0 ; i <= validDate.size()-2 ; i ++){
            dateDiffs.add(new DateDiff(validDate.get(i).toString(),validDate.get(i+1).toString()));
        }
        
        // Convert to normal Array and return

        DateDiff[] normalArray = new DateDiff[dateDiffs.size()];
        normalArray = dateDiffs.toArray(normalArray);
        return normalArray;
        
    }
    
    public static DateDiff[] dateDiffs(String dateStr1, String dateStr2){
        return dateDiffs(new String[]{dateStr1,dateStr2});
    }
    
    
    public static class DateDiff{
        private String start;
        private String end;
        private int diff;
        
        public DateDiff(String start, String end){
            
            this.start = start;
            this.end = end;
            
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            
            this.diff = (int)startDate.datesUntil(endDate).count();
            
        }
        
        // Getter functions
        public String getStart(){return start;}
        
        public String getEnd(){return end;}
        
        public int getDiff(){return diff;}
        
        // String function
        @Override
        public String toString(){
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            
            // Create the wanted day format
            DateTimeFormatter formatteri = DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy",java.util.Locale.US);
            
            // Combine all info in one String
            String printStr = formatteri.format(startDate)
                    + " --> "
                    + formatteri.format(endDate)
                    + String.format(": %d days",this.diff);
            
            return printStr;
        }
        
    }
}
