// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    void splitList(Node head) {

        // Use slow and fast pointers to find the midpoint.
        //
        // slow moves one step at a time while fast moves two steps.
        // Because the list is circular, fast eventually reaches head
        // or the node just before head.
        Node slow = head, fast = head.next;

        while(fast!=head && fast.next!=head) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // head remains the first node of the first half.
        Node head1 = head;

        // slow is the last node of the first half,
        // so slow.next becomes the head of the second half.
        Node head2 = slow.next;

        // Close the first half into a circular linked list.
        slow.next = head1;

        // Find the last node of the second half.
        // Its next pointer currently points back to the original head.
        Node curr = head2;

        while(curr.next!=head) {
            curr = curr.next;
        }

        // Close the second half into its own circular linked list.
        curr.next = head2;
    }
}