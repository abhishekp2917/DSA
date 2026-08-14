import java.util.PriorityQueue;

// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data){
        this.data = data;
    }
}


class Solution
{
    Node mergeKLists(Node[] arr)
    {
        int K = arr.length;

        // Store both the node and the list it belongs to.
        // We need the list index because multiple lists can contain
        // nodes with the same value, so the value alone cannot identify a node.
        PriorityQueue<Node[]> pq = new PriorityQueue<>(
            (node1, node2) -> Integer.compare(node1[0].data, node2[0].data)
        );

        // Dummy node simplifies construction of the merged list.
        Node mergedHead = new Node(-1), curr = mergedHead;

        // Add the first node from every list because each of these
        // is the smallest currently available node from its list.
        for(int i=0; i<K; i++) {
            if(arr[i]!=null) {
                pq.add(new Node[] { arr[i], new Node(i) });
            }
        }

        while(!pq.isEmpty()) {

            // The heap gives the smallest currently available node
            // among all K lists.
            Node[] entry = pq.poll();
            Node smallestNode = entry[0];
            int listIdx = entry[1].data;

            // Append the smallest node to the merged list.
            curr.next = smallestNode;
            curr = curr.next;

            // The next node from the same list becomes its new candidate.
            smallestNode = smallestNode.next;

            if(smallestNode!=null) {
                pq.add(new Node[] { smallestNode, new Node(listIdx) });
            }

            // Detach the selected node from its old list so that
            // the merged list does not retain an unwanted connection.
            curr.next = null;
        }

        // Skip the dummy node and return the actual merged list.
        return mergedHead.next;
    }
}