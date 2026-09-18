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

    private int postorderIdx;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = postorder.length;
        this.postorderIdx = n-1;
        Map<Integer, Integer> inorderIdxMap = new HashMap<>();
        for(int i=0; i<n; i++) {
            inorderIdxMap.put(inorder[i], i);
        }   
        return buildTree(postorder, inorderIdxMap, 0, n-1);
    }

    private TreeNode buildTree(int[] postorder, Map<Integer, Integer> inorderIdxMap, int start, int end) {
        if(start>end) return null;
        int val = postorder[this.postorderIdx];
        int inorderIdx = inorderIdxMap.get(val);
        TreeNode node = new TreeNode(val);
        this.postorderIdx--;
        node.right = buildTree(postorder, inorderIdxMap, inorderIdx+1, end);
        node.left = buildTree(postorder, inorderIdxMap, start, inorderIdx-1);
        return node;
    }
}