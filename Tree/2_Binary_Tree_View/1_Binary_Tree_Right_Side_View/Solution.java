import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> rightSideView = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        recursion(root, 0, map);
        int row = 0;
        while(!map.isEmpty()) {
            rightSideView.add(map.get(row));
            map.remove(row);
            row++;
        }
        return rightSideView;
    }

    private void recursion(TreeNode root, int row, Map<Integer, Integer> map) {
        if(root==null) return;
        map.putIfAbsent(row, root.val);
        recursion(root.right, row+1, map);
        recursion(root.left, row+1, map);
    }
}

