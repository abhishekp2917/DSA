class Node
{
    int data;
    Node left, right;

    Node(int item)
    {
        data = item;
        left = right = null;
    }
} 

class Solution{
    
    static int largestBst(Node root)
    {      
		return traverse(root).maxSize;   
    }

	static NodeData traverse(Node root) {

		// if root is null then return min value as infinity, max value as -infinity, max size of BST as zero and true as null is also a BST
		if(root==null) return new NodeData(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
		
		// if root is leaf node, then return min and max value equal to node data, max BST size equal to 1 as there is only one node in that subtree and true
		// as single node tree is always a BST 
		if(root.left==null && root.right==null) return new NodeData(true, 1, root.data, root.data);

		NodeData left = traverse(root.left);
		NodeData right = traverse(root.right);

		// if both left and right subtree is BST then check if root tree is also a BST or not
		if(left.isBST && right.isBST && left.maxValue<root.data && right.minValue>root.data) 
			// if root tree is also a BST then return min and max of value of root tree, max BST size will be summation of 
			// left and right tree max size plus 1, and true as root tree is also a BST
			return new NodeData(true, left.maxSize + right.maxSize + 1, Math.min(root.data, left.minValue), Math.max(root.data, right.maxValue)); 
		else 
			// if root tree is not BST, then return min and max size of root tree, max BST size will be max of max BST size of left tree
			// and max BST size of right tree, false indicating root tree is not BST
			return new NodeData(false, Math.max(left.maxSize, right.maxSize), Integer.MAX_VALUE, Integer.MIN_VALUE);
	}
}



// creating class that wll encapsulate the min and max value of a subtree, max Size of BST present 
// in that subtree and boolean value indicating whether that subtree is BST or not 
class NodeData {
	boolean isBST;
	int maxSize;
	int minValue;
	int maxValue;

	public NodeData(boolean isBST, int maxSize, int minValue, int maxValue) {
		this.isBST = isBST;
		this.maxSize = maxSize;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
}