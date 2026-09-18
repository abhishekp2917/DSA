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

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        Map<Integer, Integer> inorderIdxMap = new HashMap<>();
        for(int i=0; i<n; i++) {
            inorderIdxMap.put(inorder[i], i);
        }   
        return buildTree(preorder, inorderIdxMap, 0, n-1);
    }

    private TreeNode buildTree(int[] preorder, Map<Integer, Integer> inorderIdxMap, int start, int end) {
        if(start>end) return null;
        int val = preorder[this.preorderIdx];
        int inorderIdx = inorderIdxMap.get(val);
        TreeNode node = new TreeNode(val);
        this.preorderIdx++;
        node.left = buildTree(preorder, inorderIdxMap, start, inorderIdx-1);
        node.right = buildTree(preorder, inorderIdxMap, inorderIdx+1, end);
        return node;
    }
}

