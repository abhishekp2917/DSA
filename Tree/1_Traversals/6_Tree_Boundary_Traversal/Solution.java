import java.util.ArrayList;

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
	public ArrayList<Integer> boundaryTraversal(Node root)
	{
	    ArrayList <Integer> result = new ArrayList <Integer>();
	    result.add(root.data);
	    getLeftBoundaryNodes(root.left, result);
	    getLeafNodes(root.left, result);
	    getLeafNodes(root.right, result);
	    getRightBoundaryNodes(root.right, result);
	    return result;
	}
	
	void getLeftBoundaryNodes(Node root, ArrayList <Integer> leftBoundary) {
	    if(root==null || (root.left==null && root.right==null)) return;
	    leftBoundary.add(root.data);
	    getLeftBoundaryNodes(root.left, leftBoundary);
	    if(root.left==null) getLeftBoundaryNodes(root.right, leftBoundary);
	} 
	
	void getLeafNodes(Node root, ArrayList <Integer> leafNodes) {
	    if(root==null) return;
	    if(root.left==null && root.right==null) {
	        leafNodes.add(root.data);
	        return;
	    }
	    getLeafNodes(root.left, leafNodes);
	    getLeafNodes(root.right, leafNodes);
	}
	
	void getRightBoundaryNodes(Node root, ArrayList <Integer> rightBoundary) {
	    if(root==null || (root.left==null && root.right==null)) return;
	    getRightBoundaryNodes(root.right, rightBoundary);
	    if(root.right==null) getRightBoundaryNodes(root.left, rightBoundary);
	    rightBoundary.add(root.data);
	} 
}