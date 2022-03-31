/** Not implemented Order class to get rid of red lines from OrderTest
 * @author Onni Merilä, onni.merila@tuni.fi
 */

package fi.tuni.prog3.junitorder;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class Order {

    public Order(){

    }

    public boolean addItems(Item item, int count)
            throws IllegalArgumentException,
            IllegalStateException{
        return true;

    }

    public boolean addItems(String name, int count)
            throws IllegalArgumentException,
            NoSuchElementException{
        return true;
    }

    public boolean isEmpty(){

        return true;

    }

    public List<Entry> getEntries(){
        return new ArrayList<>();
    }

    public int getEntryCount(){
        return 0;
    }

    public int getItemCount(){
        return 0;
    }

    public double getTotalPrice(){
        return 0.0;
    }

    public void removeItems(String name, int count){

    }

    public static class Item{

        public Item(String name, double price)
                throws IllegalArgumentException,
                NoSuchElementException{

        }
        public String getName(){
            return "null";
        }

        public double getPrice(){
            return 0.0;
        }

        public String toString(){
            return "null";
        }

        public boolean equals(){
            return false;
        }

    }

    public static class Entry{

        public Entry(Item item, int count)
                throws IllegalArgumentException{

        }

        public String getItemName(){
            return "null";
        }

        public double getUnitPrice(){
            return 0.0;
        }

        public Item getItem(){
            return new Item("nulL",0.0);
        }

        public int getCount(){
            return 0;
        }

        public String toString() {
            return "null";
        }



    }
}
