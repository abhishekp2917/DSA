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

    static int canRepresentBST(int arr[], int N) {

        // declaring stack 
        Stack<Integer> stack = new Stack<>();

        // declaring lower bound 
        int root = Integer.MIN_VALUE;

        for(int i=0; i<N; i++) {
            // if new value is smaller than lower bound, then array doesn't represents BST
            if(arr[i]<root) return 0;

            // keep poping the top element if new value is greater than top of the stack, 
            // the last element that poped out will be the root element of new value and whatever will come after this new node will be child of new node
            // so all the child of new node should be greater than root element (lasted poped element)
            while(!stack.isEmpty() && stack.peek()<arr[i]) {
                root = stack.peek();
                stack.pop();
            }

            // push the new node in stack
            stack.push(arr[i]);
        }
        return 1;
    }
}