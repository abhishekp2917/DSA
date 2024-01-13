// Linked List Node
class LNode
{
    int data;
    LNode next;
    LNode(int d) {
        data = d; 
        next = null;
    }
}

//Tree Node
class TNode
{
    int data;
    TNode left, right;
    TNode(int x)
    {
        data=x;
        left=right=null;
    }
    
}

class Solution
{
    public TNode sortedListToBST(LNode head)
    {
        // variable size to store size of linkedlist
        int size = 0;

        // calculating size of linkedlist
        LNode ptr = head;
        while(ptr!=null) {
            size++;
            ptr = ptr.next;
        }

        // creating HeadRef class object which will store LNode to track current node while traversing the linkedlist
        // here we can't use LNode object directly because while traversing even if we will make the head point to it's next
        // node, on backtracking the head will not point to the latest node as the reference will not update  
        HeadRef headRef = new HeadRef(head);
        return traverse(headRef, size);
    }
    
    TNode traverse(HeadRef headRef, int n) {
        if(n<=0) return null;

        // build tree while traversing in inorder fashion 
        // for building left subtree, reduce the size of Linked list to half so as to get balanced BST
        TNode left = traverse(headRef, n/2);

        // after backtracking from left, create a new TNode and assign current headref head data to it
        TNode root = new TNode(headRef.head.data);

        // after assigning, make the HeadRef object head ref point to it's next node
        // since there is only one HeadRef throughout the traversal, changing the content of this object will affect throughout the traversal 
        headRef.head = headRef.head.next;

        // for building right subtree, the size of Linked list will current size - size of left subtree - 1(subtracting 1 beacuse of root node)  
        TNode right = traverse(headRef, n-n/2-1);

        // assign whatever returned from left and right traveral to current root left and right child resp.
        root.left = left;
        root.right = right;

        // finally return root
        return root;
    }
}

class HeadRef {
    LNode head;
    HeadRef(LNode head) {
        this.head = head;
    }
}