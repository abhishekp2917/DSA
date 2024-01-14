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

class Solution {
    Node tempLeaf = null;
    
    public void pairwiseSwap(Node root){
       swap(root);
    }
    
    // traverse the tree in DFS fashion
    public void swap(Node root) {
        if(root==null) return;
        
        // if the current node is a leaf node then if gobal var tempLeaf is not null then the current node
        // is pair of that previous leaf node so just swap the node values and make global var null 
        // if global variable is null, then assign curr node ref to it
        if(root.left==null && root.right==null) {
            if(tempLeaf==null) {
                tempLeaf = root;
            }
            else {
               int tempData = tempLeaf.data;
               tempLeaf.data = root.data;
               root.data = tempData;
               tempLeaf = null;
            }
        }
        swap(root.left);
        swap(root.right);
    }
}