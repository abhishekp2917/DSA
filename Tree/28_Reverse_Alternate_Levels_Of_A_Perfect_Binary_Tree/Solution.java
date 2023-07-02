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
	static void reverseAlternate(Node root)  
    {  
        // while traversing, start from second level because we want to swap nodes of two subtree
        traverse(root.left, root.right, 1);
    }  
    
    static void traverse(Node root1, Node root2, int lvl) {
        if(root1==null || root2==null) return;

        // if level is odd, then swap the node value of two roots 
        if(lvl%2!=0) {
            int temp = root1.data;
            root1.data = root2.data;
            root2.data = temp;
        }

        // if traversing left for first root then traverse right for second root and vice versa
        traverse(root1.left, root2.right, lvl+1);
        traverse(root1.right, root2.left, lvl+1);
    }
}