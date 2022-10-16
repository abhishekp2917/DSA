// Structure of LinkedList
class Node
{
	int data;
	Node next;
	Node(int d)
	{
		data = d;
		next = null;
	}
}


class Solution {

	public static boolean detectLoop(Node head){
        
		// slow and fast pointers
		// slow pointer moves forward by one node
		// fats pointer moves forward by two node
        Node slow = head, fast = head.next;
        
		// while next's next node of fast pointer is not null 
		// check if slow and fast pointer points to same node or not
        while(fast!=null && fast.next!=null && fast.next.next!=null) {
            
			// if slow and fast pointer points to same node the loop exists else not
            if(slow==fast) return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }
}