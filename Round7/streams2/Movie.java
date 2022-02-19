
/**
 *
 * @author Onni Merila
 */

public class Movie implements Comparable<Movie>{
    
    // All neeeded info stored
    private final String title;
    private final int releaseYear;
    private final int duration;
    private final String genre;
    private final double score;
    private final String director;
    
    // Constructor
    public Movie(String title, int releaseYear, int duration, String genre,
            double score, String director){
        
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.genre = genre;
        this.score = score;
        this.director = director;
    }
    
    // Basic getter functions
    public String getTitle(){return title;}
    
    public int getReleaseYear() {return releaseYear;}
    
    public int getDuration() {return duration;}
    
    public String getGenre() {return genre;}
    
    public double getScore() {return score;}
    
    public String getDirector() {return director;}
    
    
    @Override
    public int compareTo(Movie other){
        int cmp = 0;
        
        if (this.getReleaseYear()>other.getReleaseYear()){
            cmp = 1;
        }
        else if (this.getReleaseYear()<other.getReleaseYear()){
            cmp = -1;
        }
        else {
            // If movies are released in same year, then sort them by title.
            cmp = this.getTitle().compareTo(other.getTitle());
        }
        return cmp;
    }
    
    
}
