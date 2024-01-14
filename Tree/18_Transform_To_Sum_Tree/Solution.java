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
    
    public void toSumTree(Node root){
        
        convertToSumTree(root);
    }
    
    int convertToSumTree(Node root) {

        // if root is null return 0 cause we need to set the leaf node as 0. This will happen if leaf node left and right
        // returns 0. Then we will add them up which will result in 0 and assign it to leaf node
        if(root==null) return 0;

        // getting left and right subtree sum 
        int left = convertToSumTree(root.left), right = convertToSumTree(root.right);
        
        // temporarily store root data. Once we will assign root data with left and right subtree total
        // we will return summation of left, right and root (temp) which will be the total of curent subtree nodes  
        int temp = root.data;
        root.data = left + right;
        return temp + root.data;
    }
}