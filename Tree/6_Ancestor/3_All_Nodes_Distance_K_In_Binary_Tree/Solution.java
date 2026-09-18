import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> nodesAtK = new ArrayList<>();
        Map<TreeNode, TreeNode> parentNodeMap = new HashMap<>();
        getParentNodeMapping(root, parentNodeMap);
        getNodesAtDistanceK(target, null, parentNodeMap, k, nodesAtK);
        return nodesAtK;
    }

    private void getNodesAtDistanceK(TreeNode root, TreeNode prev, Map<TreeNode, TreeNode> parentNodeMap, int k, List<Integer> nodesAtK) {
        if(root==null) return;
        if(k==0) {
            nodesAtK.add(root.val);
            return;
        }
        if(root.left!=prev) getNodesAtDistanceK(root.left, root, parentNodeMap, k-1, nodesAtK);
        if(root.right!=prev) getNodesAtDistanceK(root.right, root, parentNodeMap, k-1, nodesAtK);
        if(parentNodeMap.get(root)!=prev) getNodesAtDistanceK(parentNodeMap.get(root), root, parentNodeMap, k-1, nodesAtK);
    }

    private void getParentNodeMapping(TreeNode root, Map<TreeNode, TreeNode> parentNodeMap) {
        if(root==null) return;
        if(root.left!=null) parentNodeMap.put(root.left, root);
        if(root.right!=null) parentNodeMap.put(root.right, root);
        getParentNodeMapping(root.left, parentNodeMap);
        getParentNodeMapping(root.right, parentNodeMap);
    }
}