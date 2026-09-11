import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> verticalTraversal = new ArrayList<>();
        Map<Integer, List<Integer>> columns = new HashMap<>();
        int leftMostCol = 0;
        int rightMostCol = 0;
        Queue<NodeWrapper> queue = new LinkedList<>();
        queue.add(new NodeWrapper(root, 0));
        while(!queue.isEmpty()) {
            Map<Integer, List<Integer>> currColumns = new HashMap<>();
            int queueSize = queue.size();
            while(queueSize>0) {
                TreeNode node = queue.peek().node;
                Integer col = queue.peek().col;
                queue.poll();
                leftMostCol = Math.min(leftMostCol, col);
                rightMostCol = Math.max(rightMostCol, col);
                List<Integer> currRows = currColumns.getOrDefault(col, new ArrayList<>());
                currRows.add(node.val);
                currColumns.put(col, currRows);
                if(node.left!=null) queue.add(new NodeWrapper(node.left, col-1));
                if(node.right!=null) queue.add(new NodeWrapper(node.right, col+1));
                queueSize--;
            }
            for(Integer col : currColumns.keySet()) {
                List<Integer> currRows = currColumns.get(col);
                Collections.sort(currRows);
                List<Integer> rows = columns.getOrDefault(col, new ArrayList<>());
                rows.addAll(currRows);
                columns.put(col, rows);
            }
        }
        for(int col=leftMostCol; col<=rightMostCol; col++) {
            List<Integer> rows = columns.get(col);
            if(rows==null) continue;
            verticalTraversal.add(rows);
        }
        return verticalTraversal;
    }
}

class NodeWrapper {
    
    TreeNode node;
    Integer col;
    NodeWrapper(TreeNode node, Integer col) {
        this.node = node;
        this.col = col;
    }
}