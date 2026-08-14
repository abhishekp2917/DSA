// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data){
        this.data = data;
    }
}

class Solution
{
    public static Node zigZag(Node head){

        // Dummy node makes swapping the first two nodes easier,
        // because even the original head has a previous node.
        Node tempHead = new Node(-1);
        tempHead.next = head;

        Node prev = tempHead, curr = head, next = head.next;

        // The required relation alternates:
        // curr < next, then curr > next, then curr < next, and so on.
        boolean shouldBeLessThan = true, hasSwapped = false;

        while(next!=null) {

            // At this position, curr must be smaller than next.
            // Swap them if the required relation is violated.
            if(shouldBeLessThan) {
                if(curr.data>next.data) {
                    swap(prev, curr, next, next.next);
                    hasSwapped = true;
                }
            }
            else 
            {
                // At this position, curr must be greater than next.
                // Swap them if the required relation is violated.
                if(curr.data<next.data) {
                    swap(prev, curr, next, next.next);
                    hasSwapped = true;
                }
            }

            if(hasSwapped) {

                // After swapping, next becomes the node before curr,
                // so move prev to the newly placed node.
                prev = prev.next;

                // curr is now the second node of the pair.
                curr = next.next;

                // Continue with the next pair.
                next = curr.next;

                hasSwapped = false;
            }
            else {

                // The current pair already satisfies the required
                // relation, so simply move both pointers forward.
                prev = curr;
                curr = next;
                next = next.next;
            }

            // Alternate the required relation for the next pair.
            shouldBeLessThan = !shouldBeLessThan;
        }

        // Return the actual head, skipping the dummy node.
        return tempHead.next;
    }

    public static void swap(Node ptr1Prev, Node ptr1, Node ptr2, Node ptr2Next) {

        // Connect the previous node to ptr2 because ptr2
        // needs to move before ptr1.
        ptr1Prev.next = ptr2;

        // Reverse the order of the current pair.
        ptr2.next = ptr1;

        // Connect ptr1 to the remaining list.
        ptr1.next = ptr2Next;
    }
}