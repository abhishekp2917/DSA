import java.util.Comparator;
import java.util.PriorityQueue;

// Structure of Node Class
class Node {
    int data;
    Node next, bottom;
    
    public Node(int data){
        this.data = data;
    }
}

class Solution
{
    Node flatten(Node root)
    {
        PriorityQueue<Node> pq= new PriorityQueue<Node>(getLength(root),new Comparator<Node>(){
            @Override
            public int compare(Node o1, Node o2){
                if (o1.data<o2.data)
                    return -1;
                else if (o1.data==o2.data)
                    return 0;
                else 
                    return 1;
            }
        });
        
        Node curr = root;
        
        while(curr!=null) {
            pq.add(curr);
            curr = curr.next;
        }
        
        Node tempHead = new Node(-1), ptr = tempHead;
        
        while(pq.size()!=0) {
            Node smallestNode = pq.poll();
            ptr.bottom = smallestNode;
            ptr = ptr.bottom;
            smallestNode = smallestNode.bottom;
            ptr.bottom = null;
            if(smallestNode!=null) pq.add(smallestNode);
        }
        
        return tempHead.bottom;
    }
    
    public static int getLength(Node head) {
        int count = 0;
        while(head!=null) {
            count++;
            head = head.next;
        }
        return count;
    }
}