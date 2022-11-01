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
    static void linkdelete(Node head, int M, int N)
    {
        Node tempHead = new Node(-1);
        tempHead.next = head;
        Node prev = tempHead, curr = head;
        
        while(true) {
            curr = moveNodeByK(curr, M+N);
            prev = moveNodeByK(prev, M);
            if(prev==null) break;
            else prev.next = curr;
            if(curr==null) break;
        }
        head = tempHead.next;
    }
    
    public static Node moveNodeByK(Node head, int k) {
        while(head!=null && k>0) {
            head = head.next;
            k--;
        }
        return head;
    }
}
