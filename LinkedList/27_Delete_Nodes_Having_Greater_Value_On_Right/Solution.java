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
    Node compute(Node head)
    {
        head = reverse(head);
        Node prev = head, curr = head.next;
        
        while(curr!=null) {
            if(prev.data>curr.data) {
                prev.next = curr.next;
            }
            else prev = curr;
            curr = curr.next;
        }
        head = reverse(head);
        return head;
    }
    
    public static Node reverse(Node head) {
        Node prev = null, curr = head, next;
        while(curr!=null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}