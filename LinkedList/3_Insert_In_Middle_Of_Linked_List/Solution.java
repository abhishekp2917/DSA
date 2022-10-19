// Structure of Node Class
class Node {
    int data;
    Node next;
    
    public Node(int data){
        this.data = data;
        this.next = null;
    }
}

class Solution {
    
    public Node insertInMid(Node head, int data){
        
        // craete two pointer i.e slow pointer which points to head and fast pointer which points
        // next node of head
        Node slow = head, fast = head.next;
        
        // move slow pointer by one node and fast pointer by two node until fast pointer next's next is not null
        // by moving slow pointer by one node and fast pointer by two node, once fast pointer reaches
        // last node then at that time slow pointer will point to prev node of the node in the middle of LinkedList
        while(fast!=null && fast.next!=null && fast.next.next!=null) {
            // moving slow pointer by one node
            slow = slow.next;
            // moving fast pointer by two node
            fast = fast.next.next;
        }
        
        // if fast pointer's next node is not null that means size of LinkedList is odd and slow pointer
        // is not pointing to middle node.So, move slow pointer by one node    
        if(fast!=null && fast.next!=null) slow = slow.next;
        
        // once slow pointer reaches to the node whose next should be new node, have a temp reference to 
        // slow next node and assign new node to next of slow pointer node 
        // then assign new node next to temp reference
        Node temp = slow.next;
        slow.next = new Node(data);
        slow.next.next = temp;
        
        return head;
    }
}