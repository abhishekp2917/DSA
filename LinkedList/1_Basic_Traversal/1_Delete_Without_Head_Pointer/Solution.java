// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    void deleteNode(Node del) {

        // Since we do not have access to the head pointer,
        // we cannot move backward or update the previous node's next pointer.
        //
        // The only possible way to "delete" this node is to
        // copy the data from the next node into the current node,
        // and then bypass the next node.

        // Copy data of next node into current node
        del.data = del.next.data;

        // Skip the next node by linking current node
        // directly to the node after next
        del.next = del.next.next;
    }
}
