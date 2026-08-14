// Structure of Node Class
class Node {
    int data;
    Node next;

    Node(int d) {
        data = d;
        next = null;
    }
}

class Solution {

    public static boolean detectLoop(Node head) {

        // slow moves one step at a time, while fast moves two steps.
        // If a cycle exists, fast will eventually catch up with slow
        // because both pointers keep moving inside the same cycle.
        Node slow = head, fast = head;

        // Continue while fast can safely move two steps.
        // If fast reaches null, the list has no cycle.
        while(fast!=null && fast.next!=null) {

            slow = slow.next;
            fast = fast.next.next;

            // If both pointers meet, they must be inside a cycle.
            if(slow==fast) return true;
        }

        // fast reached the end of the list, so no cycle exists.
        return false;
    }
}