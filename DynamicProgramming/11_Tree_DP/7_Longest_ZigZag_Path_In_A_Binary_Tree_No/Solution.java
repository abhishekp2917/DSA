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

    private int longestLength;

    public int longestZigZag(TreeNode root) {
        getLongestZigZg(root);
        return this.longestLength;
    }

    private int[] getLongestZigZg(TreeNode root) {
        if(root==null) return new int[] {-1, -1};
        int[] leftLength = getLongestZigZg(root.left);
        int[] rightLength = getLongestZigZg(root.right);
        int leftRightZigZagLength = 1 + leftLength[1];
        int rightLeftZigZagLength = 1 + rightLength[0];
        this.longestLength = Math.max(
            longestLength,
            Math.max(
                leftRightZigZagLength, 
                rightLeftZigZagLength
            )
        );
        return new int[] { leftRightZigZagLength, rightLeftZigZagLength};
    }
}