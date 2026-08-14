import java.util.Comparator;
import java.util.PriorityQueue;

// Structure of Node Class
class Node {
    int data;
    Node next, bottom;

    public Node(int data){
        this.data = data;
    }
}

class Solution
{
    Node flatten(Node root)
    {
        // The first node of every vertical list is a candidate for
        // the smallest remaining value, so keep all of them in a min-heap.
        PriorityQueue<Node> pq = new PriorityQueue<Node>(getLength(root), new Comparator<Node>(){
            @Override
            public int compare(Node o1, Node o2){
                if (o1.data<o2.data)
                    return -1;
                else if (o1.data==o2.data)
                    return 0;
                else 
                    return 1;
            }
        });

        Node curr = root;

        // Add the head of every vertical list to the heap.
        // We only need the head initially because once it is removed,
        // its bottom node becomes the next candidate from that list.
        while(curr!=null) {
            pq.add(curr);
            curr = curr.next;
        }

        // Use a dummy node to simplify construction of the flattened list.
        Node tempHead = new Node(-1), ptr = tempHead;

        while(pq.size()!=0) {

            // The smallest heap node is the smallest value among
            // all currently available nodes, so it comes next.
            Node smallestNode = pq.poll();

            // Append the selected node using the bottom pointer,
            // because the flattened list must use bottom links.
            ptr.bottom = smallestNode;
            ptr = ptr.bottom;

            // The next candidate from the same vertical list is
            // the selected node's bottom node.
            smallestNode = smallestNode.bottom;

            // Remove the old bottom connection from the node
            // already placed in the flattened list.
            ptr.bottom = null;

            // Add the next node from the same vertical list to the heap.
            // This maintains one active candidate per vertical list.
            if(smallestNode!=null) pq.add(smallestNode);
        }

        // tempHead is only a dummy node, so the flattened list starts at bottom.
        return tempHead.bottom;
    }

    public static int getLength(Node head) {

        // Count the number of vertical lists so that the priority queue
        // can be initialized with a suitable capacity.
        int count = 0;

        while(head!=null) {
            count++;
            head = head.next;
        }

        return count;
    }
}