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

class GFG
{
    public static boolean isDeadEnd(Node root)
    {
        return checkIfHasDeadEnd(root, 1, Integer.MAX_VALUE);
    }
    
    // this function takes min and max value a tree can have 
    static boolean checkIfHasDeadEnd(Node root, int min, int max) {

        if(root==null) return false;

        // for a leaf node, if max-min is positive, that means that leaf node can have atleast one child so retur false
        // else return true as one can't connect any node further to it 
        if(root.left==null && root.right==null) {
            if(max-min>0) return false;
            else return true;
        }
        
        // traverse left and right and return logical OR of the returned value
        return checkIfHasDeadEnd(root.left, min, root.data-1) || checkIfHasDeadEnd(root.right, root.data+1, max);
    }
}