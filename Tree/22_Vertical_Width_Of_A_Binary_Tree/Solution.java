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
    public static int verticalWidth(Node root)
	{
	    if(root==null) return 0;
	    int[] range = new int[]{0, 0};

        // call the function to get the left most and right most node position of the tree
	    findVerticalWidth(root, 0, range);

        // return difference of left and right most position index plus 1
	    return range[1]-range[0]+1;
	}
	
    // parameter 'x' is to track horizontal position of each node
    // parameter 'range' is to store postion of leftest and rightest node 
	static void findVerticalWidth(Node root, int x, int[] range) {
	    if(root==null) return;

        // for each node, update leftest and righest position of the tree
	    range[0] = Math.min(range[0], x);
	    range[1] = Math.max(range[1], x);

        // for left subtree, reduce position 'x' by 1 and for right one increament it by 1 
	    findVerticalWidth(root.left, x-1, range);
	    findVerticalWidth(root.right, x+1, range);
	}  
}