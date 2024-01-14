import java.util.LinkedList;
import java.util.Queue;

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

class Solution1 {

    public boolean isPerfect(Node root){
        
        Queue<Node> queue = new LinkedList<Node>();
        // currExpectedSize is the exepected number of nodes at particular level
        int currExpectedSize = 1;
        queue.add(root);
         
        while(!queue.isEmpty()) {
            int currLvlSize = queue.size();

            // if number of nodes at current level is not equal to expected count, then return false
            if(currLvlSize!=currExpectedSize) return false;
            // else add child nodes of each node of current level in the queue
            while(currLvlSize!=0) {
                Node tempNode = queue.remove();
                if(tempNode.left!=null) queue.add(tempNode.left);
                if(tempNode.right!=null) queue.add(tempNode.right);
                currLvlSize--;
            }
            // double the expectedSize
            currExpectedSize *= 2;
        }
        // at last return true
        return true;
    }
}


class Solution2 {

    // global var to store the depth of first leaf node so as to compare it with depth of other leafs
    int leafLvl = -1;

    public boolean isPerfect(Node root){
        return checkIfPerfect(root, 1);
    }
    
    public boolean checkIfPerfect(Node root, int lvl) {
        if(root==null) return true;
        // if current node has only one child then don't traverse further, just return false
        if((root.left==null && root.right!=null) || (root.left!=null && root.right==null)) return false;
        // if current node is a leaf node 
        if(root.left==null && root.right==null) {
            // if global var is not initialized, then the current node is the first leaf node
            // so assign the depth to this var
            if(leafLvl==-1) {
                leafLvl = lvl;
                return true;
            }
            // else compare the depth of current leaf node with global variable 
            // if it matches return true else false 
            // we are comparing this because all the leaf nodes should be at same level in perfect binary tree
            else return leafLvl==lvl;
        }
        // return AND logic of the returned value
        return checkIfPerfect(root.left, lvl+1) && checkIfPerfect(root.right, lvl+1);
    }
}