import java.util.LinkedList;
import java.util.Queue;

class Solution {
    
    public Node deletionBT(Node root, int key){
       
       Queue<Node> queue = new LinkedList<Node>();
       Node deepestRightNode = null, NodeToBeRemoved = null;
       queue.add(root);
       
       // traverse the tree and find the node that needs to be deleted and the deepest right node
       while(!queue.isEmpty()) {
           Node tempNode = queue.remove();
           // if we found the node to be deleted, take the ref of it
           if(tempNode.data==key) NodeToBeRemoved = tempNode;
           // on each iteration, take the curr node ref to a variable so that once we traverse whole tree, this var will have 
           // the deepest right node as it will be the last node in the iteration
           deepestRightNode = tempNode;
           if(tempNode.left!=null) queue.add(tempNode.left);
           if(tempNode.right!=null) queue.add(tempNode.right);
       }

       // once we have the node that needs to be removed and the deepest right node, assign deepest right node value to the 
       // node to be removed
       NodeToBeRemoved.data = deepestRightNode.data;

       // call the removeNode method which will remove the deepest right node
       removeNode(root, deepestRightNode);
       return root;
    }
    
    void removeNode(Node root, Node node) {
        if(root==null) return;
        // if the root left node or the right node is to be removed, make it null 
        if(root.left!=null && root.left==node) root.left = null;
        if(root.right!=null && root.right==node) root.right = null; 
        removeNode(root.left, node);
        removeNode(root.right, node);
    }
}
