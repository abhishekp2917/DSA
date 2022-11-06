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
    static Node mergeSort(Node head)
    {
        if(head.next==null) return head;
        Node mid = middleNode(head);
        Node firstPart = head, secPart = mid.next;
        mid.next = null;
        
        firstPart = mergeSort(firstPart);
        secPart = mergeSort(secPart);
        
        Node tempHead = new Node(-1), curr = tempHead, ptr1 = firstPart, ptr2 = secPart;
        
        while(ptr1!=null && ptr2!=null) {
            if(ptr1.data<ptr2.data) {
                curr.next = ptr1;
                curr = curr.next;
                ptr1 = ptr1.next;
                curr.next = null;
            }
            else {
                curr.next = ptr2;
                curr = curr.next;
                ptr2 = ptr2.next;
                curr.next = null;
            }
        }
        
        if(ptr1!=null) curr.next = ptr1;
        else if(ptr2!=null) curr.next = ptr2;
        return tempHead.next;
    }
    
    public static Node middleNode(Node head) {
        Node slow = head, fast = head.next;
        while(fast!=null && fast.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
