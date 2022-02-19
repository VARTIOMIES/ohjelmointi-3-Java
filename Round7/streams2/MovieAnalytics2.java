
/**
 *
 * @author Onni Merila
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.Comparator;

public class MovieAnalytics2 {
    private ArrayList<Movie> movies;
    
    public MovieAnalytics2(){
        movies = new ArrayList<>();
    }
    
    public void populateWithData(String filename){
        
        
        // Open file and read everything from there
        try (var br = new BufferedReader(new FileReader(filename))){
            // Uses stream-mechanics to read the whole file and create Movie
            // objects which are then added to the container 'movies'
            movies.addAll(br.lines()
                .map(line -> line.split(";"))
                .map(parts -> new Movie(parts[0],
                                        Integer.parseInt(parts[1]),
                                        Integer.parseInt(parts[2]),
                                        parts[3],
                                        Double.parseDouble(parts[4]),
                                        parts[5]))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll)
            );    
        }
        catch (Exception e){
        }
        
    }
    
    public void printCountByDirector(int n){
        
        // First calculate movies of all directors and put them in container
        Map<String,Long> moviesByDirector = movies.stream()
        .collect(Collectors.groupingBy(Movie::getDirector,Collectors.counting()));
        
        // Create new stream from container and sort the stream and only accept
        // n amount of them
        Stream<Entry<String,Long>> streamOfEntries = moviesByDirector.entrySet().stream()
        .sorted(new myLongComparator())
        .limit((long) n);
        //Print out the stream
        streamOfEntries.forEach(a->System.out.format("%s: %d movies%n",a.getKey(),a.getValue()));
    }
    private static class myLongComparator implements Comparator<Entry<String,Long>>{
        @Override
        public int compare(Entry<String,Long> a,Entry<String,Long> b){
            int cmp = Long.compare(b.getValue(), a.getValue());
            
            if (cmp==0){
                cmp = a.getKey().compareTo(b.getKey());
            }
            return cmp;   
        }
        
    }
    private static class myDoubleComparator implements Comparator<Entry<String,Double>>{
        private boolean reversedDouble;
        private boolean reversedString;
        myDoubleComparator(boolean reversedDouble, boolean reversedString){
            this.reversedDouble = reversedDouble;
            this.reversedString = reversedString;
        }
        
        @Override
        public int compare(Entry<String,Double> a, Entry<String,Double> b){
            int cmp = Double.compare(a.getValue(), b.getValue());
            if (cmp==0){
                cmp = a.getKey().compareTo(b.getKey());
                return reversedString? -cmp : cmp;
            }
            return reversedDouble? -cmp : cmp;   
        }
    }
    
    public void printAverageDurationByGenre(){
        Map<String,Double> movieDurationsByGenre = movies.stream()
                .collect(Collectors.groupingBy(Movie::getGenre,Collectors.averagingInt(i->i.getDuration())));
        
        Stream<Entry<String,Double>> streamOfEntries = movieDurationsByGenre
                .entrySet().stream().sorted(new myDoubleComparator(false,false));
        
        streamOfEntries.forEach(a->System.out.format("%s: %.2f%n", a.getKey(),a.getValue()));
    }
    
    public void printAverageScoreByGenre(){
        Map<String,Double> movieScoresByGenre = movies.stream()
                .collect(Collectors.groupingBy(Movie::getGenre,Collectors.averagingDouble(i->i.getScore())));
        
        Stream<Entry<String,Double>> streamOfEntries = movieScoresByGenre
                .entrySet().stream().sorted(new myDoubleComparator(true,false));
        
        streamOfEntries.forEach(a->System.out.format("%s: %.2f%n", a.getKey(),a.getValue()));
    }
}
