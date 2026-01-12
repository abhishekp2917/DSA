// Structure of Node Class
class Node {
    int data;
    Node next;
    
    public Node(int data){
        this.data = data;
    }
}

class Solution {
    
    public int sum(Node head, int k){
      
        // create two pointer
        // both will intially point to head of LinkedList 
        Node slow = head, fast = head;

        // variable to store result
        int sum = 0;
        
        // move fast pointer K nodes ahead 
        while(k>0) {
            fast = fast.next;
            k--;
        }
        
        // move both slow and fast node ahead until fast pointer is not null
        // so that slow pointer will reach to the Kth node from the last
        while(fast!=null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // now move slow node forward and add node values to the variable 'sum'
        // move until slow pointer points to null
        while(slow!=null) {
            sum += slow.data;
            slow = slow.next;
        }
      
        return sum;
    }
}