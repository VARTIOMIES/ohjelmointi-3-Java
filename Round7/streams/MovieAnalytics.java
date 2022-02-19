
import java.util.ArrayList;
import java.util.function.Consumer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Stream;

/**
 *
 * @author Onni Merila
 */
public class MovieAnalytics {
    
    // Container for movies
    private ArrayList<Movie> movies;
    
    // Class for printing info of movie
    private static class PrintMovie implements Consumer<Movie>{
        @Override
        public void accept(Movie movie){
            System.out.print(String.format("%s (By %s, %d)%n",movie.getTitle(),
                    movie.getDirector(), movie.getReleaseYear()));
        }
    }
    
    // Constructor
    public MovieAnalytics(){
        
        movies = new ArrayList<>();
    }
    
    public static Consumer<Movie> showInfo(){
        return new PrintMovie();
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
    
    public Stream<Movie> moviesAfter(int year){
        // Create stream from movies
        Stream<Movie> movieStream = movies.stream();
        
        // Filter the stream, so that only movies that have a releaseyear
        // greater or equal than 'year' remain. Then return the stream.
        return movieStream
            .filter(movie -> {return movie.getReleaseYear() >= year;})
            .sorted((movieA,movieB)-> movieA.compareTo(movieB));
    }
    
    public Stream<Movie> moviesBefore(int year){
        // Create stream from movies
        Stream<Movie> movieStream = movies.stream();
        
        // Filter the stream, so that only movies that have a releaseyear
        // smaller than 'year' remain. Then return the stream.
        return movieStream
            .filter(movie ->{return movie.getReleaseYear() <= year;})
            .sorted((movieA,movieB)-> movieA.compareTo(movieB));
    }
    
    public Stream<Movie> moviesBetween(int yearA, int yearB){
        // Create stream from movies
        Stream<Movie> movieStream = movies.stream();
        
        // Filter the stream, so that only movies that have a releaseyear
        // between 'yearA' and 'yearB' remain. Then return the stream.
        return movieStream
            .filter(movie -> {
                // Tha lambda function given for the filter()-function, returns true
                // when movies releaseYear is between the two years.
                return movie.getReleaseYear() >= yearA 
                    && movie.getReleaseYear() <= yearB;
            })
            .sorted((movieA,movieB)->movieA.compareTo(movieB));
        
    }
    
    public Stream<Movie> moviesByDirector(String director){
        // Create stream from movies
        Stream<Movie> movieStream = movies.stream();
        
        // Filter the stream so that only movies of the given 'director' remain.
        // Then return the stream.
        return movieStream
            .filter(movie -> {
                return movie.getDirector().equalsIgnoreCase(director);
            })
            .sorted((movieA,movieB)->movieA.compareTo(movieB));
    }
    
    
}
