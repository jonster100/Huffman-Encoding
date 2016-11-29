/**
 * Author: Job64
 * Date: 25/11/2016
 **/

public class HuffmanTree {
	private float averageDepth;
	private int compressFileSize;
	
	/**
	 * CharBinaryTree Constructor
	 * */
	public HuffmanTree(){
		compressFileSize = 0;
		averageDepth = 0;
	}
	
	/**
	 * This method will add given nodes as children of a given root node, using principles of binary search
	 * @param newNode this is a new node to be added to the root as a child or added further down the tree
	 * @param root this is top node to be used to identify where the newNode should be in the tree.*/
	/*public void addNode(CharacterNode newNode,CharacterNode root){
			CharacterNode focusNode = root;
			CharacterNode parent = focusNode;
			boolean loopDone = false;
			while(loopDone!=true){
				if((newNode.getNoChars() < parent.getNoChars())){
					if(parent.getLeftChild() == null ){
						parent.setLeftChild(newNode);
						parent.setNodeDepth(this.determinDepth(parent, 0));// sets the depth of the parent node
						loopDone = true;
					}
					else {
						parent = parent.getLeftChild();
					}
				}
				else {
					if(parent.getRightChild() == null && this.isLeaf(parent)){
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
	*/
	
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
	 * This method will list all the nodes in descending order
	 * @param focusNode the root 'CharacterNode' to traverse around the tree from.
	 **/
	public void traverseTreeInPreOrder(CharacterNode focusNode){
		if(focusNode != null){
			System.out.println(focusNode.getOrginalCharSymbol() + " - " + focusNode.getNoChars() + " - Depth: "+focusNode.getNodeDepth() + "- Code: " + focusNode.getNBits());
			traverseTreeInPreOrder(focusNode.getLeftChild());
			traverseTreeInPreOrder(focusNode.getRightChild());
		}
	}
	
	/**
	 * This method calculates the total depth to be used for the average tree depth
	 * @param focusNode this is the object that will be traversed around the tree from
	 * @param tempArray the array is passed through for the recursion
	 * */
	public void averageDepth(CharacterNode focusNode){
		
		averageDepth += focusNode.getNodeDepth()+1;
		if(focusNode.getLeftChild() != null){
			averageDepth(focusNode.getLeftChild());
		}
		if(focusNode.getRightChild() != null){
			averageDepth(focusNode.getRightChild());
		}
		
	}
	
	/**
	 * This method will set the new number of bits for each of the characters and add the the compressed file size.
	 * @param focusNode the root node to recurs through the tree from
	 * @param tempNBits this parameter will become the new ChararacterNode's noBits*/
	public void setCompressionFileSize(CharacterNode focusNode,String tempNBits){
		String charNBits = tempNBits;
		focusNode.setCode(charNBits);
		if(focusNode.getOrginalCharSymbol() !='|'){
			compressFileSize += (charNBits.toCharArray().length*focusNode.getNoChars());
		}
		if(focusNode.getLeftChild() != null){
			setCompressionFileSize(focusNode.getLeftChild(), charNBits+"0");
		}
		if(focusNode.getRightChild() != null){
			setCompressionFileSize(focusNode.getRightChild(),charNBits+"1");
		}
	}
	
	/**
	 *@return int returns the size of the compressed tree */
	public int getCompressFileSize(){
		return compressFileSize;
	}
	
	/**
	 * @param noNodes will be used in average depth calculation
	 * @return float returning the average depth of the tree */
	public float getAverageDepth(float noNodes) {
		return averageDepth/noNodes;
	}

}
