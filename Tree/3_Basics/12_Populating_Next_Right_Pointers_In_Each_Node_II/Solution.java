class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}

class Solution1 {

    public Node connect(Node root) {
        Node curr = root;
        while(curr!=null) {
            Node dummy = new Node(0);
            Node tail = dummy;
            while(curr!=null) {
                if(curr.left!=null) {
                    tail.next = curr.left;
                    tail = tail.next;
                }
                if(curr.right!=null) {
                    tail.next = curr.right;
                    tail = tail.next;
                }
                curr = curr.next;
            }
            curr = dummy.next;
        }
        return root;
    }
}

// works only for perfect binary tree
class Solution2 {

    public Node connect(Node root) {
        if(root==null) return null;
        if(root.left==null && root.right==null) return root;
        root.left.next = root.right;
        if(root.next!=null) {
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
        return root;
    }
}