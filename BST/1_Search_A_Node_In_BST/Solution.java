// Structure of Node Class
class Node
{
	int data;
	Node left, right;
	Node(int d)
	{
		data = d;
	}
}


class BST {
    // Function to search a node in BST.
    boolean search(Node root, int x) {
		// if root is nul then return false 
        if(root==null) return false;

		// if that node is present, then return true
        if(root.data==x) return true;

		// since in BST all the nodes on the left are smaller and on the right are larger than the current node
		// so if current node is larger than target node then move left else move right this way operation can be done in O(log(N))
        if(root.data>x) return search(root.left, x);
        else return search(root.right, x);
    }
}
