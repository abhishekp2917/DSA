import java.util.ArrayList;

class Node{
    int data;
    Node left,right;
    Node(int d){
        data=d;
        left=right=null;
    }
}

class Solution
{ 
    //Function to convert binary tree into circular doubly linked list.
    Node bTreeToClist(Node root)
    {
        ArrayList<Node> list = BToCDLL(root);
        // after traversing, connect the head of linkedlist with it's tail
        Node headNode = list.get(0), tailNode = list.get(1);
        headNode.left = tailNode;
        tailNode.right = headNode;
        return headNode;
    }
    
    ArrayList<Node> BToCDLL(Node root) {
        if(root==null) return null;
        // if it is a leaf node, then for this subtree, if we convert it into linkedlist, it's head and tail both will be same
        if(root.left==null && root.right==null)  {
            // return an list in which first element will be head of current linkedlist and second will be it's tail
            ArrayList<Node> list = new ArrayList<Node>();
            list.add(root);
            list.add(root);
            return list;
        }
        
        // traverse left and right subtree will give left linkedlist and right linkedlist
        ArrayList<Node> left = BToCDLL(root.left), right = BToCDLL(root.right);
        ArrayList<Node> result = new ArrayList<Node>();
        
        // if left linkedlist is not null, then connect root node at the tail of left linkedlist and add head of left linkedlist into result
        // else add root node at the first place of result
        if(left!=null) {
            Node leftHead = left.get(0);
            Node leftTail = left.get(1);
            root.left = leftTail;
            leftTail.right = root;
            result.add(leftHead);
        }
        else result.add(root);
        
        // if right linkedlist is not null, then connect it's head with the root node and add tail of right linkedlist into result
        // else add root node at second place of result
        if(right!=null) {
            Node rightHead = right.get(0);
            Node rightTail = right.get(1);
            root.right = rightHead;
            rightHead.left = root;
            result.add(rightTail);
        }
        else result.add(root);
        // return the list
        return result;
    }
    
}
    