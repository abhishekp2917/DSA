
class Node {
    int data;
    Node left;
    Node right;
    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}


class Tree
{
    
    // Function to check whether a binary tree is balanced or not.
    boolean isBalanced(Node root)
    {
        // if function returns max int value, then the tree is not balanced one else it's a balanced tree
	    if(heightOfSubtree(root)==Integer.MAX_VALUE) return false;
	    else return true;
    }
    
    int heightOfSubtree(Node root) {
        
        
        if(root==null) return 0;
        // Traverse left side of tree and find the height of left subtree
        int left = heightOfSubtree(root.left);

        // Traverse right side of the tree and find the height of right subtree
        int right = heightOfSubtree(root.right);
        
        // if height of either of the subtree is max of int, that means it is not a balanced tree 
        // hence return max int value as we donb't need to check other condition
        if(left==Integer.MAX_VALUE || right==Integer.MAX_VALUE) return Integer.MAX_VALUE;

        // else check if the diff of height is greater than 1 or not
        // if it fails the condition then, return max int value showing it is not balanced tree
        if(Math.abs(left-right)>1) return Integer.MAX_VALUE;

        // if left and right child diff is not greater than 1, then that subtree is a balanced tree and 
        // hence return height of that subtree 
        return 1 + Math.max(left, right);
    }
}