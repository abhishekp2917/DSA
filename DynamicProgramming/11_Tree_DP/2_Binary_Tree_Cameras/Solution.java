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
    public int minCameraCover(TreeNode root) {
        int[] minCameraRequired = getMinCameraRequired(root);
        return Math.min(minCameraRequired[0], minCameraRequired[1]);
    }

    private int[] getMinCameraRequired(TreeNode root) {
        if(root==null) return new int[] {
            Integer.MAX_VALUE/2,
            0,
            Integer.MAX_VALUE/2
        };
        int[] left = getMinCameraRequired(root.left);
        int[] right = getMinCameraRequired(root.right);
        // 1. place one camera at root
        //    camera required =  1 (for root) + min camera req to cover both child tree by any way possible
        int placeAtRoot = 
                    1 + 
                    Math.min(left[0], Math.min(left[1], left[2])) +
                    Math.min(right[0], Math.min(right[1], right[2]));
        // 2. skip root but root should be covered
        // camera required = Math.min(
        //                      camera req to cover left subtree with camera at subtree root +
        //                      min camera req to cover only right subtree (no need to cover for parent),
        //                      camera req to cover right subtree with camera at subtree root +
        //                      min camera req to cover only left subtree (no need to cover for parent),
        //
        //                   )
        int skipAtRoot = Math.min(
                    left[0] + Math.min(right[0], right[1]), 
                    right[0] + Math.min(left[0], left[1]));
        // 3. skip root and don't cover it
        // camera required = camera req to cover left and right subtree by skipping their root 
        // we skipped left and right subtree root so that main root is not covered by any chance by them
        int rootUncovered = left[1] + right[1];
        return new int[] { placeAtRoot, skipAtRoot, rootUncovered }; 
    }
}