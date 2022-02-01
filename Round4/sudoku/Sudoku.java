/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */
import java.util.ArrayList;

public class Sudoku {

    private ArrayList<ArrayList<Character>> field = new ArrayList<>();
    
    // Contructor
    public Sudoku(){
        for (int i = 0;i<=8;i++){
            ArrayList<Character> pysty = new ArrayList<>();
            for (int j = 0;j<=8;j++){
                pysty.add(' ');
            }
            field.add(pysty);
        }
    }
    
    public void set(int i, int j, char c){
        
        ArrayList<Character> allowedCharacters = new ArrayList<>();
        java.util.Collections.addAll(allowedCharacters,' ','1','2',
                '3','4','5','6','7','8','9');
        
        // Check if given coordinates are ok
        if (i > 8 || j > 8 || i < 0 || j < 0){
            System.out.format("Trying to access illegal cell (%d, %d)!%n",i,j);
            return;
        }
        
        // Check if given character is allowed
        
        for (Character allowed : allowedCharacters){
            if (c == allowed){
                // Adds the given character c to the sudoku field
                ArrayList<Character> pysty = field.get(j);
                pysty.set(i,c);
                return;
            }
        }
        System.out.format("Trying to set illegal character %s to (%d, %d)!%n",
                c,i,j);
    }
    
    public boolean check(){
        
        // First check every row
        for (int j = 0;j<field.size();j++){
            
            ArrayList<Character> rowCheckList = new ArrayList<>();
        
            for (var a : field){
                Character x = a.get(j);
                rowCheckList.add(x);
            }
            java.util.Collections.sort(rowCheckList);

            for (int i = 0;i < rowCheckList.size()-2;i++){
                if (rowCheckList.get(i).equals(rowCheckList.get(i+1))){
                    if (rowCheckList.get(i).equals(' ')){
                        continue;
                    }
                    System.out.format("Row %d has multiple %s's!%n",j,rowCheckList.get(i));
                    return false;
                    // False
                }
            }
        }
        
        // Then check every column
        for (int j = 0;j<field.size();j++){
            
            ArrayList<Character> columnCheckList = new ArrayList<>();
            
            for (int i = 0; i<field.get(j).size();i++){
                
                columnCheckList.add(field.get(j).get(i)); 
            }
            java.util.Collections.sort(columnCheckList);
            
            for (int i = 1; i<columnCheckList.size();i++){
                
                if (columnCheckList.get(i).equals(columnCheckList.get(i-1))){
                    if (columnCheckList.get(i).equals(' ')){
                        continue;
                    }
                    System.out.format("Column %d has multiple %s's!%n",j,columnCheckList.get(i));
                    return false;
                }
            }
           
        }
        
        // Then check every 3x3 cell
        for (int x = 0;x < field.size();x = x + 3){
            for (int y = 0;y < field.get(0).size(); y = y + 3){
                ArrayList<Character> cellCheckList = new ArrayList<>();
                
                // Adds every number from one cell to the checklist
                for (int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        cellCheckList.add(field.get(x+i).get(y+j));
                    }
                }
                
                java.util.Collections.sort(cellCheckList);
                
                for (int i = 1;i<cellCheckList.size();i++)
                {
                    if (cellCheckList.get(i).equals(cellCheckList.get(i-1))){
                        if (cellCheckList.get(i).equals(' ')){
                            continue;
                        }
                        System.out.format("Block at (%d, %d) has multiple %s's!%n",
                                y,x,cellCheckList.get(i));
                        return false;
                    }
                }
                
            }
        }
        return true;
    }
    
    public void print(){
        //System.out.println("Read the following sudoku:");
        
        System.out.println("#".repeat(37));
        // Goes through rows
        for (int i= 0;i<field.get(0).size();i++)
        {
            String output = "#";
            // Goes through columns
            for (int j=0;j<field.size();j++){
                // Format the output line
                output += String.format(" %s ",field.get(j).get(i));
                 if (j==2||j==5||j==8){
                     output += "#";
                 }
                 else{
                     output += "|";
                 }
            }
            System.out.println(output);
            // Prints parts of the ruudukko
            if (i==2||i==5||i==8){
                System.out.println("#".repeat(37));
            }
            else {
                System.out.println("#---+---+---#---+---+---#---+---+---#");
            }
        }
    }
}