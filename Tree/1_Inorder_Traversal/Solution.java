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
        // this is the base condition 
        // if root is null then we can't move left or right from there. Thus, return from there
        if(root==null) return;

        // first recursively call the function and move left of the root node
        traverse(root.left, list);

        // add the root data after moving left
        list.add(root.data);

        // finally move right
        traverse(root.right, list);
    }
}




