import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {

        // List to store the smallest set of vertices
        List<Integer> smallestSet = new ArrayList<>();

        // Array to keep track of whether a node has a parent or not
        int[] hasParent = new int[n];

        // iterate over the edges and update the hasParent array
        // the pair (a, b) in the edges array indicates that there is an edge from a to b
        // so, b has a parent
        for(List<Integer> pairs: edges) {

            // for all the b's in the pairs, update the hasParent array marking them as having a parent
            hasParent[pairs.get(1)]=1;
        }

        // after marking all the nodes that have a parent, remaining nodes are the one that can't be reached from any
        // other node since they don't have a parent
        for(int i=0; i<n; i++) {

            // add all the nodes that don't have a parent to the smallest set
            // these nodes are the smallest set of vertices that can reach all the nodes
            if(hasParent[i]==0) smallestSet.add(i);
        }
        return smallestSet;
    }
}