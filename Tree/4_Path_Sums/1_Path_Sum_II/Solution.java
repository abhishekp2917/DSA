import java.util.ArrayList;
import java.util.List;

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
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> paths = new ArrayList<>();
        getPaths(root, targetSum, new ArrayList<>(), paths);
        return paths;
    }

    private void getPaths(TreeNode root, int targetSum, List<Integer> currPath, List<List<Integer>> paths) {
        if(root==null) return;
        currPath.add(root.val);
        if(root.left==null && root.right==null && targetSum-root.val==0) {
            paths.add(new ArrayList<>(currPath));
        }
        getPaths(root.left, targetSum-root.val, currPath, paths);
        getPaths(root.right, targetSum-root.val, currPath, paths);
        currPath.remove(currPath.size()-1);
    }
}