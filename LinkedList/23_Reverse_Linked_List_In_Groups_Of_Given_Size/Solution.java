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
    public static Node reverse(Node head, int k)
    {
        Node prev = null, curr = head, next = null;
        int counter = 0;
        while(counter<k && curr!=null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            counter++;
        }
        if(next!=null) head.next = reverse(next, k);
        return prev;   
    }
}