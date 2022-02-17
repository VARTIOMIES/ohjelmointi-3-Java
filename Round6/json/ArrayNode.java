/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class ArrayNode extends Node implements Iterable<Node>{
    
    private ArrayList<Node> list;
    
    public ArrayNode(){
        list = new ArrayList<>();
    }
    
    public void add(Node node){
        list.add(node);
    }
    
    public int size(){
        return list.size();
    }
    
    private class ArrayNodeIterator implements Iterator<Node>{
        private int currentIndex;
        public ArrayNodeIterator(){
            currentIndex=0;
        }
        @Override
        public boolean hasNext(){
            return currentIndex<list.size();
        }
        
        @Override 
        public Node next(){
            
            return list.get(currentIndex++);
            
        }
    }
    @Override
    public Iterator<Node> iterator(){
        return new ArrayNodeIterator();
    }
            
        
    
}
