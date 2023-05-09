import java.util.ArrayList;

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
    
    public static ArrayList<Integer> Ancestors(Node root, int target)
    {
        if(root==null) return null;
        
        // if we encounter target node while traversing, then return an empty ArrayList
        // we don't have to add target node
        if(root.data==target) {
            return new ArrayList<Integer>();
        }
        
        // traverse left and then right and store the result in a variable
        ArrayList<Integer> left = Ancestors(root.left, target);
        ArrayList<Integer> right = Ancestors(root.right, target);
        
        // if both left and right is null, that means both the subtree doesn't have that target node
        // so simply return null
        if(left==null && right==null) return null;

        // if any of the returned value is not null, that means current node is one of the ancestor of the
        // target node, so add the current node in the returned list and return it back to the top 
        if(left==null) {
            right.add(root.data);
            return right;
        }
        else{
            left.add(root.data);
            return left;
        }
    }
}