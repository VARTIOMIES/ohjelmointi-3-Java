/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */
public class Circle implements IShapeMetrics{
    
    private double radius;
    
    public Circle(double radius){
        this.radius = radius;
    }
    
    public String toString(){
        return String.format("Circle with radius: %.2f",radius);
    }
    
    public String name(){
        return "circle";
    }
    
    public double area(){
        return PI*radius*radius;
    }
    
    public double circumference(){
        return PI*radius*2.0;
    }
}
