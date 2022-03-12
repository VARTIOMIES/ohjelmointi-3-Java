/**
 *
 * @author Onni Merila
 */

package fi.tuni.prog3.standings;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class for maintaining team statistics and standings.
 * Team standings are determined by the following rules:
 * <ul>
 *     <li>Primary rule: points total. Higher points come first.</li>
 *     <li>Secondary rule: goal difference (scored minus allowed). Higher difference comes first.</li>
 *     <li>Tertiary rule: number of goals scored. Higher number comes first.</li>
 *     <li>Last rule: natural String order of team names.</li>
 * </ul>
 */
public class Standings {

    final private TreeMap<String,Team> teams;

    /**
     * Constructs an empty Standings object.
     */
    public Standings(){
        teams = new TreeMap<>();
    }

    /**
     * Constructs a Standings object that is initialized with the game data read
     * from the specified file. The result is identical to first constructing an
     * empty Standing object and then calling {@link #readMatchData(String) readMatchData(filename)}.
     *
     * @param filename the name of the game data file to read.
     * @throws IOException if there is some kind of an IO error (e.g. if the
     * specified file does not exist).
     */
    public Standings(String filename) throws IOException{
        //Readfile
        teams = new TreeMap<>();
        readMatchData(filename);
        
        
        // Add given information
        
    }

    /**
     * <p>Reads game data from the specified file and updates the team statistics
     * and standings accordingly.</p>
     *
     * <p>The match data file is expected to contain lines of form
     * "teamNameA\tgoalsA-goalsB\tteamNameB".
     * Note that the '\t' are tabulator characters.</p>
     *
     * <p>E.g. the line "Iceland\t3-2\tFinland" would describe a match between
     * Iceland and Finland where Iceland scored 3 and Finland 2 goals.</p>
     *
     * @param filename the name of the game data file to read.
     * @throws IOException if there is some kind of an IO error
     * (e.g. if the specified file does not exist).
     */
    public final void readMatchData(String filename) throws IOException{
        
        ArrayList<String> data = readFile(filename);
        
        for (String line : data){
            String[] temp1 = line.split("\\t");
            String[] temp2 = temp1[1].split("-");
            
            String teamAname = temp1[0];
            String teamBname = temp1[2];
            int teamAgoals = Integer.parseInt(temp2[0]);
            int teamBgoals = Integer.parseInt(temp2[1]);
            
            addMatchResult(teamAname,teamAgoals,teamBgoals,teamBname);
            
        }
    }

    /**
     * Updates the team statistics and standings according to the match result
     * described by the parameters.
     *
     * @param teamNameA the name of the first ("home") team.
     * @param goalsA the number of goals scored by the first team.
     * @param goalsB the number of goals scored by the second team.
     * @param teamNameB the name of the second ("away") team.
     */
    public void addMatchResult(String teamNameA,
                               int goalsA,
                               int goalsB,
                               String teamNameB){
        
        addTeamIfNotExist(teamNameA);
        addTeamIfNotExist(teamNameB);

        Team teamA = teams.get(teamNameA);
        Team teamB = teams.get(teamNameB);

        // Add info for the teams

        //Win loss and tie
        if (goalsA>goalsB){
            teamA.wins++;
            teamB.losses++;
            teamA.points += 3;
        }
        else if(goalsA == goalsB){
            teamA.ties++;
            teamB.ties++;
            teamA.points++;
            teamB.points++;
        }
        else{
            teamA.losses++;
            teamB.wins++;
            teamB.points += 3;
        }

        // goals, allowed
        teamA.scored += goalsA;
        teamB.scored += goalsB;

        teamA.allowed += goalsB;
        teamB.allowed += goalsA;
        
        teamA.gamesPlayed++;
        teamB.gamesPlayed++;

    }

    /**
     * Returns a list of the teams in the same order as they would appear in a
     * standings table.
     *
     * @return a list of the teams in the same order as they would appear in a
     * standings table.
     */
    public List<Team> getTeams(){
        ArrayList<Team> temp = new ArrayList<>(teams.values());
        
        //TeamCompare teamComp = new TeamCompare();
        temp.sort((a, b) -> {
            if (a.getPoints() != b.getPoints()) {
                return b.getPoints() - a.getPoints();
            } else if (a.getScored() - a.getAllowed() !=
                    b.getScored() - b.getAllowed()) {
                return b.getScored() - b.getAllowed() -
                        (a.getScored() - a.getAllowed());
            } else if (a.getScored() != b.getScored()) {
                return b.getScored() - a.getScored();
            } else {

                return a.getName().compareTo(b.getName());
            }
        });
        
        return temp;
    }

    /**
     * Prints a formatted standings table to the provided output stream.
     *
     * @param out the output stream to use when printing the standings table.
     */
    public void printStandings(PrintStream out){
        List<Team> teamStandings = getTeams();
        
        int biggestSize = 0;
      
        for (Team team : teamStandings){
            if (team.getName().length()>biggestSize){
                biggestSize = team.getName().length();
            }
        }
        
        for (Team team : teamStandings){
            
            out.format("%-"+biggestSize+"s%4d%4d%4d%4d%7s%4d%n",
                    team.getName(),team.getGames(),team.getWins(),team.getTies(),
                    team.getLosses(),team.getScored() + "-"+
                            team.getAllowed(),
                    team.getPoints());
        }
        
    }

    /**
     * @hidden
     */
    private void addTeamIfNotExist(String teamname){
        if (!teams.containsKey(teamname)){
                teams.put(teamname,new Team(teamname));
            }
    }
    
    // Reads textfile and returns an arraylist witch contains Strings 
    // of every line in the file

    /**
     * @hidden
     */
    private ArrayList<String> readFile(String filename) throws IOException{
        
        ArrayList<String> linesInFile = new ArrayList<>();
        String currentLine;
        var input = new BufferedReader(new FileReader(filename));
        while((currentLine = input.readLine()) != null) {
            linesInFile.add(currentLine);
        }

            return linesInFile;
    }

    /**
     * A class for storing statistics of a single team. The class offers only
     *  public getter functions. The enclosing class Standings is responsible
     *  for setting and updating team statistics.
     */
    public static class Team{
        //Information the class has
        final private String teamName;
        private int wins;
        private int ties;
        private int losses;
        private int scored;
        private int allowed;
        private int points;
        private int gamesPlayed;


        /**
         * Constructs a Team object for storing statistics of the named team.
         * @param name the name of the team whose statistics the new team object stores.
         */
        public Team(String name){
            teamName = name;
        }

        /**
         * Returns the name of the team.
         * @return the name of the team.
         */
        String getName(){
            return teamName;
        }

        /**
         * Returns the number of wins of the team.
         * @return the number of wins of the team.
         */
        int getWins(){
            return wins;
        }

        /**
         * Returns the number of ties of the team.
         * @return the number of ties of the team.
         */
        int getTies(){
            return ties;
        }

        /**
         * Returns the number of losses of the team.
         * @return the number of losses of the team.
         */
        int getLosses(){
            return losses;
        }

        /**
         * Returns the number of goals scored by the team.
         * @return the number of goals scored by the team.
         */
        int getScored(){
            return scored;
        }

        /**
         * Returns the number of goals allowed (conceded) by the team.
         * @return the number of goals allowed (conceded) by the team.
         */
        int getAllowed(){
            return allowed;
        }

        /**
         * Returns the overall number of points of the team.
         * @return the overall number of points of the team.
         */
        int getPoints(){
            return points;
        }

        /**
         * @hidden
         */
        int getGames(){
            return gamesPlayed;
        }
        
        
    }
}
