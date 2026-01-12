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
    static Node findIntersection(Node head1, Node head2)
    {
        Node ans = new Node(-1), curr = ans;
        
        while(head1!=null && head2!=null) {
            if(head1.data==head2.data) {
                curr.next = new Node(head1.data);
                curr = curr.next;
                head1 = head1.next;
                head2 = head2.next;
            }
            else if(head1.data>head2.data) {
                head2 = head2.next;
            }
            else if(head1.data<head2.data) {
                head1 = head1.next;
            }
        }
        return ans.next;
    }
}
