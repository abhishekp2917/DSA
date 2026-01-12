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
    public Node rotate(Node head, int k) {
        
        Node secondHead = head, firstHead = head, tail = head;
        
        while(k>1) {
            tail = tail.next;
            firstHead = firstHead.next;
            k--;
        }
        
        firstHead = firstHead.next;
        if(firstHead==null) return head;
        tail.next = null;
        
        Node curr = firstHead;
        
        while(curr!=null && curr.next!=null) {
            curr = curr.next;
        }
        
        curr.next = secondHead;
        
        return firstHead;
    }
}
