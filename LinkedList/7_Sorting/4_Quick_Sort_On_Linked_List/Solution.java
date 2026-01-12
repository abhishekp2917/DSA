import java.util.ArrayList;

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
    public static Node quickSort(Node head)
    {
        if(head.next==null) return head;
        
        ArrayList<Node> nodes = splitNodesAroundThreshold(head, head.data);
        
        if(nodes.get(0)!=null) nodes.set(0, quickSort(nodes.get(0)));
        if(nodes.get(2)!=null) nodes.set(2, quickSort(nodes.get(2)));
        
        Node tempHead = new Node(-1), ptr = tempHead;
        
        if(nodes.get(0)!=null) {
            ptr.next = nodes.get(0);
            ptr = getLastNode(nodes.get(0));
        } 
        if(nodes.get(1)!=null) {
            ptr.next = nodes.get(1);
            ptr = getLastNode(nodes.get(1));
        }
        if(nodes.get(2)!=null) {
            ptr.next = nodes.get(2);
            ptr = getLastNode(nodes.get(2));
        }
        return tempHead.next;
    }
    
    public static ArrayList<Node> splitNodesAroundThreshold(Node head, int threshold) {
        Node tempHead = new Node(-1);
        tempHead.next = head;
        Node prev = tempHead, curr = prev.next;
        
        Node belowThresholdHead = new Node(-1), ptr1 = belowThresholdHead;
        Node equalToThresholdHead = new Node(-1), ptr2 = equalToThresholdHead;
        Node aboveThresholdHead = new Node(-1), ptr3 = aboveThresholdHead;
        
        while(curr!=null) {
            if(curr.data<threshold) {
                ptr1.next = curr;
                ptr1 = ptr1.next;
                prev.next = curr.next;
                curr = curr.next;
                ptr1.next = null;
            }
            else if(curr.data>threshold) {
                ptr3.next = curr;
                ptr3 = ptr3.next;
                prev.next = curr.next;
                curr = curr.next;
                ptr3.next = null;
            }
            else {
                ptr2.next = curr;
                ptr2 = ptr2.next;
                prev.next = curr.next;
                curr = curr.next;
                ptr2.next = null;
            }
        }
        
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(belowThresholdHead.next);
        nodes.add(equalToThresholdHead.next);
        nodes.add(aboveThresholdHead.next);
        return nodes;
    }
    
    public static Node getLastNode(Node head) {
        while(head.next!=null) head = head.next;
        return head;
    }
}
