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
    static Node mergeSort(Node head)
    {
        // A list with one node is already sorted, so recursion can stop here.
        if(head.next==null) return head;

        // Find the last node of the first half so that the list can be split
        // into two smaller independent lists.
        Node mid = middleNode(head);
        Node firstPart = head, secPart = mid.next;

        // Break the connection between the two halves.
        mid.next = null;

        // Recursively sort both halves before merging them.
        firstPart = mergeSort(firstPart);
        secPart = mergeSort(secPart);

        // Merge the two sorted halves using their existing nodes.
        // A dummy node makes appending the first node straightforward.
        Node tempHead = new Node(-1), curr = tempHead, ptr1 = firstPart, ptr2 = secPart;

        while(ptr1!=null && ptr2!=null) {

            // Always append the smaller current node so that the merged
            // list remains sorted.
            if(ptr1.data<ptr2.data) {
                curr.next = ptr1;
                curr = curr.next;
                ptr1 = ptr1.next;

                // Detach the selected node from its old list because
                // its next pointer will now be controlled by the merged list.
                curr.next = null;
            }
            else {
                curr.next = ptr2;
                curr = curr.next;
                ptr2 = ptr2.next;
                curr.next = null;
            }
        }

        // One list may still contain nodes after the other becomes empty.
        // Those remaining nodes are already sorted, so attach them directly.
        if(ptr1!=null) curr.next = ptr1;
        else if(ptr2!=null) curr.next = ptr2;

        // Skip the dummy node and return the actual merged head.
        return tempHead.next;
    }

    public static Node middleNode(Node head) {

        // fast starts one node ahead so that slow ends at the last node
        // of the first half, which makes splitting easy.
        Node slow = head, fast = head.next;

        // slow moves one step while fast moves two steps.
        while(fast!=null && fast.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}