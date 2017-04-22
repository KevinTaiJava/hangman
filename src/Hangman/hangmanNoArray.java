package Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class hangmanNoArray { 

	public static void main(String[] args) throws FileNotFoundException {
		
		ArrayList<String> wordList = new ArrayList<String>();
		
		Scanner input = new Scanner(new File("randomWords.txt")); 
		while (input.hasNext()) {
			String word = input.next();
			word = word.toLowerCase();
			wordList.add(word);
		}
		
		//word
		String word = grabRandomWord(wordList);
//		System.out.println(word); //tester

		
		
		//guessWord
		String guessWord = "";
		for (int i = 0; i < word.length(); i++) {
			guessWord += '_';
		}
	

		//keeps track of all used letters
		String alphabet = "";
		for (int i = 0; i < 26; i++) {
			char letter = (char) ('a' + i);
			alphabet += letter;
			alphabet += " ";
		}
		
		
		//first instance of game:
		System.out.println(alphabet);
		
		System.out.println("\nMystery word:");
		print(guessWord);
		
		//variables to keep track of win/lose conditions of hangman
		boolean hangmanNotDead = true;
		boolean wordNotGuessed = true;
		int missCounter = 0;
		int score = 0;
		
		while (hangmanNotDead && wordNotGuessed) {
			
			printHangman(missCounter);
			
			Scanner keyboard = new Scanner(System.in);
			System.out.println("\nGuess a letter.");
			String userInput = keyboard.nextLine(); //HOW DO MAKE SCANNER ONLY INPUT A CHAR?
			char letter = userInput.charAt(0);
			
			alphabet = updateAlphabet(alphabet, letter);
			
			
			boolean letterInWord = false;
									
			if (word.indexOf(letter) != - 1) {		
				guessWord = updateGuessWord(word, guessWord, letter);
				letterInWord = true;
				score += 10;
			}
			
			System.out.println(guessWord);
	
			clearConsole();
			System.out.println(alphabet);
			System.out.println();
			
			System.out.println("Mystery word:");
			print(guessWord);
			
			if (!letterInWord) {
				System.out.println("\nThe letter " + letter + " is not in the word");
				missCounter++;
				score -= 5;
			} else {
				System.out.println("\nThe letter " + letter + " is in the word");
			}
			
			
			//this section checks if you've either won or lost the games /w the two conditions
			//wordNotGuessed and hangmanNotDead
			boolean gameWon = gameWon(guessWord);
			if (gameWon) {
				wordNotGuessed = false;
				System.out.println("\nCongratulations! You've guessed the word and saved hangman!");
				System.out.println("You scored " + score + " points.");
				
				System.out.println("Add yourself to the leaderboard! Enter a name.");
				String name = keyboard.next();
				leaderboard(name,score);
				
			}
			
			if (missCounter == 7) {
				hangmanNotDead = false; 
				System.out.println("You failed, RIP stick figure man");
				System.out.println("The word was " + word);
			}
			System.out.println("Score: " + score);
		}
	
}

	public static void print(String word) { //just adds some space between letters because it looks better
		String result = "";
		for (int i = 0; i < word.length(); i++) {
			result += word.charAt(i);
			result += " ";
		}
		System.out.println(result);
	}
	
	public static String grabRandomWord(ArrayList<String> wordList) {
		Random random = new Random();
		
		int randomIndex = random.nextInt(wordList.size());
		String randomWord = wordList.get(randomIndex);
		
		return randomWord;	
	}
	
	public static String updateGuessWord(String word, String guessWord, char letter) {
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == letter) {
				guessWord = guessWord.substring(0,i) + letter + guessWord.substring(i+1);
			}
		}
		return guessWord;
	}
	
	public static String updateAlphabet(String alphabet, char letter) {
		int index = alphabet.indexOf(letter);
		String newAlphabet = "";
		newAlphabet = alphabet.substring(0, index) + "_" + alphabet.substring(index+1);
		return newAlphabet;
	}
	
	public static void clearConsole () { //kind of, not really.
		for (int i = 1; i < 20; i++) {
			System.out.println();
		}
	}
	
	public static boolean gameWon(String guessWord) {
		for (int i = 0; i < guessWord.length(); i++) {
			if (guessWord.charAt(i) == '_') {
				return false;
			}
		}
		return true;
	}
	
	private static void printHangman(int missCounter) {
		int poleLines = 6;   // number of lines for hanging pole
		System.out.println("  ____");
		System.out.println("  |  |");
		
		int badGuesses = missCounter;
		
		if (badGuesses > 0) {	    	   
			System.out.println("  |  O");
			poleLines = 5;
		}
		
		//arms
		if (badGuesses > 1) {
			if (badGuesses == 2) {
				System.out.println("  |  |"); //chest
			} else if (badGuesses == 3) {
				System.out.println("  | \\|"); //left arm
			} else if (badGuesses >= 4) {
				System.out.println("  | \\|/"); //right arm
			}
			poleLines = 4;
		}
		
		
		//feet
		if (badGuesses > 4) {
			poleLines = 3;
			if (badGuesses == 5) {
				System.out.println("  | /"); //left foot
			} else if (badGuesses >= 6) {
				System.out.println("  | / \\"); //right foot
			}
		}

		for (int k = 0; k < poleLines; k++) {
			System.out.println("  |");
		}
		
		//base
		System.out.println("__|__");
		System.out.println();
		
		System.out.println(7-badGuesses + " more bad guesses and hangman will be dead.");
	}
	
	public static void leaderboard (String name, int score) throws FileNotFoundException {
		Leaderboard hangman = new Leaderboard();
		
		Leader player = new Leader (name, score);
		hangman.addLeader(player);
		hangman.writeLeaderboard();
		
		System.out.println(hangman);
		
	}
	
}
	
