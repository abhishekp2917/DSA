class Node
{
    int data, height;
    Node left, right;
    Node(int d) {
        data = d; 
        height = 1;
        left = right = null;
    }
}

public class Solution {

    public Node insertToAVL(Node root, int value) {
        // if root is null then return a new node
        if(root==null) return new Node(value);
        // if root data is greater than the value then move left as new node is suppose
        // to be added on left subtree
        if(root.data>value) root.left = insertToAVL(root.left, value);
        // else move right
        else root.right = insertToAVL(root.right, value);

        // while backtracking reset the height of each node as new node has added which 
        // will change the height of its ancestors
        setHeight(root);

        // find balance of current root node
        int balance = getBalance(root);

        // if balance is positive and greater than 1 that means we have to rotate root in right direction
        // if new node value is greater than root's left node then new node is added to right of root's 
        // left else it's added to left of root's left  
        // left right rotation
        if(balance>1 && value>root.left.data) {
            root.left = leftRotate(root.left);
            root = rightRotate(root);
        }
        // right right rotation
        else if(balance>1 && value<root.left.data) {
            root = rightRotate(root);
        }
        // if balance is negative and smaller than 1 that means we have to rotate root in left direction
        // if new node value is greater than root's right node then new node is added to right of root's 
        // right else it's added to left of root's right  
        // right left rotation
        else if(balance<-1 && value<root.right.data) {
            root.right = rightRotate(root.right);
            root = leftRotate(root);
        }
        // left left rotation
        else if(balance<-1 && value>root.right.data) {
            root = leftRotate(root);
        }
        return root;
    }

    // rotating tree root in left direction
    private Node leftRotate(Node root) {
        Node right = root.right;
        Node rightLeft = root.right.left;
        right.left = root;
        root.right = rightLeft;
        // resetting the height of root node and its right child because on left rotation
        // only this 2 node children has changed. As children changes, height of the parent 
        // node could change as newly added children can have height diff from previous child
        setHeight(root);
        setHeight(right);
        // returning right node as it will be the new root node
        return right;
    }

    // rotating tree root in right direction
    private Node rightRotate(Node root) {
        Node left = root.left;
        Node leftRight = root.left.right;
        left.right = root;
        root.left = leftRight;
        // resetting the height of root node and its right child because on left rotation
        // only this 2 node children has changed.
        setHeight(root);
        setHeight(left);
        // returning left node as it will be the new root node
        return left;
    }

    // utility method to set the height of a node based on its left and right child height
    private void setHeight(Node root) {
        int leftHeight = 0, rightHeight = 0;
        if(root.left!=null) leftHeight = root.left.height;
        if(root.right!=null) rightHeight = root.right.height;
        root.height = Math.max(leftHeight, rightHeight) + 1;
    }

    // utility method to get balance of a node
    private int getBalance(Node root) {
        int leftHeight = 0, rightHeight = 0;
        if(root.left!=null) leftHeight = root.left.height;
        if(root.right!=null) rightHeight = root.right.height;
        return leftHeight - rightHeight;
    }
}
