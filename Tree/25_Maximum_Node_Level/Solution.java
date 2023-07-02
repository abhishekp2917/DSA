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

class Tree
{
    // Return the level (0-indexed) with maximum number of nodes.
    public static int maxNodeLevel(Node root)
    {
        int maxNodeCount = 0, maxLvl = 0, lvlCounter = 0;
        
        // using queue to travel i level order fashion
        Queue<Node> queue = new LinkedList<Node>(); 
        queue.add(root);
        
        while(!queue.isEmpty()) {

            // storing current queue size which is equal to current level width
            int tempSize = queue.size();

            // if current level has more number of nodes then current max value, then update the current max value and max level
            if(tempSize>maxNodeCount) {
                maxNodeCount = tempSize;
                maxLvl = lvlCounter;
            }

            // pop out the current level nodes from the queue and their child nodes back into the queue
            // use tempSize to track the number of nodes of current level in the queue 
            // it's required because we are adding child nodes in the same queue
            while(tempSize!=0){
                Node tempNode = queue.remove();
                if(tempNode.left!=null) queue.add(tempNode.left);
                if(tempNode.right!=null) queue.add(tempNode.right);
                tempSize--;
            }
        
            // after each level traversal, increament the level counter by 1 
            lvlCounter++;
        }
        return maxLvl;
    }
}