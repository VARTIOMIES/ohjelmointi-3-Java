/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */
public class DateTime extends Date{
    private int hour;
    private int minute;
    private int second;
    
    
    DateTime(int year, int month, int day, int hour, int minute, int second)
     throws DateException {
        super(year,month,day);
        if (!(hour >= 0 && hour < 24 && minute >= 0 && minute <60 
                && second >= 0 && second <60)){
            throw new DateException(String.format("Illegal time %2d:%2d:%2d",
                    hour,minute,second));
        }
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
    
    public int getHour(){
        return this.hour;
    }
    public int getMinute(){
        return this.minute;
    }
    public int getSecond(){
        return this.second;
    }
    
    @Override
    public String toString(){
        String temp = super.toString();
        return String.format(temp + " %02d:%02d:%02d",hour,minute,second);
    }
}
