import java.util.ArrayList;
import java.util.Collections;

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
    // variable 'idx' to keep track of elements in sorted list
    int idx = 0;

    Node binaryTreeToBST(Node root)
    {
        // declare an arrayList and store all the node values in it
        ArrayList<Integer> list = new ArrayList<Integer>();
        loadTreeNodes(root, list);

        // sort the arraylist as in BST, inorder traversal is in sorted order
        Collections.sort(list);

        // to convert given binary tree to bst, update tree node value from the sorted list
        // by travering the tree in inorder fashion
        makeBST(root, list);
        return root;
    }
    
    void loadTreeNodes(Node root, ArrayList<Integer> list) {
        if(root==null) return;
        list.add(root.data);
        loadTreeNodes(root.left, list);
        loadTreeNodes(root.right, list);
    }
    
    void makeBST(Node root, ArrayList<Integer> list) {
        if(root==null) return;
        makeBST(root.left, list);

        // update node value by travering tree in inorder fashion
        // after updating, increment the 'idx' by 1
        root.data = list.get(idx);
        idx++;
        makeBST(root.right, list);
    }
}