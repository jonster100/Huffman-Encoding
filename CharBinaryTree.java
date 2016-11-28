/**
 * Author: Job64
 * Date: 25/11/2016
 **/

public class CharBinaryTree {
	//CharacterNode root;
	private int averageDepth;
	private int compressFileSize;
	/**
	 * CharBinaryTree Constructor
	 * */
	public CharBinaryTree(){
		//root = node;
		//noNodesInTree =0;
		averageDepth = 0;
	}
	
	/**
	 * This method will add given nodes as children of a given root node
	 * @param newNode this is a new node to be added to the root as a child or added further down the tree
	 * @param root this is top node to be used to identify where the newNode should be in the tree.*/
	public void addNode(CharacterNode newNode,CharacterNode root){
			CharacterNode focusNode = root;
			CharacterNode parent = focusNode;
			boolean loopDone = false;
			while(loopDone!=true){
				if(newNode.getNoChars() < parent.getNoChars()){
					if(parent.getLeftChild() == null){
						parent.setLeftChild(newNode);
						parent.setNodeDepth(this.determinDepth(parent, 0));// sets the depth of the parent node
						loopDone = true;
					}
					else {
						parent = parent.getLeftChild();
					}
				}
				else {
					if(parent.getRightChild() == null){
						parent.setRightChild(newNode);
						parent.setNodeDepth(this.determinDepth(parent, 0)); // sets the depth of the parent node
						loopDone = true;
					}
					else {
						parent = parent.getRightChild();
					}
				}
			}
		
	}
	
	/**This method will return the depth of the given focus node
	 * @param focusNode the focus node is a parent given to find the depth of the node
	 * @param d is a identifier for the focusNode current depth of the beginning of the method
	 * @return int will return the newly established depth using the child depths*/
	public int determinDepth(CharacterNode focusNode, int d){
	    int leftDepth = d, rightDepth = d;
	     
	    if(focusNode.getLeftChild() != null){
	        leftDepth = determinDepth(focusNode.getLeftChild(), d+1);
	    }
	    if(focusNode.getRightChild() != null){
	        rightDepth = determinDepth(focusNode.getRightChild(), d+1);
	    }
	    return leftDepth > rightDepth ? leftDepth : rightDepth;
	}
	
	/**
	 * This method will list all the nodes in ascending order
	 * @param focusNode the root 'CharacterNode' to traverse around the tree from.
	 **/
	public void traverseTreeInOrder(CharacterNode focusNode){
		if(focusNode != null){
			traverseTreeInOrder(focusNode.getLeftChild());
			System.out.println(focusNode.getOrginalCharSymbol() + " - " + focusNode.getNoChars() + " Depth: " + focusNode.getNodeDepth());
			traverseTreeInOrder(focusNode.getRightChild());
		}
	}
	
	/**
	 * This method will list all the nodes in descending order
	 * @param focusNode the root 'CharacterNode' to traverse around the tree from.
	 **/
	public void traverseTreeInPreOrder(CharacterNode focusNode){
		if(focusNode != null){
			System.out.println(focusNode.getOrginalCharSymbol() + " - " + focusNode.getNoChars() + " - Depth: "+focusNode.getNodeDepth());
			traverseTreeInPreOrder(focusNode.getLeftChild());
			traverseTreeInPreOrder(focusNode.getRightChild());
		}
	}
	
	/**
	 * This method calculates the total depth to be used for the average tree depth
	 * @param focusNode this is the object that will be traversed around the tree from
	 * @param tempArray the array is passed through for the recursion
	 * */
	public void averageDepth(CharacterNode focusNode, char[] tempArray){
		char[] tempCharArray = tempArray;
		if(focusNode != null){
			boolean found = false;
			for(int i = 0; i < tempCharArray.length; i++){
				if(focusNode.getOrginalCharSymbol() == tempCharArray[i]){
					found = true;
					averageDepth += 1;//focusNode.getNodeDepth();
					
				}
			}
			if(found == false){
				for(int i = 0; i< tempCharArray.length;i++){
					if(tempCharArray[i] == 0){
						tempCharArray[i] = focusNode.getOrginalCharSymbol();
					}
				}
			}
			averageDepth(focusNode.getLeftChild(),tempCharArray);
			averageDepth(focusNode.getRightChild(),tempCharArray);
		}
		
	}
	
	public void setCompressionFileSize(CharacterNode focusNode,CharacterNode rootRoot){
		if(focusNode != null){
			if(focusNode.getOrginalCharSymbol() != '|'){
				compressFileSize +=(rootRoot.getNodeDepth()-focusNode.getNodeDepth())-2;
			}
			setCompressionFileSize(focusNode.getLeftChild(),rootRoot);
			setCompressionFileSize(focusNode.getRightChild(),rootRoot);
		}
	}
	
	public int getCompressFileSize(){
		return compressFileSize;
	}
	
	/**
	 * @param noNodes will be used in average depth calculation
	 * @return int returning the average depth of the tree */
	public int getAverageDepth(int noNodes) {
		return averageDepth/noNodes;
	}

	/*public CharacterNode getRoot() {
		return root;
	}*/
}
