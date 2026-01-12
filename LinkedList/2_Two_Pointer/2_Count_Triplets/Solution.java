import java.util.HashMap;

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
    static int countTriplets(Node head, int x) 
    { 
        if(head.next==null || head.next.next==null) return 0;
        
        if(head.data>head.next.data) head = reverse(head);
        
        Node lastNode = getLastNode(head), ptr1 = head, ptr2, ptr3;
        int count = 0;
       
        PrevNodeMap.generateMap(head);
       
        while(ptr1.next.next!=null) {
            
            ptr2 = ptr1.next;
            ptr3 = lastNode;
            
            while(ptr2.data<ptr3.data) {

                int sum = ptr1.data + ptr2.data + ptr3.data;
                
                if(sum==x) {
                    count++;
                    ptr2 = ptr2.next;
                    ptr3 = PrevNodeMap.getPrev(ptr3);
                }
                else if(sum>x) ptr3 = PrevNodeMap.getPrev(ptr3);
                else ptr2 = ptr2.next;
            }
            ptr1 = ptr1.next;
        }
        return count;
    } 
    
    static Node reverse(Node head) {
        Node prev = null, curr = head, next;
        while(curr!=null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    
    static Node getLastNode(Node head) {
        while(head.next!=null) {
            head = head.next;
        }
        return head;
    }
}

class PrevNodeMap {
    
    public static HashMap<Node, Node> map = new HashMap<Node, Node>();
    
    public static void generateMap(Node head) {
        Node prev = null, curr = head;
        while(curr!=null) {
           map.put(curr, prev);
           prev = curr;
           curr = curr.next;
        }
    }
    
    public static Node getPrev(Node curr) {
        return map.get(curr);
    }
}