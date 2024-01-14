import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class Node
{
    int data;
    Node left, right;

    Node(int item)
    {
        data = item;
        left = right = null;
    }
}

class Tree {
    
    public static ArrayList <Integer> diagonalSum(Node root) 
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        // map to store sum of nodes of each diagonal level
        HashMap<Integer, Integer> diagonalMap = new HashMap<Integer, Integer>();
        traverse(root, 0, 0, diagonalMap);
        
        // once found out the diagonal sum in map, transfer it to arraylist
        for(int i=0; i<diagonalMap.size(); i++) {
            result.add(diagonalMap.get(i));
        }
        
        return result;
    }
    
    // var 'x' and 'y' is to track horizontal and vertical position of each node resp.
    static void traverse(Node root, int x, int y, HashMap<Integer, Integer> diagonalMap) {
        if(root==null) return;
        
        // subtraction of horizontal from vertical will result the diagonal level of each node
        int idx = (int)Math.abs(y-x)/2;
        
        // once we get the diagonal level of particular node, if that level exists in map then add root data to that level value
        // else append a new value in the map
        if(diagonalMap.containsKey(idx)) diagonalMap.put(idx, diagonalMap.get(idx) + root.data);
        else diagonalMap.put(idx, root.data);
        
        // while traversing left, decreament 'x' by 1 and for right increament it by 1 
        // and for 'y' increament by 1 in either of the case
        traverse(root.left, x-1, y+1, diagonalMap);
        traverse(root.right, x+1, y+1, diagonalMap);
    } 
}