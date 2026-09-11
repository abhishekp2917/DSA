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
    public int widthOfBinaryTree(TreeNode root) {
        int maxWidth = 0;
        ArrayDeque<TreeNodeWrapper> queue = new ArrayDeque<>(); 
        if(root!=null) queue.add(new TreeNodeWrapper(root, 1));
        while(!queue.isEmpty()) {
            int currLevelSize = queue.size();
            int leftMostIndex = queue.peekFirst().index;
            int rightMostIndex = queue.peekLast().index;
            maxWidth = Math.max(
                maxWidth,
                rightMostIndex-leftMostIndex+1
            );
            while(currLevelSize>0) {
                TreeNode node = queue.peekFirst().node;
                int index = queue.peekFirst().index;
                queue.pollFirst();
                int leftIndex = index*2;
                int rightIndex = index*2 + 1;
                if(node.left!=null) queue.add(new TreeNodeWrapper(node.left, leftIndex));
                if(node.right!=null) queue.add(new TreeNodeWrapper(node.right, rightIndex));
                currLevelSize--;
            }
        }
        return maxWidth;
    }
}

class TreeNodeWrapper {

    TreeNode node;
    int index;

    TreeNodeWrapper(TreeNode node, int index) {
        this.node = node;
        this.index = index;
    }    
}