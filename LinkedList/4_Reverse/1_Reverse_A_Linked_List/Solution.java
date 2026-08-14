// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    Node reverseList(Node head) {

        // prev represents the already reversed portion of the list,
        // while curr represents the node currently being processed.
        Node prev = null, curr = head, next;

        while(curr!=null) {

            // Save the remaining list before changing curr.next,
            // otherwise we would lose access to the unreversed portion.
            next = curr.next;

            // Reverse the current node's pointer so that
            // it points to the previous node instead of the next node.
            curr.next = prev;

            // Move prev forward because curr is now part
            // of the reversed portion.
            prev = curr;

            // Move curr to the next unreversed node.
            curr = next;
        }

        // curr becomes null after processing every node,
        // so prev is the new head of the reversed list.
        return prev;
    }
}