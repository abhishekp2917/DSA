class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public long weightedSum(int[] parent, int[] nums) {
        int n = parent.length;
        long weightedSum = 0;
        int[] nodesDepth = new int[n];
        int treeHeight = 0;
        for(int node=0; node<n; node++) {
            int depth = getDepth(parent, nodesDepth, node);
            treeHeight = Math.max(treeHeight, depth);
        }    
        for(int node=0; node<n; node++) {
            weightedSum += ((long)nums[node])*(treeHeight-nodesDepth[node]+1);
        }
        return weightedSum;
    }

    private int getDepth(int[] parent, int[] nodesDepth, int node) {
        if(node==-1) return 0;
        if(nodesDepth[node]!=0) return nodesDepth[node];
        return nodesDepth[node] = 1 + getDepth(parent, nodesDepth, parent[node]);
    }
}