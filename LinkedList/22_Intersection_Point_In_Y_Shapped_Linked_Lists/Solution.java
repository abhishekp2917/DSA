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
    int intersectPoint(Node head1, Node head2)
	{
        int len1 = getLinkedListLength(head1), len2 = getLinkedListLength(head2);
         
        if(len1>len2) head1 = moveNodeByK(head1, len1-len2);
        else if(len2>len1) head2 = moveNodeByK(head2, len2-len1);
        
        while(head1!=null && head2!=null) {
            if(head1==head2) return head1.data;
            head1 = head1.next;
            head2 = head2.next;
        }
        return -1;
	}
	
	public static int getLinkedListLength(Node head) {
	    int count = 0;
	    while(head!=null) {
	        count++;
	        head = head.next;
	    }
	    return count;
	}
	
	public static Node moveNodeByK(Node head, int k) {
	    while(head!=null && k>0) {
	        head = head.next;
	        k--;
	    }
	    return head;
	}
}