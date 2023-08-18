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

class Tree
{
    public static int inorderPredecessor(Node root, int val)
    {
        // initialize variable to store the answer
        int predecessor = -1;
        
        while(root!=null) {
            // if node value is equal or smaller than target, then update the answer with that value as that value could be our answer
            // explore right 
            if(root.data<=val) {
                predecessor = root.data;
                root = root.right;
            }
            // if current node data is greater than target then don't need to add that node value in answer
            // if this case move left as possiblity of getting more closest to target will be greater on left
            else {
                root = root.left;
            }
        }
        return predecessor;
    }
}