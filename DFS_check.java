package assignment3;

import java.util.ArrayList;
import java.util.*;
import java.io.*;
import java.util.Iterator;

public class DFS_check {

	String lastword; // the one to be compared to
	String start_word;
	String end_word;
	ArrayList<String> curr_path;
	int path_size = 0;

	DFS_check(ArrayList<String> path) { // constructors
		this.curr_path = path;
	}

	DFS_check(String start, String end, String lastword, ArrayList<String> path, int size) { 
		this.start_word = start;
		this.end_word = end;
		this.lastword = lastword;
		this.curr_path = path;
		this.path_size = size;
	}

	public static ArrayList<String> DFSwordladder(Set<String> dictionary, String start, String end) {
		start = start.toLowerCase();
		end = end.toLowerCase();
		ArrayList<String> curr_path = new ArrayList<String>();
		curr_path.add(start); // start path with first word
		ArrayList<String> answer = new ArrayList<String>();
		DFS_check firstDFS = new DFS_check(start, end, start, curr_path, 1);
	//	ArrayList<String> visited = new ArrayList<String>();
		int length = dictionary.size();
		newDict[] mydict = new newDict[length];
		mydict = create_myDictionary(dictionary, length, mydict);
		ArrayList<String> result = getDFSladder(firstDFS, mydict, answer);

		return result;
	}
	
	private static newDict[] create_myDictionary(Set<String> dictionary, int length, newDict[] mydict){
		Iterator<String> iterator = dictionary.iterator();
		int i = 0;
		while (length > i) {
			newDict check = new newDict();
			check.word = iterator.next();
			check.word = check.word.toLowerCase();
			mydict[i] = check;
			i++;
		}
		return mydict;
	}

	public static ArrayList<String> getDFSladder(DFS_check myDFS, newDict[] dictionary, ArrayList<String> answer) {
			int q = 0;
			while (q < dictionary.length) {
				if (myDFS.lastword.equals(myDFS.end_word)) { // means a ladder has been
					// completed
					answer = myDFS.curr_path;
					return answer;
				} 
				newDict curr_node = dictionary[q];
				String curr_word = curr_node.word;
						if (word_checker(myDFS.lastword, curr_word)) {
							if (curr_node.visited == false) {  // take this only if it isn't visited
							myDFS.curr_path.add(curr_word);
							dictionary[q].visited = true;
							DFS_check next_DFS = new DFS_check(myDFS.start_word, myDFS.end_word, curr_word, myDFS.curr_path, myDFS.path_size + 1);
							ArrayList<String> checky = getDFSladder(next_DFS, dictionary, answer);
							if (checky.size() != 0) { // means answer has been
								return checky;
							}
						}
					}
				q++;
			}
			myDFS.curr_path.remove(myDFS.path_size - 1); // after check, remove from path
			return new ArrayList<String>();
	}

	public static boolean word_checker(String curr, String tocheck) {
		// given two words, check if they are only one letter apart

		if (curr.length() != tocheck.length()) {
			return false;
		}
		int length = curr.length();
		int i = 0;
		int diff = 0; // count the number of differences
		while ((length > 0)) {
			if (curr.charAt(i) != tocheck.charAt(i)) {
				diff++;
			}
			i++;
			length--;
		}

		if (diff > 1) {
			return false;
		}
		if (diff == 1) {
			return true;
		}
		if (diff == 0) {
			// this will never happen since i wont check for words that were
			// already used
			// System.out.println("this is an error alex");
			// return 0;
		}
		return false;

	}

}