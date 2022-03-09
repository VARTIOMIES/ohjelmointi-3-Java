package fi.tuni.prog3.round8.jsoncountries;

/*
    @author - Onni Merila , onni.merila@tuni.fi , H299725
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CountryData {


    public static List<Country> readFromJsons(String areaFile,
                                              String populationFile,
                                              String gdpFile){
        // *****Read file
        TreeMap<String, String> areaData;
        TreeMap<String, String> populationData;
        TreeMap<String, String> gdpData;

        try {
            areaData = readJsonFile(areaFile);
            populationData = readJsonFile(populationFile);
            gdpData = readJsonFile(gdpFile);
        }
        catch (IOException e){
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
    Function to read a Json file with Gson. HOLY MOLY What a spaghetti, but at
    this point I have already used way too much time to learn the usage of GSON
    for only a few points, so it shall be left like this.

    Basically it just goes through the file with JsonReader and extracts wanted
    data from the Json file.
     */
    private static TreeMap<String,String> readJsonFile(String filename)
            throws IOException{

        TreeMap<String,String> countryDataPoint = new TreeMap<>();

        // Read the text file containing json
        Gson gson = new Gson();

        FileReader fileReader = new FileReader(filename);
        JsonReader reader = gson.newJsonReader(fileReader);

        // Go to root
        reader.beginObject();
        while (reader.hasNext()){
            String a = reader.nextName();
            if (a.equals("Root") | a.equals("data")){
                reader.beginObject();
            }
            else if (a.equals("record")){
                reader.beginArray();

                // Read all field arrays
                while (reader.hasNext()) {
                    reader.beginObject();
                    while(reader.hasNext()) {
                        String b = reader.nextName();
                        if (b.equals("field")) {
                            reader.beginArray();
                            String storeValue = "";
                            String countryName = "";
                            String value = "";

                            while (reader.hasNext()) {
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    String c = reader.nextName();
                                    if (c.equals("value")) {
                                        storeValue = reader.nextString();

                                    } else if (c.equals("attributes")) {
                                        reader.beginObject();
                                        while (reader.hasNext()) {
                                            String d = reader.nextName();
                                            if (d.equals("name")) {
                                                String nextString = reader.nextString();
                                                if (nextString.equals("Country or Area")) {
                                                    countryName = storeValue;

                                                } else if (nextString.equals("Value")) {
                                                    value = storeValue;
                                                }
                                            } else if (d.equals("key")){
                                                //reader.nextString();
                                                reader.skipValue();
                                            }
                                            else {
                                                //reader.nextString();
                                                reader.skipValue();
                                            }
                                        }
                                        reader.endObject();

                                    } else {
                                        reader.skipValue();
                                    }
                                }
                                reader.endObject();

                            }
                            reader.endArray();

                            countryDataPoint.put(countryName,value);

                        } else {
                            reader.skipValue();
                        }


                    }
                    reader.endObject();
                }

                reader.endArray();

            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.endObject();
        reader.endObject();

        reader.close();

        return countryDataPoint;

    }
    /*
    A very compact function to write a list of Countries beautifully into a file
     */
    public static void writeToJson(List<Country> countries, String countryFile)
            throws IOException {
        // Create instance of gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Create output file and printer into it
        PrintStream output = new PrintStream(countryFile);
        // Just simply transfer the list to json with gsons own method and write
        // it to the file
        output.print(gson.toJson(countries));

    }
}
