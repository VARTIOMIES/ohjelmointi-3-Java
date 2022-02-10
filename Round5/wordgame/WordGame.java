/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Onni Merila
 */

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordGame {
    private ArrayList<String> allWords;
    private boolean isGameActive;
    private WordGameState gameState;
    private int wordIndex;
    
    public WordGame(String wordFilename){
        
        // Read file
        allWords = new ArrayList<>();
        try (var wordFile = new BufferedReader(new FileReader(wordFilename))){
            String word;
            while((word = wordFile.readLine())!= null){
                allWords.add(word);
            }
            
        }
        catch (IOException e){
            
        }
        isGameActive = false;
        gameState = null;
        wordIndex = 0;
        
    }
    
    public void initGame(int wordIndex, int mistakeLimit){
        String wordToBeUsed = allWords.get(wordIndex % allWords.size());
        
        
        WordGameState newGameState = new WordGameState(wordToBeUsed,mistakeLimit);
        this.gameState = newGameState;
        isGameActive = true;
        this.wordIndex = wordIndex;
    }
    
    public boolean isGameActive(){
        return isGameActive;
    }
    
    public WordGameState getGameState() throws GameStateException{
        if (!isGameActive){
            throw new GameStateException("There is currently no active word game!");
        }
        return gameState;
    }
    
    public WordGameState guess(String word) throws GameStateException{
        if (!isGameActive){
            throw new GameStateException("There is currently no active word game!");
        }
        if (word.equalsIgnoreCase(allWords.get(wordIndex % allWords.size()))){
            gameState.word = word.toLowerCase();
            gameState.missingChars = 0;
            isGameActive = false;
        }
        else {
            gameState.mistakes++;
            if (gameState.mistakes > gameState.mistakeLimit){
                isGameActive = false;
                gameState.word = allWords.get(wordIndex % allWords.size());
            }
        }
        return gameState;
    }
    
    public WordGameState guess(char c) throws GameStateException{
        if (!isGameActive){
            throw new GameStateException("There is currently no active word game!");
        }
        if (!isGuessCorrect(c)){
            gameState.mistakes++;
            
            // End game if mistakelimit has been reached
            if (gameState.mistakes > gameState.mistakeLimit){
                isGameActive = false;
            }
        }
        else {
            // End game if playaer has WON!
            if (gameState.missingChars == 0){
                isGameActive = false;
            }
            
        }
        
        return gameState;
    }
    
    
    // Find out if guessed character is in the correct word. If so and also
    // the character is not already guessed, then update the gamestateword
    private boolean isGuessCorrect(char c){
        
        String correctWord = allWords.get(wordIndex % allWords.size());
        
        boolean isCorrect = false;
       
        char guessedCharacter = Character.toLowerCase(c);
        
        char[] correctWordArray = correctWord.toCharArray();
        
        char[] Gamestateword = gameState.word.toCharArray();
        
        
        for (int i= 0; i<correctWord.length();i++){
            
            char correctCharacter = Character.toLowerCase(correctWordArray[i]);
            
            if ( guessedCharacter == correctCharacter
                    && guessedCharacter != Gamestateword[i]) {
                isCorrect = true;
                Gamestateword[i] = guessedCharacter;
                gameState.missingChars--;
               
            }
        }
        if (isCorrect){
            String newGameStateWord = String.valueOf(Gamestateword);
            gameState.word = newGameStateWord;
        }
        
        
        return isCorrect;
    }
    
    
    public static class WordGameState{
        private String word;
        private int mistakes;
        private int mistakeLimit;
        private int missingChars;
        
        private WordGameState(String word, int mistakeLimit){
            
            this.mistakeLimit = mistakeLimit;
            this.mistakes = 0;
            this.missingChars = word.length();
            
            // Change the word to unknown 
            char[] chars = word.toCharArray();
            
            for (int i=0;i<chars.length;i++){
                chars[i] = '_';
            }
            this.word = String.valueOf(chars);
            
            
        }
        
        public String getWord(){
            return word;
        }
        
        public int getMistakes(){
            return mistakes;
        }
        
        public int getMistakeLimit(){
            return mistakeLimit;
        }
        
        public int getMissingChars(){
            return missingChars;
        }
        
        
        
    }
}
