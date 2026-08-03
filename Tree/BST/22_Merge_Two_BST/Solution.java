import java.util.ArrayList;
import java.util.List;

class Node  
{ 
    int data; 
    Node left, right; 
   
    public Node(int d)  
    { 
        data = d; 
        left = right = null; 
    } 
}

class Solution
{
    public List<Integer> merge(Node root1,Node root2)
    {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> ans = new ArrayList<>();

        // load both the BST nodes in ascending order in list1 and list2 resp.
        insertBST(root1, list1);
        insertBST(root2, list2);
        
        // initializing ptr1 and ptr2 which will be use to compare the current node value of each BST
        int ptr1 = 0, ptr2 = 0;

        // keep iterating until any one of the BST's all the nodes have been explored
        while(ptr1<list1.size() && ptr2<list2.size()) {

            // since both the lists are in increasing order, we just need to know which of the current
            // element is smallest as result of the merge should also be in increasing order 
            
            // whichever pointer is pointing element is smallest, add that element into the ans and
            // increment that pointer by one
            if(list1.get(ptr1)<list2.get(ptr2)) {
                ans.add(list1.get(ptr1));
                ptr1++;
            }
            else if(list1.get(ptr1)>list2.get(ptr2)) {
                ans.add(list2.get(ptr2));
                ptr2++;
            }

            // if both the pointer pointing element is same then add both the element and 
            // increment both the pointer
            else {
                ans.add(list1.get(ptr1));
                ans.add(list2.get(ptr2));
                ptr1++;
                ptr2++;
            }
        }

        // after coming out of first loop, if any of the list has element unexplored, add all the 
        // elements into the ans in the same order as it is in that list

        while(ptr1<list1.size())  {
            ans.add(list1.get(ptr1));
            ptr1++;
        }

        while(ptr2<list2.size())  {
            ans.add(list2.get(ptr2));
            ptr2++;
        }

        // return the ans list
        return ans;
    }

    // method which traverse tree in inorder fashion and add the node value is the list
    // traversing in inorder fashion so as to get list in increasing order 
    public void insertBST(Node root, List<Integer> list) {
        if(root!=null) {
            insertBST(root.left, list);
            list.add(root.data);
            insertBST(root.right, list);
        }
    }
}