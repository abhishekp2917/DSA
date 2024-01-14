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
    // declare a global variable which will track whether tree is balanced or not
    // intially it will be set to true and will be changed only if tree is not balanced
    boolean isBalancedTree = true;
    
    //Function to check whether a binary tree is balanced or not.
    boolean isBalanced(Node root)
    {
	    heightOfSubtree(root);
	    return isBalancedTree;
    }
    
    int heightOfSubtree(Node root) {
        
        // if root is null then return 0 as without head, the tree height will be 0
        if(root==null) return 0;

        // move left to get the maximum height of the left subtree
        int left = heightOfSubtree(root.left);

        // move right to get the maximum height of the right subtree
        int right = heightOfSubtree(root.right);
        
        // difference of left and right subtree max height is greater than 1, then 
        // set the global variable as false  
        if(Math.abs(left-right)>1) isBalancedTree = false;

        // atlast return max of left and right plus 1 (plus 1 to include root node in subtree height)
        // we are taking maximum value out of two cause we need to know the max height of the tree
        return 1 + Math.max(left, right);
    }
}