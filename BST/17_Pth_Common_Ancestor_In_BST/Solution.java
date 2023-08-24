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

class Solution {

    public int pthAncestor(Node root, int x, int y, int p, ArrayList<Integer> s){

        // declaring stack to store the list of ancestors
        Stack<Integer> stack = new Stack<>();
        int ans = -1;
        
        while(root!=null) {
            // add current node value to stack and then check which node to traverse next either left or right
            stack.push(root.data);

            // if the root node value is larger than both the target node, then explore left subtree 
            if(root.data>x && root.data>y) {
                root = root.left;
            }
            // if the root node value is smaller than both the target node, then explore right subtree 
            else if(root.data<x && root.data<y) {
                root = root.right;
            }
            // else break frop loop as latest entered node is the last ancestor
            else break;
        }
        
        // keep updating the ans while poping the stack 
        // keep poping until p becomes zero
        while(!stack.isEmpty() && p>0 && stack.size()>=p) {
            ans = stack.pop();
            p--;
        }

        return ans;
    }

}