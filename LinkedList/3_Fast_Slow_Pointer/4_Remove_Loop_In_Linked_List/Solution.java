// Structure of Node Class
class Node {
    int data;
    Node next;
    
    public Node(int data){
        this.data = data;
    }
}

class Solution {

    public static void removeLoop(Node head) {

        // Use Floyd's cycle detection algorithm.
        // slow moves one step, while fast moves two steps.
        // If a loop exists, the two pointers must eventually meet.
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            // A meeting point confirms that a cycle exists.
            if (slow == fast) {
                break;
            }
        }

        // If fast reached null, the list has no loop,
        // so there is nothing to remove.
        if (fast == null || fast.next == null) {
            return;
        }

        // Reset slow to the head.
        //
        // From this point, moving both pointers one step at a time
        // makes them meet at the first node of the cycle.
        slow = head;

        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        // fast is now at the first node of the cycle.
        //
        // To remove the cycle without removing any node,
        // find the last node inside the cycle.
        while (fast.next != slow) {
            fast = fast.next;
        }

        // Break the cycle by making the last cycle node
        // point to null.
        fast.next = null;
    }
}