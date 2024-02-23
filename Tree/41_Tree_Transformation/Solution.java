import java.util.HashMap;

class Solution {
    public static int solve(int N, int[] p) {
        // declaring hashmap to store the intermidiary node (non-leaf node) mapped to it's children count
        HashMap<Integer, Integer> map = new HashMap<>();
        // variable to store the root of the tree
        int root = -1;
        for(int i=0; i<N; i++) {
            // if any index value is -1, then that index will be root node
            // since -1 is not any actual node, no need to consider it in map
            if(p[i]==-1) {
                root = i;
                continue; 
            }
            map.put(p[i], map.getOrDefault(p[i], 0)+1);
        }
        // to form star, there should be only one intermidiary node
        // if there is no intermidiary node, return 0
        if(map.size()==0) return 0;
        // if root node has more than one child, root node will be one of the intermidiary node
        // so return (number of intermidiary nodes - 1)
        else if(map.get(root)>1) return Math.max(map.size()-1, 0);
        // if root has only one child, the root node will be treated as leaf node
        // so the intermidiary node count will reduce by 1
        // return (number of intermidiary nodes - 2)
        else return Math.max(map.size()-2, 0);
    }
}
    