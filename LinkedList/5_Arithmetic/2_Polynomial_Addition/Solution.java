// Structure of Node Class
class Node {
    int pow, coeff;
    Node next;

    public Node(int pow, int coeff) {
        this.pow = pow;
        this.coeff = coeff;
    }
}

class Solution {

    public static Node addPolynomial(Node p1, Node p2) {

        // Dummy node simplifies construction of the result polynomial
        // because every selected term can be attached using curr.next.
        Node head = new Node(-1, -1), curr = head;

        // Both polynomials are assumed to be sorted by power in descending order,
        // so we can merge them using the same idea as merging two sorted lists.
        while(p1!=null && p2!=null) {

            // p1 has the larger power, so its term appears next
            // in the resulting polynomial.
            if(p1.pow>p2.pow) {
                curr.next = p1;
                curr = curr.next;
                p1 = p1.next;

                // Detach the selected node from its old list so that
                // the result list is built cleanly.
                curr.next = null;
            }

            // p2 has the larger power, so its term appears next
            // in the resulting polynomial.
            else if(p2.pow>p1.pow) {
                curr.next = p2;
                curr = curr.next;
                p2 = p2.next;
                curr.next = null;
            }

            // Both terms have the same power, so they represent
            // like terms and their coefficients must be added.
            else {
                p1.coeff += p2.coeff;

                // Reuse p1 as the result node instead of creating
                // a new node for the combined term.
                curr.next = p1;
                curr = curr.next;
                p1 = p1.next;
                p2 = p2.next;
                curr.next = null;
            }
        }

        // Once one polynomial is exhausted, all remaining terms
        // of the other polynomial can be appended directly because
        // they are already sorted by decreasing power.
        if(p1!=null) curr.next = p1;
        else if(p2!=null) curr.next = p2;

        // head is a dummy node, so the actual polynomial starts at head.next.
        return head.next;
    }
}