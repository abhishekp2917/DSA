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
	public static int countPairs(Node root1, Node root2, int x)
	{
		// initializing stacks to traverse both the BST	
		Stack<Node> stack1 = new Stack<>();
		Stack<Node> stack2 = new Stack<>();

		// count to store the answer
		int count = 0;

		while(true) {
			while(root1!=null) {
				stack1.push(root1);
				root1 = root1.left;
			}
		
			while(root2!=null) {
				stack2.push(root2);
				root2 = root2.right;
			}

			// if any of the stack is empth that means we have completed the traversal of atleast one of the BST
			// so there is no chance of getting any pair further. Hence, return from loop 
			if(stack1.isEmpty() || stack2.isEmpty()) break;

			int num1 = 0, num2 = 0;
			if(!stack1.isEmpty()) num1 = stack1.peek().data;
			if(!stack2.isEmpty()) num2 = stack2.peek().data;

			// if sum of pairs is smaller than target, then move first pointer forward 
			if (num1 + num2<x) {
				if(!stack1.isEmpty()) root1 = stack1.pop().right;
			}
			// if sum of pairs is larger than target, then move second pointer backward 
			else if(num1 + num2>x) {
				if(!stack2.isEmpty()) root2 = stack2.pop().left;
			} 
			// if sum is equakl to target, then increment the count and move both the pointer forward and backward resp.
			else {
				count++;
				if(!stack1.isEmpty()) root1 = stack1.pop().right;
				if(!stack2.isEmpty()) root2 = stack2.pop().left;
			}
		}

		return count;
	}
}