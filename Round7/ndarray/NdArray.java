
/**
 *
 * @author Onni Merila
 */

import java.util.AbstractCollection;
import java.util.Iterator;

public class NdArray<E> extends AbstractCollection<E>{
        //implements Iterable<E>{
    
    private Object[] container;
    private Integer[] dimLens;
    
    public NdArray(Integer firstDimLen,Integer ...furtherDimLens)
            throws NegativeArraySizeException{
        
        // If any dimension is negative, throw Exception
        checkPositive(firstDimLen);
        checkPositive(furtherDimLens);
        
        // Then create the arrays
        dimLens = new Integer[1+furtherDimLens.length];
        dimLens[0] = firstDimLen;
        
        for (int i = 1;i<dimLens.length;i++){
            dimLens[i] = furtherDimLens[i-1];
        }
        int size = firstDimLen;
        for (Integer dimLen : furtherDimLens){
            size *= dimLen;
        }
        container = new Object[size];
        
        
        
        
        
    }
    
    // Checks if all given Integers are positive
    private void checkPositive(Integer...i) throws NegativeArraySizeException{
        for (Integer a : i){
            if (a<0){
                throw new NegativeArraySizeException(
                        String.format("Illegal dimension size %d.",a));
                
            }
        }
    }
    @Override
    public int size(){return container.length;}
    
    @SuppressWarnings("unchecked")
    public E get(int...indices) throws IllegalArgumentException,
            IndexOutOfBoundsException{
        
        // First check if there are correct amount of indices given
        checkIndicesAmount(indices);
        
        // Then check if given indices belong to the dimensions
        checkIndicesInDimension(indices);
        
        // Then find the index of the wanted value
        int index = calculateIndex(indices);
        
        // Return item from calculated index
        return (E) container[index];
    }
    
    public void set(E item, int...indices) throws IllegalArgumentException,
            IndexOutOfBoundsException{
        // First check if there are correct amount of indices given
        checkIndicesAmount(indices);
        
        // Then check if given indices belong to the dimensions
        checkIndicesInDimension(indices);
        
        // Then find the index of the wanted value
        int index = calculateIndex(indices);
        
        // Put item in
        container[index] = item;
    }
    
    private void checkIndicesAmount(int...indices)
            throws IllegalArgumentException{
        if (indices.length != dimLens.length){
            throw new IllegalArgumentException(
                String.format("The array has %d dimensions but"
                        + " %d indices were given."
                        ,dimLens.length , indices.length ));
        }
    }
    
    private void checkIndicesInDimension(int...indices)
            throws IndexOutOfBoundsException{
        for (int i = 0; i < this.dimLens.length ; i++){
            if ( !(indices[i]>=0) || !(indices[i]<dimLens[i]) ){
                throw new IndexOutOfBoundsException(String.format(
                        "Illegal index %d for dimension %d of length %d."
                        ,indices[i] , 1+i , dimLens[i] ));
            }
        }
    }
    
    // Tässä taitaa olla joku bugi
    private int calculateIndex(int...indices){
        int index = 0;
        
        for (int i = indices.length, depth = 0; i > 0; i--,depth++){
            int multiplier = 1;
            for (int j = 0 ; j < depth;j++){
                 multiplier *= dimLens[dimLens.length-1-j];
            }
            index += indices[i-1]*multiplier;
        }
        return index;
    }
    
    public int[] getDimensions(){
        int[] benis = new int[dimLens.length];
        for (int i = 0; i < benis.length ; i++){
            benis[i] = dimLens[i];
        }
        return benis;
    }
    
    private class Iter<E> implements Iterator<E> {
        private int index = 0;
        @Override @SuppressWarnings("unchecked")
        public E next(){
            return (E) container[index++];
        }
        
        @Override
        public boolean hasNext(){
            return index < container.length;
        }
    }
    
    @Override
    public Iterator<E> iterator(){
        return new Iter<>();
    }
    
   
    
}
