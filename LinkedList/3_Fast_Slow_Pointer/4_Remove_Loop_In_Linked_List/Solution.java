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
    public static void removeLoop(Node head){
        
        Node slow = head, fast = head.next;
        
        while(fast!=slow) {
            if(fast==null || fast.next==null) return;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        int loopSize = 0;
        
        do{
            fast = fast.next;
            loopSize++;
        }while(fast!=slow);
    
        slow = fast = head;
        
        while(loopSize>1) {
            fast = fast.next;
            loopSize--;
        }
        
        while(fast.next!=slow) {
            fast = fast.next;
            slow = slow.next;
        }
        
        fast.next=null;
    }
}
