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
        Node prev = null, curr = head, next;
        while(curr!=null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev.next;
    }
}
