// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    public static Node reverse(Node head, int k) {

        // Reverse the current group of at most k nodes.
        // prev becomes the new head of this reversed group,
        // while curr points to the first node of the remaining list.
        Node prev = null, curr = head, next = null;
        int counter = 0;

        while(counter<k && curr!=null) {

            // Save the next node before changing curr.next,
            // otherwise the remaining list would be lost.
            next = curr.next;

            // Reverse the current node's pointer so that
            // it points to the previous node in this group.
            curr.next = prev;

            // Move prev forward because curr is now
            // part of the reversed group.
            prev = curr;

            // Move curr to the next unreversed node.
            curr = next;
            counter++;
        }

        // head is now the last node of the reversed group.
        //
        // Connect it to the recursively reversed remaining groups.
        // next is the first node of that remaining portion.
        if(next!=null) head.next = reverse(next, k);

        // prev is the new head of the current reversed group.
        return prev;
    }
}