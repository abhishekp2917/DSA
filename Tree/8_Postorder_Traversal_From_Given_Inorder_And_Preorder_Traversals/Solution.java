import java.util.HashMap;

class Node {
    int data;
    Node left;
    Node right;
    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}


class GfG
{
    int index = 0;
    
	void printPostOrder(int in[], int pre[], int n)
	{
		// map to store the index of element in in[] so that we can get element index in O(N)
	    HashMap<Integer, Integer> inMap = new HashMap<Integer, Integer>();
	    for(int i=0; i<n; i++) {
			inMap.put(in[i], i);
		} 
	    traverse(in, pre, 0, n, inMap);
	}
	
	// traverse method which prints node in postorder that are in subarr of inorder
	// subarr starts at inStart and having length of inLength
	void traverse(int in[], int pre[], int inStart, int inLength, HashMap<Integer, Integer> inMap) {
		// if length of subarr is 0, then return as nothing to print
	    if(inLength<=0 || index>=in.length) return;
		// get the index of current element of preorder in inorder array 
	    int inIndex = inMap.get(pre[index]);
		// all the element left to inIdex will be part of left subtree and element right to inIdex will be part of right subtree
	    int leftLength = inIndex-inStart;
	    int rightLength = inStart + inLength-inIndex-1;
		// increment the index of preOrder pointer
	    index++;
		// traverse left and right subtree
	    traverse(in, pre, inStart, leftLength, inMap);
	    traverse(in, pre, inIndex+1, rightLength, inMap);
		// after coming back from the left and right subtree, print the current root element as we have to print in postorder
	    System.out.print(in[inIndex] + " ");
	}
}