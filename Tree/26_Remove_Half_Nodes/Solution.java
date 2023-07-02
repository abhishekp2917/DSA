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
    // Approach 1
    // Return the root of the modified tree after removing all the half nodes.
    public static Node RemoveHalfNodes(Node root)
    {
        if(root==null) return  null;

        // if the root node is a leaf node, then return that root node
        if(root.left==null && root.right==null) return root;
        
        // traverse left and right subtree and assign whatever gets in return to the root's left and right child 
        root.left = RemoveHalfNodes(root.left);
        root.right = RemoveHalfNodes(root.right);
        
        // if either of root's child is null, then return root child which is not null
        // so that when assign the returned value to parent root's lef or right child, the 
        // root node will be removed from the normal flow
        if(root.left==null) return root.right;
        else if(root.right==null) return root.left;

        // if both the child are not null, then return root node
        else return root;
    }

    // Approach 2
    // Return the root of the modified tree after removing all the half nodes.
    public static Node RemoveHalfNodes2(Node root)
    {
        traverse(root);
        return root;
    }
    
    public static void traverse(Node root) {
        
        if(root==null || (root.left==null && root.right==null)) return;

        // if any of the child node is null, then to remove root node
        // replace root node data with the it's child which is not null. In this way root node will be eliminated
        // replace root left and right child with it's child's (which is not null) left and right node 
        if(root.left==null || root.right==null) {

            // temporarily storing child node which is not null
            Node temp;
            if(root.left!=null) temp = root.left;
            else temp = root.right;
            
            root.data = temp.data;
            root.left = temp.left;
            root.right = temp.right;

            // since root node has been updated, traverse that root node again
            traverse(root);
        }

        // if root node has both left and right child not null, then traverse left and right child
        else {
            traverse(root.left);
            traverse(root.right);
        }
    } 
}
