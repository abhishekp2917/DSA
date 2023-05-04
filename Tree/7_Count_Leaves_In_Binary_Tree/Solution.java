
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


class Tree
{
    int countLeaves(Node node) 
    {
        // if head is null then there is no leaves in that tree, so return 0
        if(node==null) return 0;

        // if left and right both nodes are null, then the current node is leave node so return 1
        if(node.left==null && node.right==null) return 1;
        
        // traverse both side of tree and return the sum of the count of leaves on both side
        return countLeaves(node.left) + countLeaves(node.right);
    }
}
