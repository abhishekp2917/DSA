// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    public Node swapKth(Node head, int k) {

        // slow will eventually point to the kth node from the end
        // fast is used to reach the end of the list
        // kthNode will point to the kth node from the beginning
        Node slow = head, fast = head, kthNode = head;

        // Move kthNode and fast k-1 steps forward
        // After this loop:
        // - kthNode points to kth node from the beginning
        // - fast is k-1 nodes ahead
        while (fast != null && k > 1) {
            kthNode = kthNode.next;
            fast = fast.next;
            k--;
        }

        // If fast becomes null, it means k is larger than list length
        // In that case, no swap is possible
        if (fast == null) return head;

        // Move fast to the end of the list
        // At the same time, move slow forward
        // When fast reaches the last node,
        // slow will be at the kth node from the end
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // Swap the data values of the kth node from beginning
        // and kth node from end
        swapNode(kthNode, slow);

        return head;
    }

    // Helper method to swap data between two nodes
    private void swapNode(Node node1, Node node2) {
        int temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }
}
