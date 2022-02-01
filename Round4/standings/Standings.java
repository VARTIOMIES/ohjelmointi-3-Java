/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */

import java.util.ArrayList;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Standings {
    
    private TreeMap<String,Team> teams;
    
    public Standings(){
        teams = new TreeMap<>();
    }
    public Standings(String filename){
        //Readfile
        teams = new TreeMap<>();
        readMatchData(filename);
        
        
        // Add given information
        
    }
    
    public void readMatchData(String filename){
        
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
    
    public void addMatchResult(String teamNameA, int goalsA,
            int goalsB, String teamNameB){
        
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
    
    public ArrayList<Team> getTeams(){
        ArrayList<Team> temp = new ArrayList<>(teams.values());
        
        //TeamCompare teamComp = new TeamCompare();
        java.util.Collections.sort(temp,(a,b)->{
            if (a.getPoints()!=b.getPoints()){
                return b.getPoints() - a.getPoints();
            }

            else if (a.getScored()-a.getAllowed() !=
                    b.getScored()-b.getAllowed()){
                return b.getScored()-b.getAllowed() -
                        (a.getScored()-a.getAllowed());
            }

            else if (a.getScored() != b.getScored()){
                return b.getScored() - a.getScored();
            }
            else {

                return a.getName().compareTo(b.getName());
            }
        });
        
        return temp;
    }
    
    public void printStandings(){
        ArrayList<Team> teamStandings = getTeams();
        
        int biggestSize = 0;
      
        for (Team team : teamStandings){
            if (team.getName().length()>biggestSize){
                biggestSize = team.getName().length();
            }
        }
        
        for (Team team : teamStandings){
            
            System.out.format("%-"+biggestSize+"s%4d%4d%4d%4d%7s%4d%n",
                    team.getName(),team.getGames(),team.getWins(),team.getTies(),
                    team.getLosses(),Integer.toString(team.getScored()) + "-"+ 
                            Integer.toString(team.getAllowed()),
                    team.getPoints());
        }
        
    }
    
    private void addTeamIfNotExist(String teamname){
        if (!teams.containsKey(teamname)){
                teams.put(teamname,new Team(teamname));
            }
    }
    
    // Reads textfile and returns an arraylist witch contains Strings 
    // of every line in the file
    private ArrayList<String> readFile(String filename){
        
        ArrayList<String> linesInFile = new ArrayList<>();
        String currentLine;
        try{
            var input = new BufferedReader(new FileReader(filename));
            while((currentLine = input.readLine()) != null){
                linesInFile.add(currentLine);
            }
        }
        catch (IOException e){
            System.err.println("Error reading file");
        }
        finally {
            return linesInFile;
        }
    }
    
    
    static class Team{
        //Information the class has
        private String teamName;
        private int wins;
        private int ties;
        private int losses;
        private int scored;
        private int allowed;
        private int points;
        private int gamesPlayed;
        
        // Constructor
        public Team(String name){
            teamName = name;
        }
        
        // Getter functions
        String getName(){
            return teamName;
        }
        int getWins(){
            return wins;
        }
        int getTies(){
            return ties;
        }
        int getLosses(){
            return losses;
        }
        int getScored(){
            return scored;
        }
        int getAllowed(){
            return allowed;
        }
        int getPoints(){
            return points;
        }
        int getGames(){
            return gamesPlayed;
        }
        
        
    }
}
