// Structure of Node Class
class Node
{
	int data;
	Node left, right;
	Node(int d)
	{
		data = d;
	}
}
  

public class Solution
{
    //Function to check whether a Binary Tree is BST or not.
    boolean isBST(Node root)
    {
        // initially max and min value will be Infinity and -Infinity 
        return findIfBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    boolean findIfBST(Node root, int min, int max) {
        
        // if root is null then return true as empty subtree is also a BST  
        if(root==null) return true;

        // for a particular node, check if its value resides in give range ([min, max])
        // if node value is out of range then return false as that node doesn't satisfy BST property
        if(root.data>=max || root.data<=min) return false;

        // traverse left and right 
        // for left traversal, max value will become current node value minus 1
        // similarly, for right traversal, min value will become current node value plus 1
        // return AND logic of the returned values as both the left and right subtree must be BST for the root tree to be BST
        return findIfBST(root.left, min, root.data) && findIfBST(root.right, root.data, max);
    }
}
