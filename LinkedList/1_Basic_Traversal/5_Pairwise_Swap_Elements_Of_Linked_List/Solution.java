// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    public Node pairwiseSwap(Node head) {

        // If the list has only one node, no swapping is needed
        if (head.next == null) return head;

        // prev  -> node before the current pair
        // curr  -> first node of the current pair
        // next  -> second node of the current pair
        // nxtNext -> node after the current pair
        Node prev = head;
        Node curr = head;
        Node next = head.next;
        Node nxtNext = head.next.next;

        // After first swap, the second node becomes the new head
        Node ans = head.next;

        // Process pairs until we reach the end
        while (next != null) {

            // Step 1: link previous part to second node of the pair
            prev.next = next;

            // Step 2: reverse the current pair
            next.next = curr;

            // Step 3: temporarily break link to avoid cycles
            curr.next = null;

            // Move prev to the end of the swapped pair
            prev = curr;

            // Move curr to the start of the next pair
            curr = nxtNext;

            // If no more nodes remain, stop
            if (curr == null) break;

            // Set next as the second node of the new pair
            next = curr.next;

            // If there is only one node left, attach it as-is
            if (next == null) {
                prev.next = curr;
            } 
            else {
                // Store the node after the next pair
                nxtNext = next.next;
            }
        }

        // Return the new head after swapping
        return ans;
    }
}
