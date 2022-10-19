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
    StackNode Head;
    
    //Function to push an integer into the stack.
    void push(int a) 
    {
		// if no node is present then just add a node to head of LinkedList
        if(Head==null) Head = new StackNode(a);
		// else move towards last node and add new Node there
        else {

			// create a pointer which points to head  
            StackNode tempHead = Head;
			// then move that pointer to next nodes until pointer next node is null
            while(tempHead.next!=null) tempHead = tempHead.next;
			// then create new Node with the value that is to be added to top of the stack and
			// assign it to last node next reference 
            tempHead.next = new StackNode(a);
        }
    }
    
    //Function to remove an item from top of the stack.
    int pop() 
    {
		// if head is null that means there is no element in stack so return -1
        if(Head==null) return -1;

		// create three pointers which points to previous , current and next node 
		// so that when current node moves to last node then we will have referenec to second last node 
		// and can make it null which will remove last node (top of the stack) from the stack
        StackNode prev = null, curr = Head, next = Head.next;
        
		// move all pointers forward until reference to next node is null so that current will move
		// to last node (top) of the stack  
        while(next!=null) {
            prev = curr;
            curr = next;
            next = next.next;
        }
        
		
		// if previous node is not null then make previous next reference to null so that it will
		// remove last (top) node from the stack  
        if(prev!=null) prev.next = null;
		// if previous node is null that means current is pointing to head and there is only one
		// node in stack so just remove the current (head) node
        else Head = null;
        
		// return current node as it will point to last (top) node of the stack
        return curr.data;
    }
}
