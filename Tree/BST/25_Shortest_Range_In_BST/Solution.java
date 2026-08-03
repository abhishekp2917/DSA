import java.util.*;


class Node
{
    int data;
    Node left, right;
    Node(int d) {
        data = d; 
        left = right = null;
    }
}

class Range {
    int x, y;
    Range() {
        this.x = this.y = 0;
    }
    Range(int x, int y) {
        this.x = x;
        this.y = y;
    }
}


class Solution {
    public Range shortestRange(Node root) {
        ArrayList<Node> list = new ArrayList<>();
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.data));
        int minNodeValue = Integer.MAX_VALUE, maxNodeValue = Integer.MIN_VALUE, minRange = Integer.MAX_VALUE;
        Range ans = new Range(minNodeValue, maxNodeValue);
        Queue<Node> queue = new LinkedList<>();
        int qSize = 0;
        queue.add(root);

        // prepare list of linkedlist where each index will represent level of tree and each linkedlist 
        // will hold the node values of each level from left to right
        // this linkedlist will be sorted in ascending order
        while(!queue.isEmpty()) {
            qSize = queue.size();
            Node level = new Node(-1), ptr = level;
            Node tempNode = null;
            while(qSize>0) {
                tempNode = queue.remove();
                ptr.right = new Node(tempNode.data);
                ptr = ptr.right;
                if(tempNode.left!=null) queue.add(tempNode.left);
                if(tempNode.right!=null) queue.add(tempNode.right);
                qSize--;
            }
            list.add(level.right);
            // keep adding first node of each level in heap as this will be the smallest node of each level
            minHeap.add(level.right);
            // keep tracking of largest node of the heap since in min heap we can get min node value in nlog(n) but not the largest node
            maxNodeValue = Math.max(maxNodeValue, level.right.data);
        }

        // after building the heap, fetch the min value and make it as current min value of the list
        minNodeValue = minHeap.peek().data;
        // update the range if current range (min and max node value) is smaller than current min range
        int tempRange = maxNodeValue - minNodeValue;
        if(minRange>tempRange) {
            ans.x = minNodeValue;
            ans.y = maxNodeValue;
            minRange = tempRange;
        }

        // keep replacing min node value from the list (list of one node of each level)
        // after replacing the node, update the range value
        // keep doing this until min node value next pointer points to null  
        while(minHeap.peek().right!=null) {
            Node newNode = minHeap.poll().right;
            minHeap.add(newNode);
            minNodeValue = minHeap.peek().data;
            maxNodeValue = Math.max(maxNodeValue, newNode.data);
            tempRange = maxNodeValue - minNodeValue;
            if(minRange>tempRange) {
                ans.x = minNodeValue;
                ans.y = maxNodeValue;
                minRange = tempRange;
            }
        }

        return ans;
    }
}