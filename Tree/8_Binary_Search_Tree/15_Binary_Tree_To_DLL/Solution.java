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
    Node bToDLL(Node root)
    {
	    return traverse(root).first;
    }
    
    NodeData traverse(Node root) {
        if(root==null) return new NodeData(null, null);
        if(root.left==null && root.right==null) return new NodeData(root, root);
        NodeData left = traverse(root.left);
        NodeData right = traverse(root.right);
        if(left.last!=null) left.last.right = root;
        root.left = left.last;
        if(right.first!=null) right.first.left = root;
        root.right = right.first;
        
        Node first = left.first, last = right.last;
        if(first==null) first = root;
        if(last==null) last = root;
        return new NodeData(first, last);
    }
}

class NodeData {
    Node first, last;
    NodeData(Node first, Node last) {
        this.first = first;
        this.last = last;
    }
}