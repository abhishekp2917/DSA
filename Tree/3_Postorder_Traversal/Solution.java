import java.util.ArrayList;

class Node {
    int data;
    Node left, right;
   Node(int item)    {
        data = item;
        left = right = null;
    }
}

class Tree
{
    // Function to return a list containing the postorder traversal of the tree.
    ArrayList<Integer> postOrder(Node root)
    {
        ArrayList<Integer> al = new ArrayList<Integer>();
        traverse(root, al);
        return al;
    }
    
	// Traversing in Postorder fashion
    static void traverse(Node root, ArrayList<Integer> list) {

        // this is the base condition 
        // if root is null then we can't move left or right from there. Thus, return from there
        if(root==null) return;

        // if root is not null, move to left
        traverse(root.left, list);

        // after returning from left, move to right
        traverse(root.right, list);

        // at last add root node data 
        list.add(root.data);
    }
}
