// Structure of Node Class
class Node {
    int data;
    Node next;
    
    public Node(int data){
        this.data = data;
    }
}

class Solution {
    public static int maxPalindrome(Node head)
    {
        int maxLength = 1; 
        Node ptr1 = null, ptr2 = head;
        while(ptr2!=null) {
            Node head2 = ptr2;
            ptr2 = ptr2.next;
            head2.next = null;
            ptr1 = addToFront(ptr1, head2);
            maxLength = Math.max(
                maxLength,
                Math.max(
                    compare(ptr1, ptr2),
                    1 + compare(ptr1.next, ptr2) 
                )
            ); 
        }
        return maxLength;
    }
    
    public static Node addToFront(Node head, Node node) {
        node.next = head;
        return node;
    }
    
    public static int compare(Node head1, Node head2) {
        int length = 0;
        while(head1!=null && head2!=null && head1.data==head2.data){
            length += 2;
            head1 = head1.next;
            head2 = head2.next;
        } 
        return length;
    }
}