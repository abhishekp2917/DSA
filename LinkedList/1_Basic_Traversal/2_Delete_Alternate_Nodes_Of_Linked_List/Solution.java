// Structure of Node Class
class Node {
    int data;
    Node next;
    
    public Node(int data){
        this.data = data;
    }
}

class Solution {
    
    public void deleteAlternate (Node head){
        
        // create a pointer which will intially points to head of the LinkedList
        Node curr = head;
        
        // while curr pointer and its next is not null perform below operation
        while(curr!=null && curr.next!=null) {
            
            // make curr node next to curr curr node next's next so that it will remove 
            // curr node next node from LinkedList
            curr.next = curr.next.next;

            // after removing next node move curr pointer to curr node new next node
            curr = curr.next;
        }
    }
}