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
    public static Node insertionSort(Node head)
    {
        Node tempHead = new Node(-1);
        tempHead.next = head;
        
        Node ptr, prev = head, curr = head.next;
        
        while(curr!=null) {
            ptr = tempHead;
            
            while(ptr.next!=curr && curr.data>ptr.next.data) {
                ptr = ptr.next;
            }
            if(ptr.next!=curr) {
                prev.next = curr.next;
                curr.next = ptr.next;
                ptr.next = curr;
                curr = prev.next;
            }
            else {
                prev = curr;
                curr = curr.next;
            }
        }
        return tempHead.next;
    }
}
