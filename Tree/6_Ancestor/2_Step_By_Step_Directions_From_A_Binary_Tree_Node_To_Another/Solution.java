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

    public String getDirections(TreeNode root, int startValue, int destValue) {
        TreeNode lca = findLCA(root, startValue, destValue);
        StringBuilder directions = new StringBuilder();
        findPath(lca, startValue, directions, true);
        findPath(lca, destValue, directions, false);
        return directions.toString();
    }

    private TreeNode findLCA(TreeNode root, int start, int dest) {
        if(root==null) return root;
        TreeNode left = findLCA(root.left, start, dest);
        TreeNode right = findLCA(root.right, start, dest);
        if(root.val==start || root.val==dest || (left!=null && right!=null)) return root;
        return (left!=null)? left : right;
    }

    private boolean findPath(TreeNode root, int target, StringBuilder path, boolean fromStart) {
        if(root==null) return false;
        if(root.val==target) return true;
        
        path.append((fromStart)? 'U' : 'L');
        if(findPath(root.left, target, path, fromStart)) return true;
        path.deleteCharAt(path.length() - 1);
        
        path.append((fromStart)? 'U' : 'R');
        if (findPath(root.right, target, path, fromStart)) return true;
        path.deleteCharAt(path.length() - 1);

        return false;
    }
}