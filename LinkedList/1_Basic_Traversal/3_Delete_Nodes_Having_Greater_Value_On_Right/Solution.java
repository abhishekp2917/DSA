// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    Node compute(Node head) {

        // Reverse the list so that every node's right-side
        // elements become the elements that we have already processed.
        //
        // This allows us to determine whether a node has a greater
        // element on its right by simply looking at the next node.
        head = reverse(head);

        Node curr = head;

        while(curr.next!=null) {

            // If the current node is greater than the next node,
            // then curr.next has a greater element on its right
            // (curr itself), so curr.next must be removed.
            if(curr.data>curr.next.data) {
                curr.next = curr.next.next;
            }

            // Otherwise, curr.next is at least as large as curr,
            // so it can potentially be the maximum seen so far.
            else {
                curr = curr.next;
            }
        }

        // Reverse again to restore the original left-to-right order.
        head = reverse(head);

        return head;
    }

    public static Node reverse(Node head) {

        Node prev = null, curr = head, next;

        // Reverse each pointer one at a time.
        while(curr!=null) {

            // Save the remaining list before changing curr.next.
            next = curr.next;

            // Point the current node backwards.
            curr.next = prev;

            // Move prev and curr one step forward.
            prev = curr;
            curr = next;
        }

        // prev becomes the new head of the reversed list.
        return prev;
    }
}