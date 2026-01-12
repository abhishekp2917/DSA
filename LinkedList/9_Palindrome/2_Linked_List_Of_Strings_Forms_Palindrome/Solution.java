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
    public static boolean compute(Node head)
    {
        Node ptr1 = head;
        String str1 = "", str2 = "";
        
        while(ptr1!=null) {
            str1 += ptr1.data;
            ptr1 = ptr1.next;
        }

        str2 = reverse(str1);
        
        return str1.equals(str2);
    }
    
    public static String reverse(String str) {
        String ans = "";
        for(int i=str.length()-1; i>=0; i--) {
            ans += String.valueOf(str.charAt(i));
        }
        return ans;
    }
}
