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

// first approach
class Solution1
{
    // declare a variable to store the height of the first leaf node that we will encounter
    // initially it will -1 to indicate we haven't seen the leaf node yet
    int treeHeight = -1;
    
    boolean check(Node root)
    {
	  return checkLeaveNodes(root, 0);
    }
    
    // function to traverse the tree while keeping track of the heaigh of the current node
    boolean checkLeaveNodes(Node root, int height) {
        
        // if root is null return true
        if(root==null) return true;
        
        // when we reach a leaf node, check if it's the first leaf node that we reach 
        if(root.left==null && root.right==null) {
            // if it's the first leaf node, then update the treeHeight var and just return true 
            if(treeHeight==-1) {
                treeHeight = height;
                return true;
            }
            // if we have already seen a leaf node before, then treeHeight var must have been updated
            // so compare the heaigh of curr leaf with the height of the leaf node that we encounter very first
            // if it's the same return true else false
            return treeHeight==height;
        }
        // traverse left and right subtree and return logical and of their returned value
        return checkLeaveNodes(root.left, height+1) && checkLeaveNodes(root.right, height+1);
    }
}


// second approach
class Solution2
{
    boolean check(Node root)
    {
        // find the depth of the deepest leaf node as well as of the least deep leaf node
        int max_dist = maxDist(root);
        int min_dist = minDist(root);

        // once we found out the depth of least deep and the deepest leaf node, all the leaf node will reside only in this range
        // so if max and min depth is same then all the leaf node will be at same level else not
        if(max_dist == min_dist) return true;
        return false;
    }

    // function to find the depth of the deepest leaf node or max distance from root to leaf node 
    int maxDist(Node curr)
    {
        // if node curr is null then return 0
        if (curr == null)
            return 0;
 
        // call for maximum height of left subtree
        int left_dist = maxDist(curr.left);
 
        // call for maximum height of right subtree
        int right_dist = maxDist(curr.right);
 
        // we are taking max(left_dist , right_dist) because we need to find the maximum height
        // adding  1 to the max(left_dist , right_dist) of them as current node also need to be counted
        return 1 + Math.max(left_dist, right_dist);
    }
 
    int minDist(Node curr)
    {
        // if the node curr is null than we can assume that
        // it is at distance infinity(Integer.MAX_VALUE)
        // as we have to find the minimum height
        if (curr == null)
            return Integer.MAX_VALUE;
 
        // if the node curr is leaf node then simply return 1
        if (curr.left == null && curr.right == null)
            return 1;
 
        // call for minimum height of left subtree
        int left_dist = minDist(curr.left);
 
        // call for minimum height of right subtree
        int right_dist = minDist(curr.right);

        // we are taking min(left_dist , right_dist) because we need to find the minimum height
        // add 1 to the min(left_dist , right_dist) of them as current node also need to be counted
        return 1 + Math.min(left_dist, right_dist);
    }
}