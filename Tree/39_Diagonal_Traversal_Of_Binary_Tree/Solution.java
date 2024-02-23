import java.util.ArrayList;
import java.util.HashMap;

class Node{
    int data;
    Node left;
    Node right;
    Node(int data){
        this.data = data;
        left=null;
        right=null;
    }
}


class Tree
{
    public ArrayList<Integer> diagonal(Node root)
    {
        // map to store the list of nodes of a each diagonal levels
        HashMap<Integer, ArrayList<Integer>> map = new  HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        // initially we will pass x=0 and  y=0 as root node is at [0, 0] co-ordinate
        traverse(root, 0, 0, map);
        // traverse map which has key as a diagonal levels from 0 to nth level
        for(int key : map.keySet()) {
            // get the list of nodes of ith level and insert it into the answer list
            ArrayList<Integer> tempList = map.get(key);
            for(int n : tempList) {
                list.add(n);
            }
        }
        return list;
    }
    
    // we are using x and y to track the horizontal and vertical position of the node 
    public void traverse(Node root, int x, int y, HashMap<Integer, ArrayList<Integer>> map) {
        if(root==null) return;
        // to get the diagonla level of the node, find the absolute diff of the x and y position of the node
        int key = Math.abs(y - x);
        // once we get the diagonal level, add the current node to that level list
        ArrayList<Integer> list = map.getOrDefault(key, new ArrayList<>());
        list.add(root.data);
        map.put(key, list);
        // traverse left and right by updating the x and y position of the left and right child
        traverse(root.left, x-1, y+1, map);
        traverse(root.right, x+1, y+1, map);
    }
}