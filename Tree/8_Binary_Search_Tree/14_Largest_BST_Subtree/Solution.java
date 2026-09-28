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


class Solution{
    
    static int largestBst(Node root)
    {
		return traverse(root).maxSize;   
    }

	static NodeData traverse(Node root) {
		if(root==null) return new NodeData(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
		if(root.left==null && root.right==null) return new NodeData(true, 1, root.data, root.data);

		NodeData left = traverse(root.left);
		NodeData right = traverse(root.right);

		if(left.isBST && right.isBST && left.maxValue<root.data && right.minValue>root.data) 
			return new NodeData(true, left.maxSize + right.maxSize + 1, Math.min(root.data, left.minValue), Math.max(root.data, right.maxValue)); 
		else 
			return new NodeData(false, Math.max(left.maxSize, right.maxSize), Integer.MAX_VALUE, Integer.MIN_VALUE);
	}
}


class NodeData {
    
	boolean isBST;
	int maxSize;
	int minValue;
	int maxValue;

	public NodeData(boolean isBST, int maxSize, int minValue, int maxValue) {
		this.isBST = isBST;
		this.maxSize = maxSize;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
}