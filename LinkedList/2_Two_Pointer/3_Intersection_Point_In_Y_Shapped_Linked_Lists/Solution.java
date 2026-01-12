// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    public Node intersectPoint(Node head1, Node head2) {

        // ptr1 starts from head of first list
        // ptr2 starts from head of second list
        Node ptr1 = head1, ptr2 = head2;

        // Traverse both lists simultaneously
        // The loop continues until either:
        // - both pointers meet (intersection found)
        // - both pointers become null (no intersection)
        while (ptr1 != null && ptr2 != null) {

            // If both pointers refer to the same node,
            // we have found the intersection point
            if (ptr1 == ptr2) break;

            // If ptr1 reaches the end of list1,
            // redirect it to the head of list2
            // This equalizes the path length
            if (ptr1.next == null) ptr1 = head2;

            // If ptr2 reaches the end of list2,
            // redirect it to the head of list1
            if (ptr2.next == null) ptr2 = head1;

            // Move both pointers one step forward
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        // Either returns:
        // - the intersection node
        // - or null if no intersection exists
        return ptr1;
    }
}
