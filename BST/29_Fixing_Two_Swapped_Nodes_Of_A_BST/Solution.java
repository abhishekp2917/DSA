class Node
{
    int data;
    Node left, right;
    Node(int d) {
        data = d; 
        left = right = null;
    }
}

class Solution {
    
    Node prevNode = null, tempNode1 = null, tempNode2 = null;
    
    public Node correctBST(Node root) {
        
        // finding the two node that got swapped
        traverse(root);

        // once we find those 2 nodes, just swap their node values 
        int temp = tempNode1.data;
        tempNode1.data = tempNode2.data;
        tempNode2.data = temp;

        // return root 
        return root;
    }
    
    public void traverse(Node root) {
        if(root==null) return;

        // traverse tree in inorder fashion so that we will encounter nodes in ascending order
        // here we will keep track of previous node so as to compare current node value with it
        traverse(root.left);
        if(prevNode!=null) {

            // if prev node data is greater than current node the prev node is one of the swapped nodes
            // so if we haven't already found the first swapped node, then prev node will be first swapped node
            if(prevNode.data>root.data && tempNode1==null) tempNode1 = prevNode;
            
            // to find the second swapped node, keep assigning current node to second node until the first swapped node not gets smaller than current node
            if(tempNode1!=null && tempNode1.data>root.data) tempNode2 = root;
        }

        // keep updating prev node with current one
        prevNode = root;
        traverse(root.right);
    }
}