class Node {
    int data;
    Node left, right;
   Node(int item)    {
        data = item;
        left = right = null;
    }
}

class Solution{
    
    // declaring variable to store the sum as we traverse the tree
    int sum = 0;
    
    public int leftLeavesSum(Node root)
    {
       traverse(root, false);
       return sum;
    }
    
    // traverse method accepts root node as well as boolean value 
    // this boolean value is to indicate whether the root node is a left child or right child of the parent node
    void traverse(Node root, boolean isLeftChild) {
        if(root==null) return;
        // if we reach the leaf node, check if the current leaf node is left child or not based on the boolean value passed
        // if it is left child, add the node value to sum and return
        if(root.left==null && root.right==null) {
            if(isLeftChild) sum += root.data;
            return;
        }

        // traverse left subtree and pass true as second argument indicating it's a left child  
        traverse(root.left, true);
         // traverse right subtree and pass false as second argument indicating it's a right child
        traverse(root.right, false);
    }

}