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
    Node removekeys(Node root, int l, int r) {
        if(root==null) return null;
        
        // if current node data is smaller than lower bound, then return the value which will be returned from current node right subtree
        // because all the nodes of left subtree will be out of range
        // we will not return root here as we have to remove node which is out of range. Instead we are returning whatever is getting returned from right subtree which will get assigned to parent node child
        if(root.data<l) {
            return removekeys(root.right, l, r);
        }

        // if current node data is larger than uppper bound, then return the value which will be returned from current node left subtree
        // because all the nodes of right subtree will be out of range
        // we will not return root here as we have to remove node which is out of range. Instead we are returning whatever is getting returned from left subtree which will get assigned to parent node child
        if(root.data>r) {
            return removekeys(root.left, l, r);
        } 

        // if node is within the range, then reassign its left and right child node with the node which will be returned by traversing left and right subtree
        else {
            root.left = removekeys(root.left, l, r);
            root.right = removekeys(root.right, l, r);
        }

        // at last return root as it is within the range
        return root;
    }
}