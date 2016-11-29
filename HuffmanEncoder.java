
/**
 * Author: #####
 * Date: 25/11/2016
 **/

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class HuffmanEncoder<Adjacency> {
	private PriorityQueue<CharacterNode> characterListSorted;
	private ArrayList<CharacterNode> characterListUnsorted;
	private int totalNodesCreated;

	/**
	 * This class is used to order the nodes that get put into the Priority queue 
	 * "characterList", which uses the Comparator class and overriding a compare method.
	 **/
	public class characterFreqComparator implements Comparator<CharacterNode>{
		/**
		 * @param x,y	Identifiers for the 'CharacterNode' objects to be passed through the method
		 * @return int 	Returns the output of a comparison between two 'CharacterNode' objects e.g -1,0,1
		 **/
		@Override
		public int compare(CharacterNode x, CharacterNode y) {
			return (y.getNoChars()<x.getNoChars())?1:(y.getNoChars()==x.getNoChars())?0:-1;
		}
	}
	
	/**
	 * Class Constructor Initialising: PriorityQueue */
	public Job64CS21120Assign(){
		characterListSorted = new PriorityQueue<CharacterNode>(95,new characterFreqComparator());
		characterListUnsorted = new ArrayList<CharacterNode>();
		totalNodesCreated = 0;
	}
	
	private void insertIntoSortedCharacterList(){
		for(CharacterNode focusNode: characterListUnsorted){
			characterListSorted.offer(focusNode);
		}
	}
	
	/**
	 * This class will compare a character to 'CharacterNode' objects in the
	 * Priority Queue, Create a 'characterNode' if one does not already exist.
	 * Otherwise increment the number of characters in the 'characterNode'
	 *  
	 *  @param	c used as an identifier for a character that will be used for comparison
	 *   */
	private void insertToCharacterQueue(char c){
		if(characterListUnsorted.size() == 0){
			characterListUnsorted.add(this.makeCharNode(c));
			totalNodesCreated +=1;
		}
		else {
			boolean itemFound = false;
				for(CharacterNode focusNode: characterListUnsorted){
					try {
						if(focusNode.getOrginalCharSymbol() == c){
							itemFound = true;
							focusNode.addOneChar(); // increment no chars in object
						}
					} catch(NullPointerException e){}
				}
			if(itemFound == false){
				characterListUnsorted.add(this.makeCharNode(c));
				totalNodesCreated +=1;
			}
		}
	}
	
	/**
	 * @return int returns the number of 'CharacterNode' objects in the 'characterList' PriorityQueue
	 * */
	private int returnCharListSize(){
		return characterListSorted.size();
	}
	
	/**This class created a 'characterNode' object.
	 * @param c used for the creation of a 'CharacterNode'
	 * @return CharacterNode returns the object created in the method
	 * */
	private CharacterNode makeCharNode(char c){
		CharacterNode newNode = new CharacterNode(c);
		return newNode;
	}
	
	/**
	 * This class created a arraylist to be used in other parts of the source code
	 * @return CharacterNode[] returns an arraylist of the 'characterList' priority queue
	 * */
	private CharacterNode[] returnCharListArray(){
		CharacterNode[] tempArray1 = new CharacterNode[95];
		CharacterNode[] tempArray2 = characterListSorted.toArray(tempArray1);
		return tempArray2;
	}
	
	/**
	 * This class reads a file line by line to read all the character to be added to the 'characterList'
	 * @param fileName the name of the file that will be read from */
	private void readDataFile(String fileName) {
		BufferedReader infile = null;
		try {
				int currentChar;
				infile = new BufferedReader(new FileReader(fileName));
				while ((currentChar = infile.read()) != -1) {
				char input = (char)currentChar;
						this.insertToCharacterQueue(input);
					
				} 
			} catch (FileNotFoundException e){ // If a file is not found the program will drop out.
				System.out.println("File not found, Pleas restart program.");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}  finally {
				try {
					if (infile != null)infile.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}	
		this.insertIntoSortedCharacterList();
	}
	
	/**
	 * @return CharacterNode this method will return the head of the 'characterList', 
	 * Which is the lowest amount of characterNode in the queue
	 * */
	private CharacterNode extractMin(){
		return characterListSorted.poll();
	}
	
	/**
	 * This class will take two of the lowest 'CharacterNode' objects out of the 'characterList'
	 * and combine them into a tree creating a new 'CharacterNode' which will act as a parent for 
	 * the two lowest 'CharacterNode' objects. And this will repeat until there is only one 'CharacterNode'
	 * left in the 'characterList', Creating a Huffman Tree.  */
	private void combineTwoLowestFreqChars(){
		do{
			this.printQueueData();
			CharacterNode lowest1 = this.extractMin();
			CharacterNode lowest2 = this.extractMin();
			CharacterNode parent = new CharacterNode('|',lowest1.getNoChars()+lowest2.getNoChars(),lowest1,lowest2);
			CharBinaryTree nodeTree = new CharBinaryTree();
			//nodeTree.addNode(lowest1, parent);
			//nodeTree.addNode(lowest2, parent);
			characterListSorted.add(parent);
			totalNodesCreated +=1;
			parent.setNodeDepth(nodeTree.determinDepth(parent, 0));
			nodeTree.setCompressionFileSize(parent, "");
			nodeTree.traverseTreeInPreOrder(parent);
		} while(characterListSorted.size()!=1);
		//this.printQueueData();
	}
	
	/**
	 * This method takes input from the user using a scanner.
	 * @return String a string read from user input
	 **/
	private String readFileIO(){
		Scanner fileName = new Scanner(System.in);
		System.out.println("enter file name: ");
		String file = fileName.next();
		fileName.close();
		return file;
	}
	
	/**
	 * This method iterates through an array of 'characterNode' objects, printing 
	 * information on the 'CharacterNode' objects, Was used for testing purposes
	 **/
	private void printQueueData() {
		System.out.println("Queue List: ");
		CharacterNode[] temp = this.returnCharListArray();
		for(int i = 0;i < this.returnCharListSize();i++){
			System.out.println(temp[i].getOrginalCharSymbol() + " - " + temp[i].getNoChars() + "- Depth: "+ temp[i].getNodeDepth());
		}
		System.out.println(" ");
	}
	
	
	
	/**
	 * This method will calculate the uncompressed and compressed size of the read
	 * file, also calculating the compression ratio and outputting it for the user.
	 * 
	 * @param node passing the root of the huffman tree to the method*/
	private void printEncodingData(CharacterNode node) {
		CharBinaryTree nodeTree = new CharBinaryTree();
		
		nodeTree.setCompressionFileSize(node, "");
		System.out.println("");
		System.out.println("Compression Data: - ");

		int unCompressSize = node.getNoChars()*3;
		float compressCalculation = nodeTree.getCompressFileSize();
		float compresstionRatio = (unCompressSize/compressCalculation);
		
		System.out.println("The Uncompressed File is: " + unCompressSize);
		System.out.println("The Compressed File is: " + compressCalculation);
		System.out.println("The Compression ratio is: " + compresstionRatio);
		
		System.out.println("");
		System.out.println("Tree Data: - ");
		System.out.println("Height of Tree is: " + node.getNodeDepth());
		System.out.println("Number of Nodes: " + totalNodesCreated);
		
		nodeTree.averageDepth(node);
		System.out.println("The Average Depth: " + nodeTree.getAverageDepth(totalNodesCreated));
		
		nodeTree.traverseTreeInPreOrder(node);
	}
	
	/**
	 * This method generates the huffman tree by calling other methods.*/
	public void generateHuffmanTree(){
			this.readDataFile(this.readFileIO());
			this.combineTwoLowestFreqChars();
			this.printEncodingData(this.extractMin());
	}
	/**
	 * Runs Huffman Encoding*/
	public static void main(String[] args)  {
		HuffmanEncoder generateHuffmenEncoding = new HuffmanEncoder();
		generateHuffmenEncoding.generateHuffmanTree();
	}

}
