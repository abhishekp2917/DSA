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
    void splitList(Node head)
    {
        Node slow = head, fast = head.next;
        
        while(fast!=head && fast.next!=head) {
            
            slow = slow.next;
            fast = fast.next.next;
        }
        
        Node head1 = head;
        Node head2 = slow.next;
        
        slow.next = head1;
        
        Node curr = head2;
        while(curr.next!=head) {
            curr = curr.next;
        }
        
        curr.next = head2;
    }
}
