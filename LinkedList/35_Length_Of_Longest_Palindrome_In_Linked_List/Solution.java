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
    public static int maxPalindrome(Node head)
    {
        int maxLength = 1;
        
        Node ptr1 = head, ptr2 = ptr1.next;
        
        while(ptr2!=null) {
            splitLinkedList(ptr1);
            Node head1 = reverse(head), head2 = ptr2;
            int oddLenPalindrome = compare(head1, head2.next)*2+1, evenLenPalindrome = compare(head1, head2)*2;
            maxLength = Math.max(maxLength, Math.max(oddLenPalindrome, evenLenPalindrome));
            head1 = reverse(head1);
            joinLinkedList(ptr1, ptr2);
            ptr1 = ptr2;
            ptr2 = ptr2.next;
        }
        return maxLength;
    }
    
    public static Node reverse(Node head) {
        Node prev = null, curr = head, next = null;
        while(curr!=null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    
    public static void splitLinkedList(Node node) {
        node.next = null;
    }
    
    public static void joinLinkedList(Node tail, Node head) {
        tail.next = head;
    }
    
    public static int compare(Node head1, Node head2) {
        int count = 0;
        while(head1!=null && head2!=null && head1.data==head2.data){
            count++;
            head1 = head1.next;
            head2 = head2.next;
        } 
        return count;
    }
}
