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

class GfG
{
    public static Node changeKey(Node root, int old_key, int new_key)
    {
        return insertNode(deleteNode(root, old_key), new_key);
    }
    
    public static Node insertNode(Node root, int key) {
		// if root is nul, then returning new node so that it will be attached ot its parent 
        if(root==null) return new Node(key);
		// if key is smaller than current node, then new node will be assigned to left subtree
		// so reassign the left subtree by traversing left subtree
        if(root.data>key) root.left = insertNode(root.left, key);
		// else new node will be assigned to right subtree
		// so reassign the roght subtree by traversing right subtree
        else root.right = insertNode(root.right, key);
        return root;
    }
    
    public static Node deleteNode(Node root, int x) {
        if(root==null) return null;
		
		// traverse tree to find the target node
        if(root.data>x) {
            root.left = deleteNode(root.left, x);
        }
        else if(root.data<x) {
            root.right = deleteNode(root.right, x);
        }
        else {
			
			// incase the target node has both left and right child, then
			// attach the right child to the rightmost child of left child and return the left child
			if(root.left!=null && root.right!=null) {
				Node curr = root.left;
				while(curr!=null && curr.right!=null) {
					curr = curr.right;
				}
				curr.right = root.right;
				return root.left;
			} 
			// if target node has only one child, then just return that child
			if(root.left==null) return root.right;
			else return root.left;
		}
        return root;
    }
   
}