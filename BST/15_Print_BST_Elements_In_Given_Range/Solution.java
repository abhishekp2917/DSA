import java.util.ArrayList;

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

class Solution
{   
    
    //Function to return a list of BST elements in a given range.
	public static ArrayList<Integer> printNearNodes(Node root,int low,int high) {
        ArrayList<Integer> nearNodes = new ArrayList<Integer>();
        traverseBST(root, low, high, nearNodes);
        return nearNodes;
    }
    
    static void traverseBST(Node root, int low, int high, ArrayList<Integer> nearNodes) {
        if(root==null) return;

        //  move to left only if the current node value is greater than given lower bound
        if(root.data>low) traverseBST(root.left, low, high, nearNodes);

        // add the node in result only if that node resides in given range
        if(root.data>=low && root.data<=high) {
            nearNodes.add(root.data);
        }
        // move to right only if the current node value is smaller than given upper bound
        if(root.data<high) traverseBST(root.right, low, high, nearNodes);
    }
}