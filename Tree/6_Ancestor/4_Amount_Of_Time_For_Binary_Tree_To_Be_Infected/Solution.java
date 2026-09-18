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

    private TreeNode startNode;
    private Map<TreeNode, TreeNode> parentMap = new HashMap<>();

    public int amountOfTime(TreeNode root, int start) {
        getParentsMapping(root, start);
        return recursion(startNode, null)-1;
    }

    private int recursion(TreeNode root, TreeNode prev) {
        if(root==null) return 0;
        int left = (root.left!=prev)? recursion(root.left, root) : 0;
        int right = (root.right!=prev)? recursion(root.right, root) : 0;
        int parent = (parentMap.get(root)!=prev)? recursion(parentMap.get(root), root) : 0;
        return 1 + Math.max(parent, Math.max(left, right));
    }

    private void getParentsMapping(TreeNode root, int start) {
        if(root==null) return;
        if(root.val==start) this.startNode = root;
        if(root.left!=null) this.parentMap.put(root.left, root);
        if(root.right!=null) this.parentMap.put(root.right, root);
        getParentsMapping(root.left, start);
        getParentsMapping(root.right, start);
    }
}