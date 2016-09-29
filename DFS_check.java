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
	
	DFS_check(ArrayList<String> path){  //constructors
		this.curr_path = path;
	}
	
	DFS_check(String start, String end, String lastword, ArrayList<String> path, int size){  // constructos
		//	this.state = "unvisited";
			this.start_word = start;
			this.end_word = end;
			this.lastword = lastword;
			this.curr_path = path;
			this.path_size = size;
		}

	public static ArrayList<String> DFSwordladder(Set<String> dictionary, String start, String end){
		ArrayList<String> curr_path = new ArrayList<String>();
		curr_path.add(start); // start path with first word
		ArrayList<String> answer = new ArrayList<String>();
		DFS_check firstDFS =new DFS_check(start, end, start, curr_path, 1);
		ArrayList<String> visited = new ArrayList<String>();

		ArrayList<String> result = getDFSladder(firstDFS, dictionary, visited, answer);	
		
		return result;
	}
	
	public static ArrayList<String> getDFSladder(DFS_check myDFS, Set<String> dictionary, ArrayList<String> visited, ArrayList<String> answer){
		//dictionary.remove(myDFS.lastword); // remove from dict
		visited.add(myDFS.lastword); //adds to visited nodes list
		if(myDFS.lastword.equals(myDFS.end_word)){ // means a ladder has been completed
			answer = myDFS.curr_path;
			return answer;
		}
		else{
			Iterator<String> iterator = dictionary.iterator();
			while(iterator.hasNext()){
				String curr_word = iterator.next();		
				if(!(visited.contains(curr_word))){
				
				if(!myDFS.curr_path.contains(curr_word)){ // don't check if current word is already in path to prevent looping
				if(word_checker(myDFS.lastword, curr_word)){ // check if it differs by one letter
					myDFS.curr_path.add(curr_word);
					DFS_check next_DFS = new DFS_check(myDFS.start_word,myDFS.end_word, curr_word, myDFS.curr_path, myDFS.path_size+1);
					ArrayList<String> checky = getDFSladder(next_DFS, dictionary, visited, answer); // check new word as new path
					if(checky != null){ // means answer has been updated so just leave
						return checky;
					}
					//myDFS.path_size = myDFS.path_size - 1;
					myDFS.curr_path.remove(myDFS.path_size);  // after check, remove from path
					}
				
				}	
			}
			}
			return null;
		}
		//return null;

	}

	public static boolean word_checker(String curr, String tocheck){
		// given two words, check if they are only one letter apart
		
		if(curr.length() != tocheck.length()){
			return false;
		}
		int length = curr.length();
		int i = 0;
		int diff = 0; //count the number of differences
		while((length > 0)){
			if (curr.charAt(i) != tocheck.charAt(i)){
				diff++;
			}
			i++;
			length--;
		}
		
		if (diff > 1){
			return false;
		}
		if (diff == 1){
			return true;
		}
		if(diff == 0){
			//this will never happen since i wont check for words that were already used
	//		System.out.println("this is an error alex");
			//return 0;
		}
		return false;
		
	}
	
}
