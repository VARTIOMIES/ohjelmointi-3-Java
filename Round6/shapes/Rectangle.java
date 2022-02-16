/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */
public class Rectangle implements IShapeMetrics {
    
    private double height;
    private double width;
    
    
    public Rectangle(double height,double width){
        this.height = height;
        this.width = width;
    }
    
    public String toString(){
        return String.format("Rectangle with height %.2f and width %.2f",height,width);
    }
    
    public String name(){
        return "rectangle";
    }
    
    public double area(){
        return height * width;
    }
    
    public double circumference(){
        return 2.0*height+2.0*width;
    }
}
