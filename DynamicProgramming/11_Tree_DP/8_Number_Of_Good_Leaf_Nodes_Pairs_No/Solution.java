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

    private int pairsCount;

    public int countPairs(TreeNode root, int distance) {
        countLeafDistances(root, distance);
        return pairsCount;
    }

    private int[] countLeafDistances(TreeNode root, int distance) {
        int[] leafDistFreq = new int[distance + 1];

        if (root == null) {
            return leafDistFreq;
        }

        if (root.left == null && root.right == null) {
            leafDistFreq[1] = 1;
            return leafDistFreq;
        }

        int[] leftDistFreq = countLeafDistances(root.left, distance);
        int[] rightDistFreq = countLeafDistances(root.right, distance);

        // Prefix sum of right subtree leaf distances.
        int[] rightPrefix = new int[distance + 1];

        for (int dist = 1; dist <= distance; dist++) {
            rightPrefix[dist] = rightPrefix[dist - 1] + rightDistFreq[dist];
        }

        // Count leaf pairs whose path passes through the current node.
        for (int leftDist = 1; leftDist < distance; leftDist++) {
            int maxRightDist = distance - leftDist;
            pairsCount += leftDistFreq[leftDist] * rightPrefix[maxRightDist];
        }

        // Move every leaf one level farther from the parent.
        for (int dist = 1; dist <= distance; dist++) {
            leafDistFreq[dist] =
                leftDistFreq[dist - 1] + rightDistFreq[dist - 1];
        }

        return leafDistFreq;
    }
}