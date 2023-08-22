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

    public static int inorderSuccessor(Node root, int val)
    {
        // initialize variable to store the answer
        int successor = -1;
        
        while(root!=null) {
            // if node value is equal or greater than target, then update the answer with that value as that value could be our answer
            // explore left as right subtree will have values greater which will be more farther than target node 
            if(root.data>=val) {
                successor = root.data;
                root = root.left;
            }
            // if current node data is smaller than target then don't need to add that node value in answer
            // if this case move right as possiblity of getting more closest to target will be greater on right
            else {
                root = root.right;
            }
        }
        return successor;
    }
}