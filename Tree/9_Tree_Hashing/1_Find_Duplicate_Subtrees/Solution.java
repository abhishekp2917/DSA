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
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> duplicates = new ArrayList<>();
        Map<String, Integer> treeIdMap = new HashMap<>();
        Map<Integer, Integer> treeIdFreq = new HashMap<>();
        int[] id = new int[] { 1 };
        findDuplicates(root, treeIdMap, treeIdFreq, id, duplicates);
        return duplicates;
    }

    private int findDuplicates(TreeNode root, Map<String, Integer> treeIdMap, Map<Integer, Integer> treeIdFreq, int[] id, List<TreeNode> duplicates) {
        if(root==null) return 0;
        int leftId = findDuplicates(root.left, treeIdMap, treeIdFreq, id, duplicates);
        int rightId = findDuplicates(root.right, treeIdMap, treeIdFreq, id, duplicates);
        String treeHash = root.val + "," +  leftId + "," + rightId;  
        Integer treeId = treeIdMap.get(treeHash);
        if(treeId==null) {
            treeId = id[0]++;
            treeIdMap.put(treeHash, treeId);
        }
        if(treeIdFreq.getOrDefault(treeId, 0)==1) duplicates.add(root);
        treeIdFreq.put(treeId, treeIdFreq.getOrDefault(treeId, 0)+1); 
        return treeId;
    }
}