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

    private int preorderIdx = 0;

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        int n = preorder.length;
        Map<Integer, Integer> postorderIdxMap = new HashMap<>();
        for(int i=0; i<n; i++) {
            postorderIdxMap.put(postorder[i], i);
        } 
        return buildTree(preorder, n, postorderIdxMap);
    }

    private TreeNode buildTree(int[] preorder, int parentPostorderIdx, Map<Integer, Integer> postorderIdxMap) {
        if(this.preorderIdx>=preorder.length) return null;
        int val = preorder[this.preorderIdx];
        int postorderIdx = postorderIdxMap.get(val);
        if(postorderIdx>parentPostorderIdx) return null;
        TreeNode node = new TreeNode(val);
        this.preorderIdx++;
        node.left = buildTree(preorder, postorderIdx, postorderIdxMap);
        node.right = buildTree(preorder, postorderIdx, postorderIdxMap);
        return node;
    }
}