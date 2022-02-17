/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */
import java.util.TreeMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
public class ObjectNode extends Node implements Iterable<String>{
    
    private TreeMap<String,Node> nodes;
    
    public ObjectNode(){
        nodes = new TreeMap<>();
    }
    
    public Node get(String key){
        if (nodes.containsKey(key)){
            return nodes.get(key);
        }
        return null;
    }
    
    public void set(String key,Node node){
        nodes.put(key, node);
    }
    
    public int size(){
        return nodes.size();
    }
    
    @Override
    public Iterator<String> iterator(){
        return new ObjectNodeIterator();
    }

    
    
    
    private class ObjectNodeIterator implements Iterator<String> {
        
        
        private int currentIndex;
        private ArrayList<String> list;
        
        private ObjectNodeIterator(){
            currentIndex=0;
            list = new ArrayList<>();
            list.addAll(nodes.keySet());
        }
        
        @Override
        public boolean hasNext(){
            return currentIndex<list.size();
        }
        @Override
        public String next(){
            return list.get(currentIndex++);
                
        }
    }
    
    
    
}
