import java.util.ArrayList;
import java.util.Collections;
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
class BinaryTree
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

// Approach 2 : Iterative by reversing preorder traversal to get postorder
class Tree1 {
     // Return a list containing the Postorder traversal of the given tree
     ArrayList<Integer> postOrder(Node root)
     {
        ArrayList<Integer> list = new ArrayList<Integer>();
        // create stack to traverse tree in DFS fashion
        Stack<Node> stack = new Stack<Node>();
        // push root element into the stack
        stack.add(root);
        
        
        // traverse in pre-order fashion in which, travserse first right child and then left child 
        // we are doing this because, postorder of a tree is same as reverse of it's preorder traversal  
        while(!stack.isEmpty()) {
            // pop the top of the stack and add it to the answer 
            // we are adding the poped element before traversing it's child because we are traversing in p-order fashion
            Node temp = stack.pop();
            list.add(temp.data);
            
            // if current root element has left child, then add it to the stack
            // after that if current root element has right child, add it to the stack
            // we are adding left child first and right later beacuse in stack we always access later added element first
            // this ay wee will traverse right child first and then left child
            if(temp.left!=null) stack.push(temp.left);
            if(temp.right!=null) stack.push(temp.right);
        }
        // once we got the preorder traversal, reversing the result will give postorder traversal
        Collections.reverse(list);
        return list;
     }
}


// Approach 3 : Iterative 
class Tree2 {
    // Return a list containing the Postorder traversal of the given tree
    ArrayList<Integer> postOrder(Node root)
    {
       ArrayList<Integer> list = new ArrayList<Integer>();
       // create stack to traverse tree in DFS fashion
       Stack<Node> stack = new Stack<Node>();
       
       while(root!=null) {
            // keep adding left subtree nodes in the stack
            while(root!=null) {
                stack.add(root);
                root = root.left;
            }
            // once there is no left subtree left, make root equal to right node which has the right node
            // to find that, keep poping element who doesn't have right child and add them into answer 
            Node lastPoped = null;
            while(!stack.isEmpty() && (stack.peek().right==null || stack.peek().right==lastPoped)) {
                lastPoped = stack.pop();
                list.add(lastPoped.data);
            }
            // if we find the element whose right child is not null, make root equal to it's right child to traverse it
            // we aren't adding curent root node to answer as right child traversal is still pending
            if(!stack.isEmpty()) root = stack.peek().right;
       }
       return list;
    }
}