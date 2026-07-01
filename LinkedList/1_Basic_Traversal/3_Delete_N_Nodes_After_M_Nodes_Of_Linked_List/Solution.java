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
    static void linkdelete(Node head, int n, int m)
    {
        if(head==null) return;
        Node curr = head;
        int counter1 = m;
        int counter2 = n;
        while(curr!=null && curr.next!=null && counter1>1) {
            curr = curr.next;
            counter1--;
        }
        while(curr!=null && curr.next!=null && counter2>0) {
            curr.next = curr.next.next;
            counter2--;
        }
        linkdelete(curr.next, n, m);
    }
}
