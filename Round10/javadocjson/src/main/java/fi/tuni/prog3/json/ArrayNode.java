/**
 *A class for representing a JSON array.
 * @author Onni Merila
 */

package fi.tuni.prog3.json;

import java.util.Iterator;
import java.util.ArrayList;

/**
 *A class for representing a JSON array.
 */
final public class ArrayNode extends Node implements Iterable<Node>{
    /**
     *
     */
    final private ArrayList<Node> list;

    /**
     *Constructs an initially empty JSON array node.
     */
    public ArrayNode(){
        list = new ArrayList<>();
    }

    /**
     *Returns the number of JSON nodes stored in this JSON array.
     * @return the number of JSON nodes in this JSON array.
     */
    public int size(){
        return list.size();
    }

    /**
     *Adds a new JSON node to the end of this JSON array.
     * @param node - the new JSON node to be added.
     */
    public void add(Node node){
        list.add(node);
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

    /**
     *Returns a Node iterator that iterates the JSON nodes stored in this JSON array.
     * @return a Node iterator that iterates the JSON nodes stored in this JSON array.
     */
    @Override
    public Iterator<Node> iterator(){
        return new ArrayNodeIterator();
    }
            
        
    
}
