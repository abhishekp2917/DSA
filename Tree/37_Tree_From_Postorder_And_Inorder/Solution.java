import java.util.HashMap;

class Node
{
    int data;
    Node left;
    Node right;

    Node(int value)
    {
        data = value;
        left = null;
        right = null;
    }
}


class GfG
{
    int preStart;
    
    //Function to return a tree created from postorder and inoreder traversals.
    Node buildTree(int in[], int post[], int n) {
        // map to store the index of a particular node of postorder in inorder  
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<n; i++) map.put(in[i], i);
        // we have to traverse postorder in reverse to use it as preorder
        // declarinf index to keep track of current preorder node, starting with last node
        preStart = n-1;
        return build(in, post, n-1, n, map);
    }
    
    Node build(int[] in, int[] post, int inStart, int size, HashMap<Integer, Integer> map) {
        // if size of current subtree is 0, just return
        if(size<=0 || preStart<0) return null;
        // create a node with a value that current pointer is pointing to in postorder 
        Node root = new Node(post[preStart]);
        // get the index of current node in inorder
        int index = map.get(root.data);
        // using this inorder index, find the size of right and left subtree
        // number of nodes left to current idex will determine the size of left subtree
        // number of nodes right to current idex will define the size of right subtree
        int rightSubtreeSize = inStart-index;
        int leftSubtreeSize = index-(inStart-size+1);
        // decrement the preorder pointer 
        preStart--;
        // build the right subtree first because reverse of postorder is same as preorder when 
        // traversed right subtree first  
        root.right = build(in, post, inStart, rightSubtreeSize, map);
        root.left = build(in, post, index-1, leftSubtreeSize, map);
        // atlast return the root of current subtree
        return root;
    }
}