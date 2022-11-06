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
    Node swapkthnode(Node head, int num, int K)
    {
        int pos1 = K, pos2 = num-K+1;
        
        if(K>num || pos1==pos2) return head;
        
        if(pos1>pos2) {
            int temp = pos2;
            pos2 = pos1;
            pos1 = temp;
        }

        return swapTheNodes(head, pos1, pos2);
    }
    
    static Node swapTheNodes(Node head, int pos1, int pos2) {
        Node tempHead = new Node(-1);
        tempHead.next = head;
        boolean areConsecutiveNodes = pos2-pos1==1;
        
        Node prev1 = tempHead, prev2 = tempHead, curr1 = head, curr2 = head;
        
        while(pos1>1) {
            prev1 = curr1;
            curr1 = curr1.next;
            pos1--;
        }
        
        while(pos2>1) {
            prev2 = curr2;
            curr2 = curr2.next;
            pos2--;
        }
        
        Node next1 = curr1.next, next2 = curr2.next;
        if(areConsecutiveNodes) {
            prev1.next = curr2;
            curr2.next = curr1;
            curr1.next = next2;
        }
        else {
            prev1.next = curr2;
            curr2.next = next1;
            prev2.next = curr1;
            curr1.next = next2;
        }
        return tempHead.next;
    }
}