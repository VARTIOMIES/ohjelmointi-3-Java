/*
    @author Onni Merila
    onni.merila@tuni.fi
    H299725
 */

package fi.tuni.prog3.round8.xmlcountries;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CountryData {



    /*
    Reads data from three different files. Combines the data and creates a
    Country-object from that data. This will be done to every datapoint and
    finally a list of every country will be returned

    @param areaFile, Filename of the XML-file containing area-info
    @param populationFile, Filename of the XML-file containing population-info
    @param gdbFile, Filename of the XML-file containing gdb-info
    @returns List<Country>, All Country:s created from the data
     */
    public static List<Country> readFromXmls(String areaFile,
                                             String populationFile,
                                             String gdpFile){
        // *****Read file
        TreeMap<String, String> areaData;
        TreeMap<String, String> populationData;
        TreeMap<String, String> gdpData;

        try {
            areaData = readXmlFile(areaFile);
            populationData = readXmlFile(populationFile);
            gdpData = readXmlFile(gdpFile);
        }
        catch (IOException|JDOMException e){
            System.out.println("Something went wrong when reading the files");
            return null;
        }
        //****** End of read file

        // When all files are read, let's create Country's from that data
        List<Country> countries = new ArrayList<>();

        for(String countryname : areaData.keySet()){
            //Check that a country has all needed data to be created
            if (populationData.containsKey(countryname) &&
                    gdpData.containsKey(countryname)){
                // Extract that data
                double area = Double.parseDouble(areaData.get(countryname));
                long population = Long.parseLong(populationData.get(countryname));
                double gdb = Double.parseDouble(gdpData.get(countryname));
                // Create a new Country from that extracted data and add to list
                countries.add(new Country(countryname,area,population,gdb));
            }

        }
        return countries;
    }
    /*
    Reads an XML file expected to be in a specific order.

    @param filename, Name of the XML-file in the same directory with this class
     */
    private static TreeMap<String,String> readXmlFile(String filename)
            throws IOException,JDOMException{

        TreeMap<String,String> countryDataPoint = new TreeMap<>();

        final SAXBuilder sax = new SAXBuilder();
        Document doc = sax.build(filename);

        Element root = doc.getRootElement();
        Element data = root.getChild("data");

        List<Element> records = data.getChildren("record");

        for (Element record : records){
            // Get all fields
            List<Element> fields = record.getChildren("field");
            // Country name
            String countryName = fields.get(0).getValue();

            // Value of the country
            String valueStr = fields.get(2).getValue();


            // Add read data to our container to be used later on. If there is
            // not already an instance of specific country, add it in container
            if (!countryDataPoint.containsKey(countryName)){
                countryDataPoint.put(countryName,valueStr);
            }

        }

        return countryDataPoint;

    }

    public static void writeToXml(List<Country> countries, String countryFile)
            throws IOException {

        // Create the Element tree
        Element countriesElement = new Element("countries");
        for (Country country : countries){
            Element name = new Element("name");
            name.addContent(country.getName());

            Element area = new Element("area");
            area.addContent(String.format("%.0f",country.getArea()));

            Element population = new Element("population");
            population.addContent(String.format("%d",country.getPopulation()));

            Element gdp = new Element("gdp");
            gdp.addContent(String.format("%.0f",country.getGdp()));

            Element countryElement = new Element("country");
            countryElement.addContent(name);
            countryElement.addContent(area);
            countryElement.addContent(population);
            countryElement.addContent(gdp);

            countriesElement.addContent(countryElement);
        }
        // Compile element tree into a Document
        Document countriesBuild = new Document(countriesElement);

        // Crate new file and write into it
        File writeFile = new File(countryFile);
        FileWriter fileWriter = new FileWriter(writeFile);
        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        xout.output(countriesBuild,fileWriter);

    }



}
