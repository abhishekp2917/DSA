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
        if(root==null) return;
        traverse(root.left, list);
        traverse(root.right, list);
        list.add(root.data);
    }
}
