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
    Node removeDuplicates(Node head)
    {
        Node curr = head, next = head.next;
        
	    while(next!=null) {
	        if(curr.data==next.data) curr.next = next.next;
	        else curr = next;
	        next = curr.next;
	    }
	    
	    return head;
    }
}
