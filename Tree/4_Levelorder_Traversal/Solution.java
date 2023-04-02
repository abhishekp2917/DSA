import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
    // Function to return a list containing the levelorder traversal of the tree.
    ArrayList<Integer> levelOrder(Node root)
    {
        ArrayList<Integer> al = new ArrayList<Integer>();
        traverse(root, al);
        return al;
    }
    
	// Traversing in Levelorder fashion
    static void traverse(Node root, ArrayList<Integer> list) {
        
        // creating a queue to store tree nodes 
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);

        // while queue is not empty
        while(queue.size()!=0) {

            // poll the node that is in front of the queue
            Node temp = queue.poll();
            // add the polled data in the list
            list.add(temp.data);
            // if polled node has left child then add that node behild the queue 
            if(temp.left!=null)queue.add(temp.left);
            // if polled node has right child then add that node behild the queue 
            if(temp.right!=null)queue.add(temp.right);
        }
    }
}
