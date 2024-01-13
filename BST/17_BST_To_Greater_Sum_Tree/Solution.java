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
    public static void transformTree(Node root)
    {
        // declare refernec vaiable to store curr sum value intialized to zero
        int[] sum = new int[1];
        traverse(root, sum);
    }

    public static void traverse(Node root, int[] sum) {
        // if root is null then just return
        if(root==null) return;

        // traverse tree in reverse inorder fashion i.e. right -> root -> left
        traverse(root.right, sum);

        // temporarily store current root data so that after updating root data, we can add its previous value to ref. var sum 
        int temp = root.data;

        // update current root node value
        root.data = sum[0];

        // add current node previous value to the ref. var sum
        sum[0] += temp;
        traverse(root.left, sum);
    }
}