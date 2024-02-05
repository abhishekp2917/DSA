import java.util.ArrayList;
import java.util.Stack;

class Node {
    int data;
    Node left, right;
   Node(int item)    {
        data = item;
        left = right = null;
    }
} 

// Approach 1 : Recursion
class BinaryTree {
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


// Approach 2 : Iterative
class Tree
{
    // Return a list containing the inorder traversal of the given tree
    ArrayList<Integer> inOrder(Node root)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        // create a stack to traverse in DFS fashion
        Stack<Node> stack = new Stack<Node>();
        // add root into the stack
        stack.add(root);
        
        while(!stack.isEmpty()) {
            // keep adding left child into the stack until there is no left child remain
            Node temp = stack.peek();
            if(temp.left!=null) stack.push(temp.left);
            else {
                // if there is no left child, keep poping the node which doesn't have right child and
                // add it to the list
                // if there is a node having right child, add that node to the list and it's child into the stack
                // for traversal
                while(!stack.isEmpty() && stack.peek().right==null) list.add(stack.pop().data);
                if(!stack.isEmpty() && stack.peek().right!=null) {
                    Node top = stack.pop();
                    list.add(top.data);
                    stack.push(top.right);
                }
            }
        }
        return list;
    }
}




