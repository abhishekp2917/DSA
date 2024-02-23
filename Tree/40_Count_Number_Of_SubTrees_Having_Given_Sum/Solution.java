class Node{
    int data;
    Node left,right;
    Node(int d)
    {
        data=d;
        left=right=null;
    }
}


class Tree
{
    //Function to count number of subtrees having sum equal to given sum.
    int countSubtreesWithSumX(Node root, int x)
    {
        return traverse(root, x).count;
    }
    
    NodeData traverse(Node root, int x) {
        // if root is null, then it's sum as well as count of subtree whose sum equals to target will be 0
        if(root==null) return new NodeData(0, 0);
        // traverse left and right subtree which will return NodeData that will have sum of left/right subtree and aslo the count of 
        // subtree that has sum equals to x
        NodeData left = traverse(root.left, x);
        NodeData right = traverse(root.right, x);
        // calculate the current subtree sum 
        int tempSum = root.data + left.treeSum + right.treeSum;
        // if sum is equal to target, then add 1 to the return value i.e. count from left and right subtree
        if(tempSum==x) return new NodeData(tempSum, 1+ left.count + right.count);
        // else return count from left and right subtree only
        else return new NodeData(tempSum, left.count + right.count);
    }
}

// declared NodeData class which will hold the sum of subtree and the number of subtree within the current 
// subtree whose sum meets the target 
class NodeData {
    int treeSum, count;
    NodeData(int treeSum, int count) {
        this.treeSum = treeSum;
        this.count = count;
    }
}