// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    static Node findIntersection(Node head1, Node head2) {

        // Dummy node simplifies construction of the result list
        // because we can always append using curr.next.
        Node ans = new Node(-1), curr = ans;

        // Both lists are sorted, so use two pointers to find
        // common elements without repeatedly scanning either list.
        while(head1!=null && head2!=null) {

            // Both values are equal, so this value belongs
            // to the intersection. Move both pointers forward
            // because this occurrence has been processed.
            if(head1.data==head2.data) {
                curr.next = new Node(head1.data);
                curr = curr.next;
                head1 = head1.next;
                head2 = head2.next;
            }

            // head2 contains the smaller value, so it cannot
            // match head1.data or any future smaller value.
            // Move head2 forward to search for a larger value.
            else if(head1.data>head2.data) {
                head2 = head2.next;
            }

            // head1 contains the smaller value, so it cannot
            // match head2.data or any future smaller value.
            // Move head1 forward to search for a larger value.
            else {
                head1 = head1.next;
            }
        }

        // ans is a dummy node, so the actual intersection
        // list starts from ans.next.
        return ans.next;
    }
}