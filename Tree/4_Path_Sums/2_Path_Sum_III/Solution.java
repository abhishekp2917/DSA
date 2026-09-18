import java.util.HashMap;
import java.util.Map;

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

    private int pathCount;

    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> pathSumFreq = new HashMap<>();
        pathSumFreq.put(0L, 1);
        getPathSumCount(root, 0, targetSum, pathSumFreq);
        return this.pathCount;
    }

    private void getPathSumCount(TreeNode root, long currPathSum, int targetSum, Map<Long, Integer> pathSumFreq) {
        if(root==null) return;
        currPathSum += root.val;
        this.pathCount += pathSumFreq.getOrDefault(currPathSum-targetSum, 0);
        pathSumFreq.put(currPathSum, pathSumFreq.getOrDefault(currPathSum, 0)+1);
        getPathSumCount(root.left, currPathSum, targetSum, pathSumFreq);
        getPathSumCount(root.right, currPathSum, targetSum, pathSumFreq);
        pathSumFreq.put(currPathSum, pathSumFreq.get(currPathSum)-1);
    }
}