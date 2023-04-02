
class Node {
    int data;
    Node left;
    Node right;
    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class Tree {
    // Function to find the minimum element in the given BST.
    int minValue(Node node) {
        
        // if node(head) is null, then just return -1
        if(node==null) return -1;

        // traverse left of every node as in BST left node is always smaller or equal to parent node
        else if(node.left!=null) {
            return minValue(node.left);
        }

        // if there is no left node then the current node is the smallest one so just return it
        else {
            return node.data;
        }
    }
}
