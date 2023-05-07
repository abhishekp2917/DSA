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

class GfG
{
    // return true/false denoting whether the tree is Symmetric or not
    public static boolean isSymmetric(Node root)
    {
        return checkIfSymmetric(root, root);
    }
    
    // function to check if tree is symmetric
    // it takes two node values. Intially both the nodes will point to same node cause we need to check or single tree 
    static boolean checkIfSymmetric(Node root1, Node root2) {

        // if both the nodes are null then return true cause both the nodes are same 
        if(root1==null && root2==null) return true;
        
        // if they both aren't null, one time, for first node go to its right and for second node go to its left
        // and second time do vice-versa

        // return AND logic of whatever returned from both the process
        if(root1!=null && root2!=null && root1.data==root2.data) {
            return checkIfSymmetric(root1.left, root2.right) && checkIfSymmetric(root1.right, root2.left);
        }
        return false;
    }
}