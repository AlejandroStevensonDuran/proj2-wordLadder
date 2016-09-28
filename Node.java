package assignment3;

import java.util.*;

public class Node {
	private String val;
	private Node parent;
	
	public Node(String s){
		val = s;
		parent = null;
		marked = true;
	}
	
	public Node(String s, Node p){
		val = s;
		parent = p;
		marked = true;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public String getVal(){
		return val;
	}
}
