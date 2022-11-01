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
    static Node segregate(Node head)
    {
        Node zeroesHead = new Node(-1), zero = zeroesHead;
        Node onesHead = new Node(-1), one = onesHead;
        Node twosHead = new Node(-1), two = twosHead;
        Node curr = head;
        
        while(curr!=null) {
            if(curr.data==0) {
                zero.next = curr;
                curr = curr.next;
                zero = zero.next;
                zero.next = null;
            }
            else if(curr.data==1) {
                one.next = curr;
                curr = curr.next;
                one = one.next;
                one.next = null;
            }
            else {
                two.next = curr;
                curr = curr.next;
                two = two.next;
                two.next = null;
            }
        }
        
        if(onesHead.next!=null) {
            zero.next = onesHead.next;
            one.next = twosHead.next;
        }
        else {
           zero.next = twosHead.next;
        }
        return zeroesHead.next;
    }
}
