import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> roots = new ArrayList<>();
        Set<Integer> deletionSet = new HashSet<>();
        for(int node : to_delete) deletionSet.add(node);
        getRoots(root, deletionSet, roots, true);
        return roots;
    }

    private TreeNode getRoots(TreeNode root, Set<Integer> deletionSet, List<TreeNode> roots, boolean isPossRoot) {
        if(root==null) return null;
        boolean delete = deletionSet.contains(root.val);
        if(isPossRoot && !delete) {
            roots.add(root);
        }
        TreeNode left = getRoots(root.left, deletionSet, roots, delete);
        TreeNode right = getRoots(root.right, deletionSet, roots, delete);
        root.left = left;
        root.right = right;
        return (delete)? null : root;
    }
}