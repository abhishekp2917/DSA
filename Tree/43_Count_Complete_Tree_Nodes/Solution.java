import java.util.ArrayList;
import java.util.HashMap;

// bottom view 
class Solution1
{
    int minX = 0, maxX = 0;
    
    //Function to return a list containing the bottom view of the given tree.
    public ArrayList <Integer> bottomView(Node root)
    {
        // create a map to store the list of bottom most nodes of each x co-rdinates and traverse the tree to fill the map 
        HashMap<Integer, Integer[]> map = new HashMap<Integer, Integer[]>();
        traverse(root, 0, 0, map);
        ArrayList<Integer> bottomViewList = new ArrayList<Integer>();
        // since we have the leftmost and righmost x cordinate of bottom most nodes, run a loop from left to right
        // and store the map values in the answer list
        while(minX<=maxX) {
            if(map.containsKey(minX)) bottomViewList.add(map.get(minX)[0]);
            minX++;
        }
        return bottomViewList; 
    }
    
    // store the bottom view as we traverse the tree
    // variable x and y is to track the horixontal and vertical position of a particular node
    void traverse(Node root, int x, int y, HashMap<Integer, Integer[]> map) {
       if(root==null) return;
       // since the bottom view will cover bottom nodes in horizontal manner from left to right, we have to keep track of the
       // min and max x co-ordinate 
       minX = Math.min(minX, x);
       maxX = Math.max(maxX, x);
       // we will traverse the tree and if a particular x co-ordinate is not present in the map, then store the node along with it's vertical level in the map
       // if it already exist, then compare the vertical level of current node with the existing node and consider the node as answer which is deeper than the other
       if(!map.containsKey(x)) {
           map.put(x, new Integer[] {root.data, y});
       }
       else {
           int tempLevel = map.get(x)[1];
           if(tempLevel<=y)map.put(x, new Integer[]{root.data, y});
       }
       // traverse left and right subtree resp with the updated x and y co-ordinate values
       traverse(root.left, x-1, y+1, map);
       traverse(root.right, x+1, y+1, map);
    }
}  


// top view
class Solution2
{
    static int minX = 0, maxX = 0;
    //Function to return a list of nodes visible from the top view from left to right in Binary Tree.
    static ArrayList<Integer> topView(Node root)
    {
        // create a map to store the list of top most nodes of each x co-rdinates and traverse the tree to fill the map 
        HashMap<Integer, Integer[]> map = new HashMap<Integer, Integer[]>();
        traverse(root, 0, 0, map);
        ArrayList<Integer> topViewList = new ArrayList<Integer>();
        // since we have the leftmost and righmost x cordinate of top most nodes, run a loop from left to right
        // and store the map values in the answer list
        while(minX<=maxX) {
            if(map.containsKey(minX)) topViewList.add(map.get(minX)[0]);
            minX++;
        }
        return topViewList; 
    }
    
    // store the top view as we traverse the tree
    // variable x and y is to track the horixontal and vertical position of a particular node
    static void traverse(Node root, int x, int y, HashMap<Integer, Integer[]> map) {
       if(root==null) return;
       // since the top view will cover top nodes in horizontal manner from left to right, we have to keep track of the
       // min and max x co-ordinate 
       minX = Math.min(minX, x);
       maxX = Math.max(maxX, x);
       // we will traverse the tree and if a particular x co-ordinate is not present in the map, then store the node along with it's vertical level in the map
       // if it already exist, then compare the vertical level of current node with the existing node and consider the node as answer which is above the other
       if(!map.containsKey(x)) {
           map.put(x, new Integer[] {root.data, y});
       }
       else {
           int tempLevel = map.get(x)[1];
           if(tempLevel>y)map.put(x, new Integer[]{root.data, y});
       }
       // traverse left and right subtree resp with the updated x and y co-ordinate values
       traverse(root.left, x-1, y+1, map);
       traverse(root.right, x+1, y+1, map);
    }
}