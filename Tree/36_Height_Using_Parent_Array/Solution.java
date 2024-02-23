import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution1 {
    int findHeight(int n, int arr[]){
        int max = -2;
        // iterate over each index as a child and call the find() method which will find the 
        // length from current node to the root node
        for(int i=0;i<n;i++){
            // whichever child will be the deepest node will give the height of the tree
            max= Math.max(max,find(arr,i));
        }
        return max;
    }
    
    int find(int[] arr,int i){
        // if parent node is -1, that means it's a root node, so return 1
        if(arr[i]==-1){
            return 1;
        }
        // else recursively call the parent function to reach to the root node
        return 1+find(arr,arr[i]);
    }
}


class Solution2 {
    static int findHeight(int N, int arr[]){
        // construct hashmap where key will be parent node and value will be their children
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for(int i=0; i<arr.length; i++) {
            List<Integer> children = map.getOrDefault(arr[i], new ArrayList<>());
            children.add(i);
            map.put(arr[i], children);
        }
        // start the traversal from root node i.e. -1
        return traverse(-1, map);
    }

    static int traverse(int root, HashMap<Integer, List<Integer>> map) {
        // if map doesn't have a root, that means it's a leaf node so return 0
        if(!map.containsKey(root)) return 0;
        List<Integer> children = map.get(root);
        int leftHeight = 0, rightHeight = 0;
        // traverse left child and right child if it exists and find their height
        leftHeight = traverse(children.get(0), map);
        if(children.size()>1) rightHeight = traverse(children.get(1), map);
        // return 1 (to include current root node) + max height of child node 
        return 1 + Math.max(leftHeight, rightHeight);
    }
}