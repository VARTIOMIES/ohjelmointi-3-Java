/*
    @author Onni Merila
 */

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

    /*
    Uncompresses a 7z file
    @param filename , name of the file to be read
    @throws IOException if file with filename cannot be found
    @return TreeMap<Filename,File> A TreeMap containing files extracted from the 7z file.Keys are the names of the files
     */
    public static TreeMap<String,File> uncompressSevenZFile(String filename) throws IOException {

        TreeMap<String,File> content = new TreeMap<>();

        try (SevenZFile archiveFile = new SevenZFile(new File(filename))) {
            SevenZArchiveEntry entry;
            // Go through all entries
            while ((entry = archiveFile.getNextEntry()) != null) {
                // Maybe filter by name. Name can contain a path.
                String name = entry.getName();
                if (entry.isDirectory()) {
                    System.out.printf("Found directory entry %s%n", name);
                }
                else {
                    // If this is a file, we read the file content into a

                    File f = new File(name);
                    // write to file
                    ByteArrayOutputStream contentBytes = new ByteArrayOutputStream();
                    byte[] buffer = new byte[(int) entry.getSize()];
                    for (int i = 0; i < entry.getSize(); i++) {
                        archiveFile.read(buffer);
                    }
                    contentBytes.write(buffer);
                    FileWriter writer = new FileWriter(f);
                    writer.write(contentBytes.toString());
                    writer.close();

                    content.put(name, f);

                }
            }
        }

        return content;

    }

    /*
    Goes through all the files in given treemap and formats all the printing. If file is not a textfile, then it will
    be ignored.

    @param content, A treemap, expecting key being files name and value is the file
    @param keyword, The word that is wanted to be found
     */
    public static void process(TreeMap<String,File> content,String keyword){
        if (content == null){
            return;
        }
        for (String filename : content.keySet()){
            if (!filename.contains(".txt")){
                continue;
            }
            System.out.println(filename);
            findKeyWordInFile(content.get(filename),keyword);
            System.out.println();
        }
    }

    /*
    Reads a text file line-by-line and prints the all the lines containing the keyword, CaseInsenstive. Replaces the
    keyword with the word in CAPS (KEYWORD) and prints the row-number,

    @param file, MUST BE TEXTFILE!!
    @param keyword, The wanted word
     */
    public static void findKeyWordInFile(File file,String keyword){

        try{

            BufferedReader filereader = new BufferedReader(new FileReader(file));
            String line;
            int rowNum = 0;
            while( (line = filereader.readLine()) != null){
                rowNum++;
                Matcher matcher = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(line);
                // If keyword is found in line
                if ( matcher.find()){

                    // Replace all
                    line = matcher.replaceAll(keyword.toUpperCase());

                    System.out.println(rowNum +": "+ line);
                }
            }

        } catch (IOException e){
            // Do nothing
        }


    }


    public static void main(String[]args) {

        String filename = args[0];
        String keyword = args[1];
        TreeMap<String,File> content = null;
        try{
            content = uncompressSevenZFile(filename);
        }
        catch(IOException e){
            System.out.println("Error reading file- unknown filename");
        }

        process(content,keyword);

    }

}
