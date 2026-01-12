// Structure of Node Class
class Node {
    int data;
    Node next, prev;
    
    public Node(int data){
        this.data = data;
    }
}

class Solution
{
    public static Node reverseCLL(Node head)
    {
        Node tail = head, prev = null, curr = head, next;
        do {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }while(curr!=head);
        
        tail.next = prev;
        return prev;
    }
}
