package Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class hangmanRunner { //version with char array

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
		char[] words = new char[word.length()];
		for (int i = 0; i < word.length(); i++) {
			words[i] = word.charAt(i);
		}
		
		
		//guessWord
		char[] guessWord = new char[word.length()];
		for (int i = 0; i < word.length(); i++) {
			guessWord[i] = '_';
		}
		
		//if a letter has been guessed, index of letter is set to true
		boolean[] alphabetGuesses = new boolean[26]; 
		
		

		
		//first instance of game:
		printAlphabetGuesses(alphabetGuesses);
		
		System.out.println("\nMystery word:");
		printCharArray(guessWord);
		
		//variables to keep track of win/lose conditions of hangman
		boolean hangmanNotDead = true;
		boolean wordNotGuessed = true;
		int missCounter = 0;
		int score = 0;
		
		while (hangmanNotDead && wordNotGuessed) {
			
			printHangman(missCounter);
			
			Scanner keyboard = new Scanner(System.in);
			System.out.println("\nGuess a letter.");
			String letter = keyboard.nextLine(); //HOW DO MAKE SCANNER ONLY INPUT A CHAR?
			
			alphabetGuesses[letter.charAt(0) - 'a'] = true; 
			
			boolean letterInWord = false;
			for (int i = 0; i < words.length; i++) { //parse actual word, if letter is in word, set the
				if (letter.charAt(0) == (words[i])) {		//letter to display on guessWord.
					guessWord[i] = words[i];
					letterInWord = true;
					score += 10;
				}
			}
		
			
			clearConsole();
			printAlphabetGuesses(alphabetGuesses);
			System.out.println();
			System.out.println("Mystery word:");
			printCharArray(guessWord);
			
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

	
	public static String grabRandomWord(ArrayList<String> wordList) {
		Random random = new Random();
		
		int randomIndex = random.nextInt(wordList.size());
		String randomWord = wordList.get(randomIndex);
		
		return randomWord;	
	}
	
	public static void printCharArray(char[] array) {
		String word = "";
		for (int i = 0; i < array.length; i++) {
			word += array[i] + " ";
		}
		System.out.println(word);
	}
	
	public static void printAlphabetGuesses(boolean[] alphabetGuesses) {
		System.out.println("Available letters:");
		for (int i = 0; i < alphabetGuesses.length; i++) {
			if (alphabetGuesses[i] == true) {
				System.out.print("_ ");
			} else {
				char letter = (char) ('a' + i);
				System.out.print(letter + " ");
			}
		}
		
		System.out.println();
		
	}
	
	public static void clearConsole () { //kind of, not really.
		for (int i = 1; i < 20; i++) {
			System.out.println();
		}
	}
	
	public static boolean gameWon(char[] guessWord) {
		for (char c : guessWord) {
			if (c == '_') {
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
	
