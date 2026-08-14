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
    public static Node insertionSort(Node head)
    {
        // Dummy node acts as a fixed node before the sorted portion,
        // making insertion at the beginning of the list straightforward.
        Node tempHead = new Node(-1);
        tempHead.next = head;

        // prev is the node before curr in the original list.
        // curr is the node currently being inserted into the sorted portion.
        Node ptr, prev = head, curr = head.next;

        while(curr!=null) {

            // Start from the beginning of the sorted portion because
            // curr may need to be inserted anywhere before its current position.
            ptr = tempHead;

            // Find the first node whose value is >= curr.data.
            //
            // ptr.next is kept before curr because nodes from head through prev
            // are already sorted.
            while(ptr.next!=curr && curr.data>ptr.next.data) {
                ptr = ptr.next;
            }

            // If ptr.next is not curr, curr is smaller than some earlier node,
            // so it must be removed from its current position and inserted before ptr.next.
            if(ptr.next!=curr) {

                // Remove curr from its current position.
                prev.next = curr.next;

                // Insert curr before ptr.next in the sorted portion.
                curr.next = ptr.next;
                ptr.next = curr;

                // prev remains the node before the next unsorted node,
                // because curr was removed from its original position.
                curr = prev.next;
            }

            // curr is already in the correct position, so simply move
            // both pointers forward through the unsorted portion.
            else {
                prev = curr;
                curr = curr.next;
            }
        }

        // tempHead is a dummy node, so return the actual sorted head.
        return tempHead.next;
    }
}