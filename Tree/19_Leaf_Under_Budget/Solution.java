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

class Solution{
    public int getCount(Node node, int bud)
    {
        Queue<Node> queue = new LinkedList<>(); 
        queue.add(node);
        // count to store the number of leaf nodes that can be possibly visited within the budget
        // lvl variable to keep track of the current level of the tree 
        int count = 0, lvl = 1;
        while(!queue.isEmpty()) {
            // we store the number of nodes in the current level in a variable and will travel only those 
            // nodes in current interation 
            int size = queue.size();
            while(size>0) {
                Node temp = queue.poll();
                // if current node is a leaf node, then check if you have budget to visit it
                // if you have, then deduct the cost of visiting it i.e. level of that node from the budget and increment the count
                // else break from the loop as you don't have enough budget left to visit other leafs as all the others will be 
                // either of same cost as current node or costlier
                if(temp.left==null && temp.right==null) {
                    if(bud>=lvl) {
                        bud -= lvl;
                        count++;
                    }
                    else break;
                }
                // add the current node left and right child in the queue if any on them exists
                if(temp.left!=null) queue.add(temp.left);
                if(temp.right!=null) queue.add(temp.right);
                size--;
            }
            lvl++;
        }
        return count;
    }
}