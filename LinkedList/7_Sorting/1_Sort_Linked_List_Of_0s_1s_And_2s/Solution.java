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
    static Node segregate(Node head)
    {
        // Maintain three separate lists for 0s, 1s and 2s.
        // Dummy heads make appending nodes to each list simple.
        Node zeroesHead = new Node(-1), zero = zeroesHead;
        Node onesHead = new Node(-1), one = onesHead;
        Node twosHead = new Node(-1), two = twosHead;
        Node curr = head;

        // Traverse the original list once and place each node
        // into the list corresponding to its value.
        while(curr!=null) {

            if(curr.data==0) {

                // Append the current node to the 0s list.
                zero.next = curr;
                curr = curr.next;
                zero = zero.next;

                // Detach the node from the original list so that
                // it does not retain an unwanted connection.
                zero.next = null;
            }

            else if(curr.data==1) {

                // Append the current node to the 1s list.
                one.next = curr;
                curr = curr.next;
                one = one.next;
                one.next = null;
            }

            else {

                // Append the current node to the 2s list.
                two.next = curr;
                curr = curr.next;
                two = two.next;
                two.next = null;
            }
        }

        // Connect the three lists in sorted order: 0s -> 1s -> 2s.
        //
        // If the 1s list exists, connect 0s to 1s and 1s to 2s.
        if(onesHead.next!=null) {
            zero.next = onesHead.next;
            one.next = twosHead.next;
        }

        // If there are no 1s, connect the 0s list directly to 2s.
        else {
           zero.next = twosHead.next;
        }

        // Return the actual head, skipping the dummy 0s node.
        return zeroesHead.next;
    }
}