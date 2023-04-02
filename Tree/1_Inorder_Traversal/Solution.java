import java.util.ArrayList;

class Node {
    int data;
    Node left, right;
   Node(int item)    {
        data = item;
        left = right = null;
    }
} 


class Solution {
    // Function to return a list containing the inorder traversal of the tree.
    ArrayList<Integer> inOrder(Node root) {
       ArrayList<Integer> al = new ArrayList<Integer>();
       traverse(root, al);
       return al;
    }
    
	// Traversing tree in Inorder fashion
    static void traverse(Node root, ArrayList<Integer> list) {
        if(root==null) return;
        traverse(root.left, list);
        list.add(root.data);
        traverse(root.right, list);
    }
}