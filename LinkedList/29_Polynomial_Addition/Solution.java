// Structure of Node Class
class Node {
    int pow, coeff;
    Node next;
    
    public Node(int pow, int coeff){
        this.pow = pow;
        this.coeff = coeff;
    }
}

class Solution
{
    public static Node addPolynomial(Node p1,Node p2)
    {
        Node head = new Node(-1, -1), curr = head;
        
        while(p1!=null && p2!=null) {
            if(p1.pow>p2.pow) {
                curr.next = p1;
                curr = curr.next;
                p1 = p1.next;
                curr.next = null;
            }
            else if(p2.pow>p1.pow) {
                curr.next = p2;
                curr = curr.next;
                p2 = p2.next;
                curr.next = null;
            }
            else {
                p1.coeff += p2.coeff;
                curr.next = p1;
                curr = curr.next;
                p1 = p1.next;
                p2 = p2.next;
                curr.next = null;
            }
        }
        
        if(p1!=null) curr.next = p1;
        else if(p2!=null) curr.next = p2;
        
        return head.next;
    }
}
