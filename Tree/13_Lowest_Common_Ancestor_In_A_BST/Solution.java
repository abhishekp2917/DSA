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

class BST
{   
    //Function to find the lowest common ancestor in a BST. 
	Node LCA(Node root, int n1, int n2)
	{
        // if root is null return null
	    if(root==null) return null;

        // we will return node if any only if that node is the given node so that if 
        // will traversing left and right, the node for which its left and right is not null is the LCA
        if(root.data==n1 || root.data==n2) return root;
        Node left =  LCA(root.left, n1, n2);
        Node right = LCA(root.right, n1, n2);

        // if left and right both aren't null, then return root cause that's the LCA of the nodes given
        if(left!=null && right!=null) return root;
        // if either of the left or right traversal returns non null value, then return that node or just return null
        else if(left!=null) return left;
        else if(right!=null) return right;
        else return null;
    }
    
}
