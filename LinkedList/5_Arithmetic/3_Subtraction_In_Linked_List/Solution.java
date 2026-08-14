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
    static Node subLinkedList(Node l1, Node l2)
    {
        // Remove leading zeroes first so that the length comparison
        // correctly reflects the number of digits in each number.
        l1 = removeLeadingZeroes(l1);
        l2 = removeLeadingZeroes(l2);

        // compare() returns the smaller number at index 0
        // and the larger number at index 1.
        //
        // We always subtract smaller from larger so that
        // the final result is non-negative.
        Node heads[] = compare(l1, l2);

        // Reverse both numbers so that subtraction can start
        // from the least significant digit, just like normal
        // manual subtraction.
        heads[0] = reverse(heads[0]);
        heads[1] = reverse(heads[1]);

        Node smallPtr = heads[0], largePtr = heads[1];

        // Subtract corresponding digits.
        //
        // Borrow is handled separately afterward because
        // at this stage we only need to calculate the raw
        // digit differences.
        while(smallPtr!=null) {
            largePtr.data -= smallPtr.data;
            smallPtr = smallPtr.next;
            largePtr = largePtr.next;
        }

        // Propagate borrows from right to left.
        //
        // The list is currently reversed, so curr represents
        // the current digit and next represents the next more
        // significant digit.
        Node curr = heads[1], next = heads[1].next;

        while(next!=null) {

            // A negative digit means the current subtraction
            // required borrowing 1 from the next digit.
            if(curr.data<0) {
                curr.data += 10;
                next.data -= 1;
            }

            curr = next;
            next = next.next;
        }

        // Restore the normal most-significant-digit-first order.
        heads[1] = reverse(heads[1]);

        // Subtraction may produce leading zeroes, for example:
        // 100 - 99 = 001 -> 1.
        heads[1] = removeLeadingZeroes(heads[1]);

        // If every digit became zero, return a single zero node.
        if(heads[1]==null) return new Node(0);
        else return heads[1];
    }

    public static Node removeLeadingZeroes(Node head) {

        // Skip zero-valued nodes from the beginning because
        // they do not contribute to the numerical value.
        while(head!=null && head.data==0) {
            head = head.next;
        }

        return head;
    }

    public static Node reverse(Node head) {

        // Reverse the list so that the least significant digit
        // becomes the first node, allowing right-to-left subtraction.
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

        return prev;
    }

    public static int getLength(Node head) {

        // Count digits so numbers with different digit counts
        // can be compared immediately.
        int count = 0;

        while(head!=null) {
            count++;
            head = head.next;
        }

        return count;
    }

    public static Node[] compare(Node head1, Node head2) {

        // First compare the number of digits.
        //
        // A number with more digits is necessarily larger
        // because leading zeroes have already been removed.
        int len1 = getLength(head1), len2 = getLength(head2);
        Node[] ans = new Node[2];

        if(len1>len2) {
            // head1 is larger, so return:
            // ans[0] = smaller, ans[1] = larger.
            ans[0] = head2;
            ans[1] = head1;
        }
        else if(len2>len1) {
            // head2 is larger.
            ans[0] = head1;
            ans[1] = head2;
        }
        else {

            // If both numbers have the same number of digits,
            // compare digits from left to right.
            //
            // The first different digit determines which number
            // is larger because both numbers have equal length.
            Node ptr1 = head1, ptr2 = head2;
            boolean isBothEqual = true;

            while(ptr1!=null && ptr2!=null) {

                if(ptr1.data>ptr2.data) {
                    // head1 is larger, so put head2 first.
                    ans[0] = head2;
                    ans[1] = head1;
                    isBothEqual = false;
                    break;
                }
                else if(ptr2.data>ptr1.data){
                    // head2 is larger, so put head1 first.
                    ans[0] = head1;
                    ans[1] = head2;
                    isBothEqual = false;
                    break;
                }
                else {
                    // Current digits are equal, so continue
                    // comparing the next pair of digits.
                    ptr1 = ptr1.next;
                    ptr2 = ptr2.next;
                }
            }

            // If every corresponding digit was equal,
            // both numbers represent the same value.
            //
            // Either can be treated as the smaller number;
            // the subtraction will produce zero.
            if(isBothEqual) {
                ans[0] = head1;
                ans[1] = head2;
            }
        }

        return ans;
    }
}