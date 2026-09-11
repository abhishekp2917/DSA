import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
    
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levelOrder = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>(); 
        if(root!=null) queue.add(root);
        while(!queue.isEmpty()) {
            List<Integer> currLevel = new ArrayList<>();
            int currLevelSize = queue.size();
            while(currLevelSize>0) {
                TreeNode node = queue.poll();
                currLevel.add(node.val);
                if(node.left!=null) queue.add(node.left);
                if(node.right!=null) queue.add(node.right);
                currLevelSize--;
            }
            levelOrder.add(currLevel);
        }
        return levelOrder;
    }
}
