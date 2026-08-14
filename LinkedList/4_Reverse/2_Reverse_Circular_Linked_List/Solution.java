// Structure of Node Class
class Node {
    int data;
    Node next, prev;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    public static Node reverseCLL(Node head) {

        // In a circular list there is no null at the end,
        // so we process nodes until we come back to the head.
        Node curr = head;

        do {

            // Save the original next node because
            // curr.next will be changed during reversal.
            Node next = curr.next;

            // Swap next and prev so that both directions
            // of the doubly linked list are reversed.
            curr.next = curr.prev;
            curr.prev = next;

            // Move to the next node in the original list.
            curr = next;

        } while(curr != head);

        // The original tail becomes the new head
        // after reversing the circular list.
        return head.prev;
    }
}