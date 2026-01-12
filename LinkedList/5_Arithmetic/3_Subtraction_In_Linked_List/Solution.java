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
    static Node subLinkedList(Node l1, Node l2)
    {
        l1 = removeLeadingZeroes(l1);
        l2 = removeLeadingZeroes(l2);
        
        Node heads[] = compare(l1, l2);
        
        heads[0] = reverse(heads[0]);
        heads[1] = reverse(heads[1]);
        
        Node smallPtr = heads[0], largePtr = heads[1];
        
        while(smallPtr!=null) {
            largePtr.data -= smallPtr.data;
            smallPtr = smallPtr.next;
            largePtr = largePtr.next;
        }
        
        Node curr = heads[1], next = heads[1].next;
        
        while(next!=null) {
            if(curr.data<0) {
                curr.data += 10;
                next.data -= 1;
            }
            curr = next;
            next = next.next;
        }
        
        heads[1] = reverse(heads[1]);
        heads[1] = removeLeadingZeroes(heads[1]);
        
        if(heads[1]==null) return new Node(0);
        else return heads[1];
    }
    
    public static Node removeLeadingZeroes(Node head) {
        while(head!=null && head.data==0) {
            head = head.next;
        }
        return head;
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
    
    public static int getLength(Node head) {
        int count = 0;
        while(head!=null) {
            count++;
            head = head.next;
        }
        return count;
    }
    
    public static Node[] compare(Node head1, Node head2) {
        int len1 = getLength(head1), len2 = getLength(head2);
        Node[] ans = new Node[2];
        
        if(len1>len2) {
            ans[0] = head2;
            ans[1] = head1;
        }
        else if(len2>len1) {
            ans[0] = head1;
            ans[1] = head2;
        }
        else {
            Node ptr1 = head1, ptr2 = head2;
            boolean isBothEqual = true;
            while(ptr1!=null && ptr2!=null) {
                if(ptr1.data>ptr2.data) {
                    ans[0] = head2;
                    ans[1] = head1;
                    isBothEqual = false;
                    break;
                }
                else if(ptr2.data>ptr1.data){
                    ans[0] = head1;
                    ans[1] = head2;
                    isBothEqual = false;
                    break;
                }
                else {
                    ptr1 = ptr1.next;
                    ptr2 = ptr2.next;
                }
            }
            if(isBothEqual) {
                ans[0] = head1;
                ans[1] = head2;
            }
        }
        return ans;
    }
}
