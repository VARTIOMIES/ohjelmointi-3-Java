
package fi.tuni.prog3.junitorder;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */

public class OrderTest {

    @Test
    public void testAddItemsWithItem(){

        Order order = new Order();
        Order.Item item = new Order.Item("Peruna",2.5);
        int count = 1;

        if (order.addItems(item,count)){
            assertFalse(order.isEmpty());
        }
        assert true;
    }
    @Test
    public void testAddItemsWithString(){

        Order order = new Order();
        String name = "Peruna";
        Order.Item item = new Order.Item(name,1.0);
        order.addItems(item,1);

        assertTrue(order.addItems(name,1));

    }

    @Test
    public void testGettersModifying(){
        Order order = new Order();

        order.addItems(new Order.Item("porkkana",2.5),10);

        assertEquals(order.getEntries(),order.getEntries());
        assertEquals(order.getEntryCount(),order.getEntryCount());
        assertEquals(order.getItemCount(),order.getItemCount());
    }

    @Test
    public void testGetEntries(){
        Order order = new Order();

        List<Order.Entry> expectedList = new ArrayList<>();

        Order.Item item1 = new Order.Item("Peruna",2.4);
        Order.Item item2 = new Order.Item("Porkkana",3.5);
        Order.Item item3 = new Order.Item("Päärynä",1.1);
        Order.Item item4 = new Order.Item("Jooseppi",200.0);

        order.addItems(item1,3);
        order.addItems(item2,2);
        order.addItems(item3,1);
        order.addItems(item4,2);

        Order.Entry entry1 = new Order.Entry(item1,3);
        Order.Entry entry2 = new Order.Entry(item2,2);
        Order.Entry entry3 = new Order.Entry(item3,1);
        Order.Entry entry4 = new Order.Entry(item4,2);

        expectedList.add(entry1);
        expectedList.add(entry2);
        expectedList.add(entry3);
        expectedList.add(entry4);

        assertEquals(expectedList,order.getEntries());
    }

    @Test
    public void testCounterMethodsCalculations(){

        Order order = new Order();
        Order.Item item1 = new Order.Item("Peruna",2.4);
        Order.Item item2 = new Order.Item("Porkkana",3.5);

        order.addItems(item1,3);
        order.addItems(item2,6);
        assertEquals(9,order.getItemCount());
        assertEquals(2,order.getEntryCount());

    }


    @Test
    public void testPricing(){
        Order order = new Order();

        // Create items
        Order.Item item1 = new Order.Item("peruna",5.5);
        Order.Item item2 = new Order.Item("porkkana",3.3);
        Order.Item item3 = new Order.Item("jooseppi",25.2);

        // Add items
        order.addItems(item1,5);
        order.addItems(item2,4);
        order.addItems(item3,1);

        // Entries
        Order.Entry entry = new Order.Entry(item2,4);

        // Is the pricing correct?
        double expectedPrice = 5*5.5 + 4*3.3 + 25.2;
        assertEquals(expectedPrice,order.getTotalPrice());
        assertEquals(5.5,item1.getPrice());

        assertEquals(3.3,entry.getUnitPrice());

    }

    @Test
    public void testEmptiness(){
        Order order = new Order();
        assertEquals(order.getItemCount() == 0,order.isEmpty());

        order.addItems(new Order.Item("penis",2.4),2);

        assertEquals(order.getItemCount()!=0,!order.isEmpty());

    }

    @Test
    public void testRemovingItem(){
        Order order = new Order();

        Order.Item item1 = new Order.Item("peruna",5.5);

        order.addItems(item1,5);

        order.removeItems("peruna",3);
        assertEquals(2, order.getItemCount());

        order.removeItems("peruna",2);
        assertTrue(order.isEmpty());

    }


    @Test
    public void testGetItemName(){
        String name = "Peruna";

        // Test both name-getters

        Order.Item item = new Order.Item(name,4);
        assertEquals(name,item.getName());
        Order.Entry entry = new Order.Entry(item,1);
        assertEquals(name,entry.getItemName());

    }

    @Test
    public void testEquals(){
        Order.Item item1 = new Order.Item("Peruna",2.0);
        Order.Item item2 = new Order.Item("Peruna",1.0);

        //assertEquals(item1, item2);
        assertTrue(item1.equals(item2));

    }

    @Test
    public void testToString(){
        Order.Item item = new Order.Item("Peruna",2.1);
        Order.Entry entry = new Order.Entry(item,2);

        assertEquals("Item(Peruna, 2.10)",item.toString());
        assertEquals(String.format("2 units of %s",item),entry.toString());

    }

    @Test
    public void testExceptionThrowing(){

        Order.Item item1 = new Order.Item("Peruna",2.59);
        Order.Item item2 = new Order.Item("Peruna",4.66);

        Order order1 = new Order();
        assertThrows(IllegalArgumentException.class,()->order1.addItems(item1,-1));

        Order order2 = new Order();
        order2.addItems(item1,2);
        assertThrows(IllegalStateException.class,()->order2.addItems(item2,4));

        Order order3 = new Order();
        order3.addItems(item1,3);
        assertThrows(NoSuchElementException.class,()->order3.addItems("Jooseppi",2));
        assertThrows(IllegalArgumentException.class,()->order3.addItems("Peruna",-1));

        Order order4 = new Order();
        order4.addItems(item1,3);
        assertThrows(IllegalArgumentException.class,()->order4.removeItems("Peruna",-1));
        assertThrows(IllegalArgumentException.class,()->order4.removeItems("Peruna",4));
        assertThrows(NoSuchElementException.class,()->order4.removeItems("Jooseppi",2));


        assertThrows(IllegalArgumentException.class,()-> new Order.Item(null,1));
        assertThrows(IllegalArgumentException.class,()->new Order.Item("Kukka",-1.0));

        assertThrows(IllegalArgumentException.class,()-> new Order.Entry(item1,-1));

    }




}
