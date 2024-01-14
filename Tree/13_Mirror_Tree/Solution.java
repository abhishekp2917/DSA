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

class Solution {
    // Function to convert a binary tree into its mirror tree.
    void mirror(Node node) {
        // if node is not null, then perform the operation on left and right child
        if(node!=null) {
            // swap the left child with the right child
            Node tempNode = node.left;
            node.left = node.right;
            node.right = tempNode;
            // traverse to the left subtree
            mirror(node.left);
            // traverse to the right subtree
            mirror(node.right);
        }
    }
}