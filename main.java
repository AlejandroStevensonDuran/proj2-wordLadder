/* WORD LADDER Main.java  
 * EE422C Project 3 submission by  
 * 
 * Samuel Patterson 
 * svp395
 * 16445 
 * Alejandro Stevenson-Duran
 * as72948
 * 16445
 * Slip days used: <0>  
 * Git URL:  https://github.com/AlejandroStevensonDuran/proj2-wordLadder/
 * Fall 2016 
 */

package assignment3; 

import java.util.*;
import java.io.*;

public class Main {
	
	public static String startWord;
	public static String endWord;
	
	public static void main(String[] args) throws Exception {
		Scanner kb; // input Scanner for commands
		PrintStream ps; // output file 
		
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0])); 
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps); // redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin 
			ps = System.out; // default to Stdout
		} 
		
		initialize();
		
		
		/* bfs */
		ArrayList<String> words = parse(kb);
		
		if (words == null){System.exit(0);}
		
		String[] real = words.toArray(new String[words.size()]);
		
		ArrayList<String> result = getWordLadderBFS(real[0], real[1]);
		
		if (result == null) { 
			System.out.println("no word ladder can be found between " + real[0].toLowerCase() + " and " + real[1].toLowerCase() + ".");
			System.exit(0);
		}
		
		printLadder(result);
	}
	
	public static void initialize() {
			// initialize your static variables or constants here. 
			// We will call this method before running our JUNIT tests.  So call it
			// only once at the start of main.
	}

/**  
 * @param keyboard Scanner connected to System.in  	
 * @return ArrayList of 2 Strings containing start word and end word. 
 * If command is /quit, return empty ArrayList.  
 */
	public static ArrayList<String> parse(Scanner keyboard) {

		String words = keyboard.nextLine();
		String word1;	
		String word2;
		
		words = words.toUpperCase();
		
		if (words.equals("/QUIT")){ System.exit(0); }
		
		
		String [] splitWords = words.split(" ");
		
		word1 = splitWords[0].toUpperCase();
		if (word1.equals("/QUIT")){ return null; } //System.exit(0); } /******* CLARITY ON PIAZZA******/
		
		word2 = splitWords[1].toUpperCase();
		if (word2.equals("/QUIT")){ return null; } //System.exit(0); }
		
		ArrayList<String> result = new ArrayList<String>();
		
		/* put words in proper index */
		result.add(0, word1);
		result.add(1, word2);
		
		Main.startWord = word1;
		Main.endWord = word2;
		
		return result;
	} 
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
			// Returned list should be ordered start to end.  Include start and end.
			// Return empty list if no ladder. 
			// TODO some code 
			Set<String> dict = makeDictionary(); 
			// TODO more code
		
		return null; // replace this line later with real return
	}
	
	
	public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		Set<String> dict = makeDictionary();
		
		ArrayList<String> result = new ArrayList<String>();
		Iterator<String> iterate;
		
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		Main.startWord = start;
		Main.endWord = end;
		
		Node out;
		Node begin = new Node(start);
		
		LinkedList<Node> queue = new LinkedList<Node>();
		
		queue.add(begin);
		
		while (!queue.isEmpty()){
			
			iterate = dict.iterator();
			//get head of queue
			out = queue.remove();
			
			/* return found ladder if match */
			if (out.getVal().equals(end)){
				result = getList(out, result);
				result.add(0, end); // have to put end word into result, because if it's found, it isn't originally added
				Collections.reverse(result);
				return result;
			}
			else{
				while (iterate.hasNext()){
					findFriends(out, queue, iterate, dict);
				}
			}
		}
		/* if no match found return empty */
		return null; 
	}
	
	public static Set<String>  makeDictionary () { 
		Set<String> words = new HashSet<String>();
		Scanner infile = null;	
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!"); 
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		} 
		return words;
	}
	
	/**
	 * Prints given ladder in proper order.
	 * @param ladder
	 */
	public static void printLadder(ArrayList<String> ladder) {
		
		if (ladder == null){
			System.out.println("no word ladder can be found between " +  Main.startWord.toLowerCase() + " and " + Main.endWord.toLowerCase() + ".");
			System.exit(0);
		}
		
		String[] lad = ladder.toArray(new String[ladder.size()]);
		
		System.out.println("a " + (ladder.size() - 2) + "-rung word ladder exists between " + lad[0].toLowerCase() + " and " + lad[lad.length - 1].toLowerCase() + ".");
		
		for (int i = 0; i < lad.length; i++){
			System.out.println(lad[i].toLowerCase());
		}
	}
	
	
	/**
	 * Method to determine whether or not next differs from word by 1 letter
	 * @param word
	 * @param next
	 * @return true if neighbor, false if not.
	 */
	private static boolean isNeighbor(String word, String next){
		if (word.length() != next.length()){
			return false;
		}
		
		int num = 0;
		
		for (int i = 0; i < word.length(); i++){
			if (word.charAt(i) != next.charAt(i)){
				num++;
			}
		}
		return num == 1 ? true : false;
	}
	
	/**
	 * Method to find all neighbors of start word. Adds friends to queue for later check.
	 * Removes neighbors from dictionary so that we don't get stuck on them later (like marking as visited).
	 * @param start
	 * @param queue
	 * @param it
	 * @param dict
	 */
	
	private static void findFriends(Node start, LinkedList<Node> queue, Iterator<String> it, Set<String> dict){
		String comp;
		String parent = start.getVal();
		ArrayList<String> remove = new ArrayList<String>();
		
		while (it.hasNext()){
			comp = it.next();
			if (isNeighbor(parent, comp)){
				queue.add(new Node(comp, start));	
				remove.add(comp);
			}
		}
		
		/* delete visited nodes so that you don't keep going back to them forever */
		for (int i = 0; i < remove.size(); i++){
			dict.remove(remove.get(i));
		}
	}
	
	/**
	 * method to construct final list from end word to parent. in reverse order of print
	 * @param start
	 * @param list
	 * @return word ladder
	 */
	private static ArrayList<String> getList(Node start, ArrayList<String> list){
		String parent;
		
		while (start.getParent() != null){
			parent = start.getParent().getVal();
			list.add(parent);
			start = start.getParent();
		}
		return list;
	}
}

