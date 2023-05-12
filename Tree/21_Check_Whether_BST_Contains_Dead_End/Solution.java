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
    
    // function to check if there is a dead end in BST
    // here min and max is to track available range of values to add as a child for a particular node 
    static boolean checkIfHasDeadEnd(Node root, int min, int max) {

        // if there is no root node then there is no possibility of having dead end for that particular subtree
        if(root==null) return false;

        // once reached to leaf node, check from avl ranges if there is a posiblity of adding atleast one child
        // if max-min is not 0 then we can add atleast one node so return true else not and return false
        if(root.left==null && root.right==null) {
            if(max-min>0) return false;
            else return true;
        }
        
        // while traversing left subtree, set max value to curr node value minus 1 
        // while traversing right subtree, set min value to curr node value plus 1 
        return checkIfHasDeadEnd(root.left, min, root.data-1) || checkIfHasDeadEnd(root.right, root.data+1, max);
    }
}