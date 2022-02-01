/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author Onni Merilä
 */

import java.util.ArrayList;
import java.util.TreeMap;

public class StudentRegister{
    private TreeMap<String,Student> studentsByNumber;
    private TreeMap<String,Student> studentsByName;
    private TreeMap<String,Course> coursesByCode;
    private TreeMap<String,Course> coursesByName;
    private TreeMap<String,ArrayList<Attainment>> register;
    
    StudentRegister(){
        studentsByNumber = new TreeMap<>();
        studentsByName = new TreeMap<>();
        coursesByCode = new TreeMap<>();
        coursesByName = new TreeMap<>();
        register = new TreeMap<>();
    }
    
    // Getter mamberfunctions
    public ArrayList<Student> getStudents(){
        return new ArrayList<>(studentsByName.values());
    }
    public ArrayList<Course> getCourses(){
        return new ArrayList<>(coursesByName.values());
    }
    
    public void addStudent(Student student){
        studentsByNumber.put(student.getStudentNumber(),student);
        studentsByName.put(student.getName(),student);
    }
    public void addCourse(Course course){
        coursesByCode.put(course.getCode(),course);
        coursesByName.put(course.getName(),course);
    }
    public void addAttainment(Attainment att){
        if (!register.containsKey(att.getStudentNumber())){
            register.put(att.getStudentNumber(),new ArrayList<>());
        }
        register.get(att.getStudentNumber()).add(att);
    }
    
    public void printStudentAttainments(String studentNumber, String order){
        if (!register.containsKey(studentNumber)){
            System.out.println("Unknown student number: "+ studentNumber);
        }
        
        System.out.format("%s (%s):%n",studentsByNumber.get(studentNumber).getName(),
                studentNumber);
        
        // Collection of attainments in the print order
        java.util.Collection<Attainment> printList;
        
        // depending on 'order' printList has values in different order.
        // Next part puts them in order!
        if ("by name".equals(order)){
            
            TreeMap<String,Attainment> temp = new TreeMap<>();
            for (Attainment att : register.get(studentNumber)){
                temp.put(coursesByCode.get(att.getCourseCode()).getName(),att);
            }
            printList = temp.values();
            
        }
        else if ("by code".equals(order)){
            
            TreeMap<String,Attainment> temp = new TreeMap<>();
            for (Attainment att : register.get(studentNumber)){
                temp.put(att.getCourseCode(),att);
            }
            printList = temp.values();
        }
        else {
             printList = register.get(studentNumber);
            
        }
        
        // Print every attainment. Goes thrpough all attainments of the student
        // in the order defined before and just prints them.
        for (Attainment att : printList){
                String coursecode = att.getCourseCode();
                String coursename = coursesByCode.get(att.getCourseCode()).getName();
                int grade = att.getGrade();
                System.out.format("  %s %s: %d%n",coursecode,coursename,grade);
            }
        
        
        
    }
    public void printStudentAttainments(String studentNumber){
      
        if (!register.containsKey(studentNumber)){
            System.out.println("Unknown student number: "+ studentNumber);
            return;
        }
        
        System.out.format("%s (%s):%n",studentsByNumber.get(studentNumber).getName(),
                studentNumber);
        
        for (Attainment att : register.get(studentNumber)){
            String coursecode = att.getCourseCode();
            String coursename = coursesByCode.get(att.getCourseCode()).getName();
            int grade = att.getGrade();
            System.out.format("  %s %s: %d%n",coursecode,coursename,grade);
            
        }
    }
    
}
