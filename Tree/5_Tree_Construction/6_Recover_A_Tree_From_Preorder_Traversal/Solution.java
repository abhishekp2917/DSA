import java.util.ArrayDeque;

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
    
    public TreeNode recoverFromPreorder(String traversal) {
        
        int n = traversal.length();
        ArrayDeque<TreeNodeWrapper> stack = new ArrayDeque<>();
        int index = 0;
        
        while(index<n) {
            int depth = 0;
            int val = 0;
            while(index<n && traversal.charAt(index)=='-') {
                depth++;
                index++;
            }
            while(index<n && traversal.charAt(index)!='-') {
                val = val*10 + (traversal.charAt(index)-'0');
                index++;
            }
            TreeNode node = new TreeNode(val);
            while(!stack.isEmpty() && stack.peekLast().depth>=depth) stack.pollLast();
            if(!stack.isEmpty()) {
                if(stack.peekLast().node.left==null) stack.peekLast().node.left = node;
                else stack.peekLast().node.right = node;
            }
            stack.addLast(new TreeNodeWrapper(node, depth));
        }
        return stack.peekFirst().node;
    }
}

class TreeNodeWrapper {
    
    TreeNode node;
    int depth;

    TreeNodeWrapper(TreeNode node, int depth) {
        this.node = node;
        this.depth = depth;
    }
}
