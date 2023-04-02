import java.util.ArrayList;

class Node{
    int data;
    Node left,right;
    Node(int d){
        data=d;
        left=right=null;
    }
}

class BinaryTree
{
    //Function to return a list containing the preorder traversal of the tree.
    static ArrayList<Integer> preorder(Node root)
    {
        ArrayList<Integer> al = new ArrayList<Integer>();
        traverse(root, al);
        return al;
    }
    
    // Traversing tree in Preorder fashion
    static void traverse(Node root, ArrayList<Integer> list) {
        if(root==null) return;
        list.add(root.data);
        traverse(root.left, list);
        traverse(root.right, list);
    }
}