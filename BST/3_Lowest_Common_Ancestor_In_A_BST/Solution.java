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
        if(root==null) return null;

        // traverse left when root data is greater than both the target value
        if(root.data>n1 && root.data>n2) return LCA(root.left, n1, n2);

        // traverse right when root data is smaller than both the target value
        if(root.data<n1 && root.data<n2) return LCA(root.right, n1, n2);

        // first node for which above condition fails is the LCA 
        return root;
    }
    
}
