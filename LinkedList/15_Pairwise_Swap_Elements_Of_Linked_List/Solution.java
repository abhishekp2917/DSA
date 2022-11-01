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
    public Node pairwiseSwap(Node head)
    {
        if(head.next==null) return head;
        
        Node prev = head, curr = head, next = head.next, nxtNext = head.next.next, ans = head.next;
        
        while(next!=null) {
            prev.next = next;
            next.next = curr;
            curr.next = null;
            
            prev = curr;
            curr = nxtNext;
            if(curr==null) break;
            next = curr.next;
            if(next==null) prev.next = curr;
            else nxtNext = next.next;
        }
        
        return ans;
    }
}
