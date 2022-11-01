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
    public static Node zigZag(Node head){
        Node tempHead = new Node(-1);
        tempHead.next = head;
        
        Node prev = tempHead, curr = head, next = head.next;
        boolean shouldBeLessThan = true, hasSwapped = false;
        
        while(next!=null) {
            if(shouldBeLessThan) {
                if(curr.data>next.data) {
                    swap(prev, curr, next, next.next);
                    hasSwapped = true;
                }
            }
            else 
            {
                if(curr.data<next.data) {
                    swap(prev, curr, next, next.next);
                    hasSwapped = true;
                }
            }
            if(hasSwapped) {
                prev = prev.next;
                curr = next.next;
                next = curr.next;
                hasSwapped = false;
            }
            else {
                prev = curr;
                curr = next;
                next = next.next;
            }
            shouldBeLessThan = !shouldBeLessThan;
        }
        
        return tempHead.next;
    }
    
    public static void swap(Node ptr1Prev, Node ptr1, Node ptr2, Node ptr2Next) {
        ptr1Prev.next = ptr2;
        ptr2.next = ptr1;
        ptr1.next = ptr2Next;
    }
}
