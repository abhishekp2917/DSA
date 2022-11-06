import java.util.HashMap;
import java.util.PriorityQueue;

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
    Node mergeKList(Node[]arr,int K)
    {
        HashMap<Integer, Node> ptrs = new HashMap<Integer, Node>();
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        Node mergedHead = new Node(-1), curr = mergedHead;
        
        for(int i=0; i<K; i++) {
            ptrs.put(arr[i].data, arr[i]);
            pq.add(arr[i].data);
        }
        
        while(ptrs.size()!=0) {
            int key = pq.poll();
            Node tempNode = ptrs.get(key);
            ptrs.remove(key);
            
            curr.next = tempNode;
            curr = curr.next;
            
            tempNode = tempNode.next;
            if(tempNode!=null) {
                ptrs.put(tempNode.data, tempNode);
                pq.add(tempNode.data);
            }
            curr.next = null;
        }
        return mergedHead.next;
    }
}