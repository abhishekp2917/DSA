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
        // this is the base condition 
        // if root is null then we can't move left or right from there. Thus, return from there
        if(root==null) return;

        // before moving left or right, first add root data 
        list.add(root.data);

        // after adding root data, move left
        traverse(root.left, list);

        // finally move right
        traverse(root.right, list);
    }
}