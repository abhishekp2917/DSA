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

class Solution
{
	public static ArrayList<Integer> findCommon(Node root1,Node root2)
    {
        ArrayList<Integer> commonNodes = new ArrayList<Integer>();

        // using two stacks to traverse in dfs fashion (inorder traversal)
        Stack<Node> stack1 = new Stack<Node>(), stack2 = new Stack<Node>();

        // adding all the left nodes in stack1 and stack2 from root1 and root2
        AddLeftNodes(root1, stack1);
        AddLeftNodes(root2, stack2);

        // if both the stack isn't empty the only perform any operation
        // cause if any of the stack is empty then there is no possiblity of having common nodes
        while(!stack1.isEmpty() && !stack2.isEmpty()) {

            // taking top element of both the stack for comparision
            Node node1 = stack1.peek(), node2 = stack2.peek();

            // if those two top nodes aren't the same then pop out the smaller one and 
            // add all the left nodes of that poped right node
            if(node1.data>node2.data) {
                AddLeftNodes(stack2.pop().right, stack2);
            }
            else if(node1.data<node2.data) {
                AddLeftNodes(stack1.pop().right, stack1);
            }

            // if both the top nodes are same then add that node in the result and follow the 
            // steps simillar to what we did in case if nodes aren't the same.
            // but this time perform those steps for both the stack
            else {
                commonNodes.add(node1.data);
                AddLeftNodes(stack1.pop().right, stack1);
                AddLeftNodes(stack2.pop().right, stack2);
            }
        }

        // atlast return the result list
        return commonNodes;
    }

    static void AddLeftNodes(Node root, Stack<Node> stack) {
        while(root!=null) {
            stack.add(root);
            root = root.left;
        }
    }
}