class Node {
    public int data;
    public Node left;
    public Node right;

    public Node(int val) {
        data = val;
        left = right = null;
    }
}


class Solution {
    
    private int successor = -1;
    
    public int inOrderSuccessor(Node root, Node k) {
        findInOrderSuccessor(root, k);
        return this.successor;
    }
    
    private void findInOrderSuccessor(Node root, Node k) {
        if(root==null) return;
        if(root.data>k.data) {
            this.successor = root.data;
            findInOrderSuccessor(root.left, k);
        }
        else findInOrderSuccessor(root.right, k);
    }
}



















