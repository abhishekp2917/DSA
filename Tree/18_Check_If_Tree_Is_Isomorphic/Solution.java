class Node
{
    int data;
    Node left, right;

    Node(int item)
    {
        data = item;
        left = right = null;
    }
}

class Solution  
{ 
    // Return True if the given trees are isomotphic. Else return False.
    boolean isIsomorphic(Node root1, Node root2)  
    { 
        // if both the roots are null then they are same so return true
        if(root1==null && root2==null) return true;
        // if only one of them is null, then return false cause both can't be the same
        if(root1==null || root2==null) return false;

        // both the roots are same, then check if there subtrees are also isomorphic 
        if(root1.data==root2.data) {

            // check either both roots left and right are the same or one's left is equal to other's right
            // and vice versa
            // if above case satisfies, then it will return true else will return false
            return (isIsomorphic(root1.left, root2.left) && isIsomorphic(root1.right, root2.right)) ||
            (isIsomorphic(root1.left, root2.right) && isIsomorphic(root1.right, root2.left));
        }
        // if roots aren't the same, then return false. There's no need to check there subtrees
        return false;
    }
    
}    
       