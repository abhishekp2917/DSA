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
    Node reorderlist(Node head) {
        Node midNode = findMiddleNode(head);
        Node firstPartHead = head, secPartHead = midNode.next;
        midNode.next = null;
        secPartHead = reverse(secPartHead);
        Node ptr1 = firstPartHead, ptr2 = secPartHead;
        
        while(ptr2!=null) {
            Node ptr1Next = ptr1.next, ptr2Next = ptr2.next;
            ptr1.next = ptr2;
            ptr2.next = ptr1Next;
            ptr1 = ptr1Next;
            ptr2 = ptr2Next;
        }
        return firstPartHead;
    }
    
    public static Node reverse(Node head) {
        Node prev = null, curr = head, next;
        while(curr!=null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    
    public static Node findMiddleNode(Node head) {
        Node slow = head, fast = head.next;
        while(fast!=null && fast.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
