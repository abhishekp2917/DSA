import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;

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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> zigzagLevelOrder = new ArrayList<>();
        ArrayDeque<TreeNode> queue = new ArrayDeque<>(); 
        boolean reverse = false;

        if(root!=null) queue.add(root);

        while(!queue.isEmpty()) {
            List<Integer> currLevel = new ArrayList<>();
            int currLevelSize = queue.size();
            while(currLevelSize>0) {
                TreeNode node = (reverse)? queue.pollLast() : queue.pollFirst();
                currLevel.add(node.val);
                if(reverse) {
                    if(node.right!=null) queue.addFirst(node.right);
                    if(node.left!=null) queue.addFirst(node.left);
                }
                else {
                    if(node.left!=null) queue.addLast(node.left);
                    if(node.right!=null) queue.addLast(node.right);
                }
                currLevelSize--;
            }
            zigzagLevelOrder.add(currLevel);
            reverse = !reverse; 
        }
        return zigzagLevelOrder;
    }
}
