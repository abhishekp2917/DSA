
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


public class Solution
{
    //Function to check whether a Binary Tree is BST or not.
    boolean isBST(Node root)
    {
        return findIfBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    boolean findIfBST(Node root, int min, int max) {
        
        // if node is null, then tree is BST    
        if(root==null) return true;

        // node value should reside between given range else it's not a BST
        if(root.data>=max || root.data<=min) return false;

        // traverse either side of node to check the above condition
        return findIfBST(root.left, min, root.data) && findIfBST(root.right, root.data, max);
    }
}
