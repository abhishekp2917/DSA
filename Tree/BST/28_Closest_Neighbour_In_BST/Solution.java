class Node
{
    int key;
    Node left, right;

    Node(int item)
    {
        key = item;
        left = right = null;
    }
}

class Tree
{

    public static int findMaxForN(Node root, int val)
    {
        if(root==null) return 0;
        int left = 0, right = 0;

        // if curr node value is less than target value, then travere right
        // else if it's greater than target value, then traverse left
        // if it's equal, then just return curr node value
        if(root.key<val) right = findMaxForN(root.right, val);
        else if(root.key>val) left = findMaxForN(root.left, val);
        else return root.key;
        
        // if curr node value is less than target value then return max of curr node value, left and right 
        if(root.key<val) return Math.max(root.key, Math.max(left, right));
        // else return max of left and right
        else return Math.max(left, right);
    }
}