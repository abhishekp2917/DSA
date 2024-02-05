import java.util.ArrayList;
import java.util.Stack;

class Node{
    int data;
    Node left,right;
    Node(int d){
        data=d;
        left=right=null;
    }
}

// Approach 1 : Recursion
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


// Approach 2 : Iterative
class Tree
{
    // Return a list containing the Preorder traversal of the given tree
    ArrayList<Integer> preOrder(Node root)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        // create stack to traverse tree in DFS fashion
        Stack<Node> stack = new Stack<Node>();
        // push root element into the stack
        stack.add(root);
        
        while(!stack.isEmpty()) {
            // pop the top of the stack and add it to the answer 
            // we are adding the poped element before traversing it's child because we are traversing in pre-order fashion
            Node temp = stack.pop();
            list.add(temp.data);
            
            // if current root element has right child, then add it to the stack
            // after that if current root element has left child, add it to the stack
            // we are adding right child first and left later beacuse in stack we always access later added element first
            if(temp.right!=null) stack.push(temp.right);
            if(temp.left!=null) stack.push(temp.left);
        }
        return list;
    }
    
}