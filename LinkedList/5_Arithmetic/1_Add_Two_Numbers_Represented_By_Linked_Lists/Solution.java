// Structure of Node Class
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}

class Solution {

    static Node addTwoLists(Node first, Node second) {

        // Reverse both lists so that the least significant digits
        // come first, allowing us to perform addition from right to left.
        first = reverse(first);
        second = reverse(second);

        // Use a dummy node to simplify construction of the result list.
        // ptr1 and ptr2 traverse the two numbers, while ptr3 builds the result.
        Node ans = new Node(-1), ptr1 = first, ptr2 = second, ptr3 = ans;
        int carry = 0;

        // Continue while either number has digits remaining
        // or a carry still needs to be added.
        while(ptr1!=null || ptr2!=null || carry!=0) {

            int num1, num2, tempSum;

            // Take the current digit from the first number.
            // If the first number has ended, treat the missing digit as 0.
            if(ptr1!=null) {
                num1 = ptr1.data;
                ptr1 = ptr1.next;
            }
            else num1 = 0;

            // Take the current digit from the second number.
            // If the second number has ended, treat the missing digit as 0.
            if(ptr2!=null) {
                num2 = ptr2.data;
                ptr2 = ptr2.next;
            }
            else num2 = 0;

            // Add both digits along with the carry from the previous position.
            tempSum = num1 + num2 + carry;

            // The current result digit is the last digit of tempSum.
            ptr3.next = new Node(tempSum%10);
            ptr3 = ptr3.next;

            // The remaining part becomes the carry for the next digit.
            carry = tempSum/10;
        }

        // Digits were generated from least significant to most significant,
        // so reverse the result to restore the normal number representation.
        return reverse(ans.next);
    }

    public static Node reverse(Node head) {

        // Reverse the linked list in-place using three pointers.
        Node prev = null, curr = head, next = null;

        while(curr!=null) {

            // Save the remaining list before changing curr.next.
            next = curr.next;

            // Reverse the current node's pointer.
            curr.next = prev;

            // Move prev and curr forward.
            prev = curr;
            curr = next;
        }

        // prev becomes the new head of the reversed list.
        return prev;
    }
}