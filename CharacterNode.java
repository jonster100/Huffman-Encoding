
/**
 * Author: Job64
 * Date: 25/11/2016
 **/

class CharacterNode {
	private char orginalCharSymbol; 
	private CharacterNode leftChild;
	private CharacterNode rightChild;
	private int noChars;
	private int nodeDepth;
	private String nBits;
	
	
	
	/**
	 * CharacterNode class constructor which will be a leaf node, initialises variables.
	 * @param origChar the character this object will represent and initialises the variable
	 **/
	public CharacterNode(char origChar) {
		orginalCharSymbol = origChar;
		noChars = 1;
		leftChild = null;
		rightChild = null;
		nodeDepth = 1;
	}
	
	/**
	 * This is an alternative constructor which will be a parent node
	 * @param origChar the character this object will represent and initialises the variable
	 * @param noChar the number of character that will be represented by this object
	 * @param lChild becomes the initial leftChild value
	 * @param rChild becomes the initial rightChild value*/
	public CharacterNode(char origChar,int noChar,CharacterNode lChild,CharacterNode rChild) {
		orginalCharSymbol = origChar;
		noChars = noChar;
		leftChild = lChild;
		rightChild = rChild;
		nodeDepth = 1;
	}
	
	/**
	 * @return String returns the noBits vale for the current character*/
	public String getNBits(){
		return nBits;
	}
	
	public void setCode(String c){
		this.nBits = c;
	}
	/**
	 * @return char returns the identifier for the object*/
	public char getOrginalCharSymbol() {
		return orginalCharSymbol;
	}

	/**
	 * @return CharacterNode will return the left child of this object */
	public CharacterNode getLeftChild() {
		return leftChild;
	}
	
	/**
	 * @param leftChild this parameter will set the left child of this object*/
	public void setLeftChild(CharacterNode leftChild) {
		this.leftChild = leftChild;
	}
	
	/**
	 * @return CharacterNode will return he right child of the object*/
	public CharacterNode getRightChild() {
		return rightChild;
	}
	
	/**
	 * @param rightChild this parameter will set the right child of this object*/
	public void setRightChild(CharacterNode rightChild) {
		this.rightChild = rightChild;
	}
	
	/**
	 * @return int will return the number of chars represented by this object*/
	public int getNoChars() {
		return noChars;
	}
	
	/**
	 * This method will increment the number of chars represented by this object by 1*/
	public void addOneChar() {
		this.noChars += 1;
	}
	
	/**
	 * @return int will return the depth of this object in a tree*/
	public int getNodeDepth() {
		return nodeDepth;
	}
	
	/**
	 * @param x will set the depth of this object in a tree structure*/
	public void setNodeDepth(int x) {
		this.nodeDepth = x;
	}
	
	
}
