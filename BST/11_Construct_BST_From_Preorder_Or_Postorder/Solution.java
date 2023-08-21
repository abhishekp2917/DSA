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
    static int index;
    
    public static Node constructTreeFromPostOrder(int post[],int n)
    {
        // intializing index. For postorder iterating array in reverse order
        index = n-1;
        return buidlBSTFromPostorder(post, Integer.MIN_VALUE);
    }
    
    static Node buidlBSTFromPostorder(int post[], int lowerBound) {
        // if current index value is less than the lower bound value, then return null
        if(index<0 || post[index]<lowerBound) return null;

        // else create new node, decrement index value and assign right and left child
        int data = post[index];
        Node root = new Node(data);
        index--;

        // for right subtree, make lower bound equal to root data
        root.right = buidlBSTFromPostorder(post, data);

        // for left subtree, make lower bound equal to root data lower bound
        root.left = buidlBSTFromPostorder(post, lowerBound);

        // after creating root node return it
        return root;
    }

    public static Node constructTreeFromPreOrder(int pre[],int n)
    {
        // intializing index. For preorder iterating array in normal order 
        index = 0;
        return buidlBSTFromPreorder(pre, Integer.MAX_VALUE);
    }
    
    static Node buidlBSTFromPreorder(int pre[], int upperBound) {
        // if current index value is greater than the upper bound value, then return null
        if(index>=pre.length || pre[index]>upperBound) return null;

        // else create new node, increment index value and assign left and right child
        int data = pre[index];
        Node root = new Node(data);
        index++;

        // for left subtree, make upper bound equal to root data
        root.left = buidlBSTFromPreorder(pre, data);

        // for right subtree, make upper bound equal to root data upper bound
        root.right = buidlBSTFromPreorder(pre, upperBound);

        // after creating root node return it
        return root;
    }
}