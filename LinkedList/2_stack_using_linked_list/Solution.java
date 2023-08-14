// Structure of Node Class
class StackNode {
    int data;
    StackNode next;
	StackNode(int a) {
        data = a;
		next = null;
	}
}  

class MyStack 
{
	// Head of the LinkedList 
    StackNode head;
    
    //Function to push an integer into the stack.
    void push(int a) 
    {
		// if no node is present then just add a node to head of LinkedList
        if(head==null) head = new StackNode(a);
        else {

            // store a reference of current head 
			StackNode tempHead = head;

            // make the head point to new value and then attach the previous head next to current head
            head = new StackNode(a);
            head.next = tempHead;
        }
    }
    
    //Function to remove an item from top of the stack.
    int pop() 
    {
        // if head is null return -1
		if(head==null) return -1;

        // else store the head value and make the head poin to its next node this way top (head) of LL will get eleminated  
        int ans = head.data;
        head = head.next;
        return ans;
    }
}
