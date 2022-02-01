/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author Onni Merilä
 */

//ackage student.stuff;

public class Student {
    private String studentName;
    private String studentNumberStr;
    
    //Constructor
    public Student(String name, String studentNumber){
        studentName = name;
        studentNumberStr = studentNumber;
    }
    
    // Getter memberfunctions
    public String getName(){
        return studentName;
    }
    
    public String getStudentNumber(){
        return studentNumberStr;
    }
    

}
