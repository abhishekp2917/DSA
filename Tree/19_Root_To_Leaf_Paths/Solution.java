import java.util.ArrayList;

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

class Tree{
    
    public ArrayList<ArrayList<Integer>> Paths(Node root){
        // list to store all possible paths
        ArrayList<ArrayList<Integer>> possPathList = new ArrayList<ArrayList<Integer>>();
        
        // list to store root to leaf path temporarily 
        ArrayList<Integer> possPath = new ArrayList<Integer>();
        findPaths(root, possPath, possPathList);
        return possPathList;
    }
    
    void findPaths(Node root, ArrayList<Integer> possPath, ArrayList<ArrayList<Integer>> possPathList) {
        if(root==null) return;

        // keep adding node in temp list until we reach leaf node
        // once reached at leaf node, add the leaf node value to temp list and create a copy of that list 
        // add that copied list to possible paths final list
        // after adding since we are back tracking, remove that last node entered before back tracking
        if(root.left==null && root.right==null) {
            possPath.add(root.data);
            
            ArrayList<Integer> temp = new ArrayList<Integer>();
            
            for(Integer n : possPath) {
               temp.add(n); 
            }
            possPathList.add(temp);
            possPath.remove(possPath.size()-1);
            return;
        }

        // adding node to temp list 
        possPath.add(root.data);
        findPaths(root.left, possPath, possPathList);
        findPaths(root.right, possPath, possPathList);

        // before back tracking, removing the last node from the temp list
        possPath.remove(possPath.size()-1);
    }
    
} 