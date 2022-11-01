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
    Node sortedInsert(Node head1, int key) {
        
        Node curr = head1, prev = null;
        
        while(curr!=null && curr.data<key) {
            prev = curr;
            curr = curr.next;
        }
        
        if(prev==null) {
            Node ans = new Node(key);
            ans.next = head1;
            return ans;
        }
        else {
            prev.next = new Node(key);
            prev.next.next = curr;
            return head1;
        }
    }
}
