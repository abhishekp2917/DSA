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
    // return the Kth largest element in the given BST rooted at 'root'
    public int kthLargest(Node root,int k)
    {
        // create a variable that is pass by reference so that it will get updated during searching process
        int[] kthLargest = new int[1];
        findKthLargest(root, k, kthLargest);
        return kthLargest[0];
        
    }

    // return the Kth smallest element in the given BST rooted at 'root'
    public int kthSmallest(Node root,int k)
    {
        // create a variable that is pass by reference so that it will get updated during searching process
        int[] kthSmallest = new int[1];
        findKthSmallest(root, k, kthSmallest);
        return kthSmallest[0];
        
    }
    
    int findKthLargest(Node root, int k, int[] kthLargest) {

        // when root is null the return k as it is cause we will decrement it only when we will encounter a node
        if(root==null) return k;

        // traverse in inorder fashion but in reverse order so that very first node will be the largest node 
        k = findKthLargest(root.right, k, kthLargest);

        // if value of k is equal to 1, that means that node is our answer so assign this value to refernce variable  
        if(k==1) kthLargest[0] = root.data;

        // keep decrementing k to track the count of visited node
        k--;
        k = findKthLargest(root.left, k, kthLargest);

        // return the updated k value 
        return k;
    }
    
    int findKthSmallest(Node root, int k, int[] kthSmallest) {

        // when root is null the return k as it is cause we will increment it only when we will encounter a node
        if(root==null) return k;

        // traverse in inorder fashion so that very first node will be the smallest node 
        k = findKthSmallest(root.left, k, kthSmallest);

        // if value of k is equal to 1, that means that node is our answer so assign this value to refernce variable  
        if(k==1) kthSmallest[0] = root.data;

        // keep decrementing k to track the count of visited node
        k--;
        k = findKthSmallest(root.right, k, kthSmallest);

        // return the updated k value 
        return k;
    }
}