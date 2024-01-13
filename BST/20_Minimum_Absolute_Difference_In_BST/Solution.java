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
    // variable to hold prev node value so that we can find the diff of current node and its predecessor
    int prevVal = Integer.MAX_VALUE;
    // variable to hold min abs diff
    int minAbsDiff = Integer.MAX_VALUE;
    
    int absolute_diff(Node root)
    {
        traverse(root);
        return minAbsDiff;
    }
    
    void traverse(Node root) {
        if(root==null) return;

        // traversing tree in inorder fashion so that we will get node values in sorted order
        // if we get values in sorted order, then we just have to check if any 2 adjacent node abs diff is smaller then current min diff
        traverse(root.left);
        // update min abs diff if current adjacent node abs diff is smaller than min abs diff
        minAbsDiff = Math.min(minAbsDiff, Math.abs(prevVal-root.data));
        // update prev value with current node value cause for next node current node will be its adjacent node
        prevVal = root.data;
        traverse(root.right);
    }
}