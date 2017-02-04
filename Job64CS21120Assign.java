
/**
 * Author: Job64
 * Date: 25/11/2016
 **/

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.LinkedList;

public class Job64CS21120Assign<Adjacency> {
	private PriorityQueue<CharacterNode> characterListSorted;
	private LinkedList<CharacterNode> characterListUnsorted;
	private int totalNodesCreated;
	private int uniqueNoChars;

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
			/*if (x.getNoChars() < y.getNoChars())
	        {
	            return -1;
	        }
	        if (x.getNoChars() > y.getNoChars())
	        {
	            return 1;
	        }
	        return 0;*/
			return (x.getNoChars()<y.getNoChars())?-1:(x.getNoChars()>y.getNoChars())?1:0;
		}
	}
	
	/**
	 * Class Constructor Initialising: PriorityQueue */
	public Job64CS21120Assign(){
		characterListSorted = new PriorityQueue<CharacterNode>(125,new characterFreqComparator());
		characterListUnsorted = new LinkedList<CharacterNode>();
		totalNodesCreated = 0;
	}
	
	/**
	 * this method will insert the unsorted list of objects into the priority queue to be ordered*/
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
				for(int i = 0; i <characterListUnsorted.size();i++ ){
					try {
						if(characterListUnsorted.get(i).getOrginalCharSymbol() == c){
							itemFound = true;
							characterListUnsorted.get(i).addOneChar(); // increment no chars in object
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
	 * @param fileName the name of the file that will be read from 
	 * @throws FileNotFoundException if this exception is thrown the program will ending 
	 * asking the user to start again
	 * @throws IOException if this eception is thrown the program will print a stack-trace*/
	private void readDataFile(String fileName) {
		BufferedReader infile = null;
		try {
				int currentLine;
				infile = new BufferedReader(new FileReader(fileName));
				while ((currentLine = infile.read()) != -1) {
					char input = (char)currentLine;
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
		//this.printQueueData();
		uniqueNoChars = totalNodesCreated;
		do{
			//this.printQueueData();
			CharacterNode lowest1 = this.extractMin();
			CharacterNode lowest2 = this.extractMin();
			CharacterNode parent = new CharacterNode('|',lowest1.getNoChars()+lowest2.getNoChars(),lowest1,lowest2);
			HuffmanTree nodeTree = new HuffmanTree();
			characterListSorted.add(parent);
			totalNodesCreated +=1;
			parent.setNodeDepth(nodeTree.determinDepth(parent, 0));
			//nodeTree.setBinaryString(parent, "");
			//nodeTree.traverseTreeInPreOrder(parent);
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
		HuffmanTree nodeTree = new HuffmanTree();
	
		nodeTree.setBinaryString(node, "");
		System.out.println("");
		System.out.println("Compression Data: - ");

		int unCompressSize = this.powerN(node.getNoChars())*node.getNoChars();
		int compressSize = nodeTree.getCompressFileSize();
		double compresstionRatio = (double)unCompressSize/(double)compressSize;
		
		System.out.println("The Uncompressed File is: " + unCompressSize);
		System.out.println("The Compressed File is: " + compressSize);
		System.out.println("The Compression ratio is: " + compresstionRatio);
		
		System.out.println("");
		System.out.println("Tree Data: - ");
		System.out.println("Height of Tree is: " + node.getNodeDepth());
		System.out.println("Number of Nodes: " + totalNodesCreated);
		
		nodeTree.averageDepth(node);
		System.out.println("The Average Depth: " + nodeTree.getAverageDepth(totalNodesCreated));
		
		//nodeTree.traverseTreeInPreOrder(node);
	}
	 
	/**this method will determin the fixed encoding length for the given amount of characters
	 * @param tepBase the base parameter for the total number of character
	 * @param int returning the number of bits for the fixed encoding length*/
	 public int powerN(int tempBase) {
		 int noBits = 1;
		 int base = 2;
		 while(base <= uniqueNoChars){	
			 base = 2*base;
			 noBits++;
		 }
		 return noBits;//-(noBits-uniqueNoChars);
	}

	/**
	 * This method generates the huffman tree by calling other methods.*/
	public void generateHuffmanTree(){
			this.readDataFile(this.readFileIO());
			this.combineTwoLowestFreqChars();
			this.printEncodingData(characterListSorted.peek());
	}
	/**
	 * Runs Huffman Encoding*/
	public static void main(String[] args)  {
		Job64CS21120Assign generateHuffmenEncoding = new Job64CS21120Assign();
		generateHuffmenEncoding.generateHuffmanTree();
	}

}
