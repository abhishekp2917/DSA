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
    //Function to check if two trees are identical.
	boolean isIdentical(Node root1, Node root2)
	{
        // if both roots are null, then return true.
	    if(root1==null && root2==null) return true;

        // both the roots aren't null both are same then traverse left and right parallelly and return AND logic of both the traversal result
        // if any of the subtree isn't identical then AND operation will be false
	    if(root1!=null && root2!=null && root1.data==root2.data) {
	       return isIdentical(root1.left, root2.left) && isIdentical(root1.right, root2.right);
	    }
	    return false;
	}
	
}