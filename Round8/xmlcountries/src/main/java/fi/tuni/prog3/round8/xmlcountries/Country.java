/*
    @author Onni Merila
    onni.merila@tuni.fi
    H299725
 */
package fi.tuni.prog3.round8.xmlcountries;

public class Country implements Comparable<Country>{

    // Private elements
    private final String name;
    private final double area;
    private final long population;
    private final double gdb;

    public Country(String name, double area, long population,double gdb){
        this.name=name;
        this.area=area;
        this.population=population;
        this.gdb=gdb;
    }


    // Needed compare function to implement Comparable. Compares countries
    // by their name.
    @Override
    public int compareTo(Country o) {
        return this.name.compareTo(o.name);
    }

    // Obvious toString method to give info in wanted format
    @Override
    public String toString(){
        return String.format("%s%n" +
                "  Area: %.0f km2%n" +
                "  Population: %d%n" +
                "  GDP: %.0f (2015 USD)%n",
                name,area,population,gdb);
    }

    // Getter methods
    public String getName(){return name;}
    public double getArea(){return area;}
    public long getPopulation(){return population;}
    public double getGdb(){return gdb;}






}
