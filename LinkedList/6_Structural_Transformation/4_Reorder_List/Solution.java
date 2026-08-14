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
    Node reorderlist(Node head) {

        // Split the list into two halves.
        // The first half keeps the nodes in their original order,
        // while the second half will be reversed before merging.
        Node midNode = findMiddleNode(head);
        Node firstPartHead = head, secPartHead = midNode.next;

        // Break the connection between the two halves so that
        // they become two independent linked lists.
        midNode.next = null;

        // Reverse the second half because the required order is:
        // first node, last node, second node, second-last node, ...
        secPartHead = reverse(secPartHead);

        Node ptr1 = firstPartHead, ptr2 = secPartHead;

        // Merge the two halves alternately.
        //
        // ptr1 contributes the next node from the beginning,
        // while ptr2 contributes the next node from the reversed end.
        while(ptr2!=null) {

            // Save both next nodes before changing any pointers,
            // otherwise the remaining portions of the lists would be lost.
            Node ptr1Next = ptr1.next, ptr2Next = ptr2.next;

            // Insert ptr2 immediately after ptr1.
            ptr1.next = ptr2;
            ptr2.next = ptr1Next;

            // Move both pointers to their next unprocessed nodes.
            ptr1 = ptr1Next;
            ptr2 = ptr2Next;
        }

        // The original first node remains the head of the reordered list.
        return firstPartHead;
    }

    public static Node reverse(Node head) {

        // Reverse the linked list in-place.
        Node prev = null, curr = head, next;

        while(curr!=null) {

            // Save the remaining list before changing curr.next.
            next = curr.next;

            // Reverse the current node's pointer.
            curr.next = prev;

            // Move prev and curr forward.
            prev = curr;
            curr = next;
        }

        // prev is the new head of the reversed list.
        return prev;
    }

    public static Node findMiddleNode(Node head) {

        // slow moves one step while fast moves two steps.
        // When fast reaches the end, slow points to the last
        // node of the first half.
        Node slow = head, fast = head.next;

        while(fast!=null && fast.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}