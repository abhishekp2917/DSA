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
    static Node addTwoLists(Node first, Node second){
        
        first = reverse(first);
        second = reverse(second);

        Node ans = new Node(-1), ptr1 = first, ptr2 = second, ptr3 = ans;
        int carry = 0;
        
        while(ptr1!=null || ptr2!=null || carry!=0) {
            
            int num1, num2, tempSum;
            
            if(ptr1!=null) {
                num1 = ptr1.data;
                ptr1 = ptr1.next;
            }
            else num1 = 0;
            
            if(ptr2!=null) {
                num2 = ptr2.data;
                ptr2 = ptr2.next;
            }
            else num2 = 0;
            
            tempSum = num1 + num2 + carry;
            
            ptr3.next = new Node(tempSum%10);
            ptr3 = ptr3.next;
            
            carry = tempSum/10;
        }
        
        return reverse(ans.next);
    }
    
    public static Node reverse(Node head) {
        Node prev = null, curr = head, next = null;
        while(curr!=null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
