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
    // declaring global variable count to keep track of the largest element count while traversing tree
    // another global variable to store kth largest element once we get that during traversal
    int count = 1, kthLargest;
    
    // return the Kth largest element in the given BST rooted at 'root'
    public int kthLargest(Node root,int K)
    {
        findKthLargest(root, K);
        return kthLargest;
    }
    
    public void findKthLargest(Node root, int K) {
        if(root==null) return;

        // first moving to right subtree cause in BST rightest node will holds the largest value
        findKthLargest(root.right, K);

        // once returned from right, then only we will check if current count is equal to value K
        // if current count is eual to K, then set the global variable kthLargest as current node value 
        // we are checking this condition after returning right because we have to follow in-order traversal.
        // if we travese in inorder fashion, then in BST the order of node value will be in increasing/decreasing order
        if(count==K) kthLargest = root.data;

        // aftering checking for kth largest node, increment the count value by 1
        count++;
        findKthLargest(root.left, K);
    }
}