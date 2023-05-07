import java.util.ArrayList;
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

class Spiral
{
    //Function to return a list containing the level order 
    //traversal in spiral form.	

    // first Approach
    ArrayList<Integer> findSpiral1(Node root) 
    {
        // list to store result
        ArrayList<Integer> result = new ArrayList<Integer>();
        // using two list, one for poping elements and other for temp storing poped elements
        ArrayList<Node> curr = new ArrayList<Node>(), nxt; 
        // var to track lvl of tree
        int lvl = 1;
        if(root!=null) curr.add(root);
        
        while(!curr.isEmpty()) {

            // intializing temp list for storing poped elements
            nxt = new ArrayList<Node>();

            // adding results from the curr list into nxt list
            for(Node node: curr) {
                if(node.left!=null) nxt.add(node.left);
                if(node.right!=null) nxt.add(node.right);
            }

            // if curr lvl is even then adding elements from curr list into result list in normal fashion
            if(lvl%2==0) AddElements(curr, result);
            // if curr lvl is odd then adding elements from curr list into result list in reverse fashion
            else AddElementsInReverse(curr, result);
            lvl++;

            // finally, once curr list has been used, assign nxt list value to curr list
            // it's like moving to next lvl 
            curr = nxt;
        }
        return result;
    }
    
    void AddElements(ArrayList<Node> list, ArrayList<Integer> result) {
        for(Node node: list) {
            result.add(node.data);
        }
    }
    
    void AddElementsInReverse(ArrayList<Node> list, ArrayList<Integer> result) {
        for(int i=list.size()-1; i>=0; i--) {
            result.add(list.get(i).data);
        }
    }


    //second approach
    ArrayList<Integer> findSpiral2(Node root) 
    {
        // list to store result
        ArrayList<Integer> result = new ArrayList<Integer>();

        // using two stacks to traversa in alternate order (left->right and right->left)
        Stack<Node> stack1 = new Stack<Node>(), stack2 = new Stack<Node>(); 
        if(root!=null) stack2.add(root);
        
        while(!stack1.isEmpty() || !stack2.isEmpty()) {
            // if stack1 is not empty then for each poped element, first add left node and then right node in second stack
            // by doing this when poping element from second stack, traversal will happe from left to right
            while(!stack1.isEmpty()) {
                Node tempNode = stack1.pop();
                result.add(tempNode.data);
                if(tempNode.left!=null) stack2.add(tempNode.left);
                if(tempNode.right!=null) stack2.add(tempNode.right);
            }
            
            // if stack2 is not empty then for each poped element, first add right node and then left node in first stack
            // by doing this when poping element from first stack, traversal will happe from right to left
            while(!stack2.isEmpty()) {
                Node tempNode = stack2.pop();
                result.add(tempNode.data);
                if(tempNode.right!=null) stack1.add(tempNode.right);
                if(tempNode.left!=null) stack1.add(tempNode.left);
            }
        }
        return result;
    }
}