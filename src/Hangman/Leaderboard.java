package Hangman;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Leaderboard {
	/* the array list of leader objects */
	ArrayList<Leader> theLeaders = new ArrayList<Leader>();
	
	
	/* constructor */
	public Leaderboard() throws FileNotFoundException{
		ArrayList<Leader> theLeaders = new ArrayList<Leader>();
		// scan in the words, one on each line
		Scanner input = new Scanner(new File("leaderboard.txt"));
		while (input.hasNext()) {
			String l = input.next();
			
			String name = l.substring(0, l.indexOf(":"));		
			String score = l.substring(l.indexOf(":")+1);
			
			int s = Integer.parseInt(score);
			
			Leader player = new Leader(name,s);
			this.theLeaders.add(player);
		}
		
	}
	
	/* add a new leader to the arrayList in the right spot */
	public void addLeader(Leader l){
		this.theLeaders.add(l);
		
		//sorts list from greatest to smallest
		for (int i = this.theLeaders.size()-2; i > - 1; i--) { 
			int j = i;
			while (j < this.theLeaders.size()-1 && theLeaders.get(j).score < theLeaders.get(j+1).score) {
				Leader temp = theLeaders.get(j);
				theLeaders.set(j, theLeaders.get(j+1));
				theLeaders.set(j+1, temp);
				j = j + 1;
				
			}
		}
		
		
	}
	
	/* print out leaderboard arraylist */
	public String toString(){
		String leaderboard = "Leaderboard: \n\n";
		int position = 1;
		for (Leader l : this.theLeaders) {
			leaderboard = leaderboard + position + ". " + l.name + ": " + l.score + "\n";
			position++;
		}
		return leaderboard;
	}
	
	
	/* write the new leaders out to the file */
	public void writeLeaderboard() throws FileNotFoundException{
		PrintStream output = new PrintStream(new File("leaderboard.txt"));
		
		//make each item in the arrayList look like 
		// x = nelson:23567
		for (Leader player : theLeaders) {
			
			String x = player.name + ":" + player.score;
			output.println(x);
			
		}
		
		//this line writes to the actual file
		
		
		//do this for each item in the arrayList
	}
}
