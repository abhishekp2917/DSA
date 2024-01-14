import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

class Tree
{
    
    public ArrayList<Integer> reverseLevelOrder(Node root) 
    {
        // using stack to store node values temporarily. Once traversal is done, while poping out the 
        // stack values, it will happen in reverse level order
        Stack<Integer> stack = new Stack<Integer>();

        // using queue for normal level order traversal
        Queue<Node> queue = new LinkedList<Node>();
        stack.add(root.data);
        queue.add(root);
        
        // list to store final output
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        while(!queue.isEmpty()) {
            Node tempNode = queue.remove();
            
            // first adding right child value cause for each level while poping out the stack values,
            // the order of nodes will be from left to right which is what we want
            if(tempNode.right!=null) {
                queue.add(tempNode.right);
                stack.push(tempNode.right.data);
            }
            if(tempNode.left!=null) {
                queue.add(tempNode.left);
                stack.push(tempNode.left.data);
            }
        }
        
        // poping out stack element and adding it to the final result list
        while(!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return result;
    }
}      
