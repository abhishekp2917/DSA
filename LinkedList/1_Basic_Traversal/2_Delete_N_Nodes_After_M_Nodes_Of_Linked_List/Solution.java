// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    static void linkdelete(Node head, int n, int m) {

        // Nothing to delete if the list is empty.
        if(head==null) return;

        Node curr = head;

        // counter1 tracks how many nodes we need to KEEP
        // in the current block.
        int counter1 = m;

        // counter2 tracks how many nodes we need to DELETE
        // after the current kept block.
        int counter2 = n;

        // Move curr to the m-th node.
        //
        // We stop at the m-th node because
        // curr.next is the first node that should be deleted.
        while(curr!=null && curr.next!=null && counter1>1) {
            curr = curr.next;
            counter1--;
        }

        // Delete the next n nodes by repeatedly
        // skipping curr.next.
        //
        // curr itself is the last node we want to keep,
        // so we modify its next pointer.
        while(curr!=null && curr.next!=null && counter2>0) {
            curr.next = curr.next.next;
            counter2--;
        }

        // curr.next is now the first node of the next block.
        //
        // Recursively repeat the same process:
        // keep m nodes, then delete n nodes.
        linkdelete(curr.next, n, m);
    }
}