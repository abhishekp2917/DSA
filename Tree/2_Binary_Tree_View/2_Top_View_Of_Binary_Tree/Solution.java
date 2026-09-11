import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Node {
    int data;
    Node left, right;

    Node(int val) {
        this.data = val;
        this.left = null;
        this.right = null;
    }
}

class Solution {
    
    int leftMostCol = 0;
    int rightMostCol = 0;
    
    public ArrayList<Integer> topView(Node root) {
        ArrayList<Integer> topView = new ArrayList<>();
        Map<Integer, int[]> map = new HashMap<>();
        recursion(root, 0, 0, map);
        for(int col=this.leftMostCol; col<=this.rightMostCol; col++) {
            topView.add(map.get(col)[0]);
        }
        return topView;
    }
    
    private void recursion(Node root, int row, int col, Map<Integer, int[]> map) {
        if(root==null) return;
        if(!map.containsKey(col) || map.get(col)[1]>row) {
            map.put(col, new int[] { root.data, row });
        }
        this.leftMostCol = Math.min(leftMostCol, col);
        this.rightMostCol = Math.max(rightMostCol, col);
        recursion(root.left, row+1, col-1, map);
        recursion(root.right, row+1, col+1, map);
    }
}

