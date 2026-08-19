import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        int n = vals.length;
        int ans = n;

        // DSU is used to maintain connected components while we process
        // nodes in increasing order of their values.
        int[] parents = new int[n];

        // nodes[i] = {node, value}.
        // Sorting these pairs allows us to activate nodes from smaller
        // values to larger values.
        int[][] nodes = new int[n][2];

        // For every connected component, store:
        // value -> number of nodes having that value.
        //
        // We only need the frequency of the current value while merging
        // components, but keeping a map for each component makes the logic
        // straightforward.
        Map<Integer, Integer>[] componentNodeValFreqMap = new HashMap[n];

        List<Integer>[] adjList = getAdjList(n, edges);

        for(int i=0; i<n; i++) {
            nodes[i] = new int[] { i, vals[i] };
        }

        // Initially every node is its own component.
        for(int i=0; i<n; i++) {
            parents[i] = i;
        }

        // Initially each component contains exactly one node with its value.
        for(int i=0; i<n; i++) {
            componentNodeValFreqMap[i] = new HashMap<>();
            componentNodeValFreqMap[i].put(vals[i], 1);
        }

        // Process nodes from smaller value to larger value.
        Arrays.sort(nodes, (n1, n2) -> Integer.compare(n1[1], n2[1]));

        for(int i=0; i<n; i++) {
            int node = nodes[i][0];
            int val = nodes[i][1];

            // Only edges to nodes having value <= val can participate in
            // a good path whose maximum value is val.
            for(Integer adjNode : adjList[node]) {

                // This neighbor has a larger value, so this edge cannot
                // be used yet. It will be considered when that larger-valued
                // node becomes active.
                if(vals[adjNode]>val) continue;

                int nodeURoot = findRootNode(parents, adjNode);
                int nodeVRoot = findRootNode(parents, node);

                // The two nodes already belong to the same component,
                // so merging them cannot create any new good paths.
                if(nodeURoot==nodeVRoot) continue;

                // We are currently processing value = val.
                //
                // A new good path created by connecting these components
                // must have val at both endpoints, because all values
                // inside the active components are <= val.
                //
                // Therefore, every node with value val in component U
                // can be paired with every node with value val in component V.
                int freqU =
                    componentNodeValFreqMap[nodeURoot].getOrDefault(val, 0);

                int freqV =
                    componentNodeValFreqMap[nodeVRoot].getOrDefault(val, 0);

                // Every pair of val-valued nodes across the two components
                // creates one new good path.
                ans += freqU * freqV;

                // Merge the two connected components.
                parents[nodeURoot] = nodeVRoot;

                // After merging, the new component contains all val-valued
                // nodes from both components.
                componentNodeValFreqMap[nodeVRoot].put(
                    val,
                    freqU + freqV
                );
            }
        }

        return ans;
    }


    private int findRootNode(int[] parents, int node) {

        // A node whose parent is itself is the component representative.
        if(parents[node]==node) return node;

        // Path compression:
        // directly connect this node to the component root so future
        // find operations become faster.
        return parents[node] = findRootNode(parents, parents[node]);
    }


    private List<Integer>[] getAdjList(int n, int[][] edges) {
        List<Integer>[] adjList = new ArrayList[n];

        // Create an adjacency list because the graph is undirected.
        for(int i=0; i<n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for(int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
            adjList[edge[1]].add(edge[0]);
        }

        return adjList;
    }
}