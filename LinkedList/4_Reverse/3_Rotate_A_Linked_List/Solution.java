// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    public Node rotate(Node head, int k) {

        // firstHead will eventually point to the new head,
        // while tail points to the last node of the first part.
        Node secondHead = head, firstHead = head, tail = head;

        // Move both pointers k-1 positions.
        //
        // After this loop:
        // tail      -> k-th node
        // firstHead -> k-th node
        //
        // The node after firstHead will become
        // the new head after rotation.
        while(k>1) {
            tail = tail.next;
            firstHead = firstHead.next;
            k--;
        }

        // The (k+1)-th node becomes the new head.
        firstHead = firstHead.next;

        // If there is no (k+1)-th node,
        // k is equal to the list length,
        // so the list remains unchanged.
        if(firstHead==null) return head;

        // Break the list after the k-th node.
        //
        // This creates two separate lists:
        //
        // head -> ... -> k-th node
        //
        // firstHead -> ... -> old tail
        tail.next = null;

        Node curr = firstHead;

        // Find the tail of the second part
        // so that the original first part
        // can be attached after it.
        while(curr!=null && curr.next!=null) {
            curr = curr.next;
        }

        // Append the original first part
        // after the second part.
        curr.next = secondHead;

        // The old (k+1)-th node is now
        // the head of the rotated list.
        return firstHead;
    }
}