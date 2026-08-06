import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {

    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        int largestColorValue = 0;

        // memo[node][color]:
        // stores the maximum frequency of every color
        // among all paths starting from this node.
        //
        // memo[node] itself represents a frequency array
        // of size 26 corresponding to characters 'a' to 'z'.
        //
        // Once computed for a node,
        // every parent can directly reuse it.
        Integer[][] memo = new Integer[n][26];

        // Used for Kahn's Algorithm
        // to detect whether the graph contains a cycle.
        //
        // DP below works only on a DAG.
        int[] inDegree = new int[n];

        // Build adjacency list.
        List<Integer>[] adjList = new ArrayList[n];
        for(int i=0; i<n; i++) adjList[i] = new ArrayList<>();

        for(int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
            inDegree[edge[1]]++;
        }

        // If graph contains a cycle,
        // path length can become infinite,
        // so answer is defined as -1.
        if(hasCycle(adjList, inDegree)) return -1;

        // Every node can be the starting node
        // of the optimal path.
        for(int node=0; node<n; node++) {

            Integer[] colorFreq = recursion(colors, adjList, node, memo);

            // Find the maximum color frequency
            // among all colors for this starting node.
            for(int i=0; i<26; i++) {
                largestColorValue = Math.max(largestColorValue, colorFreq[i]);
            }
        }

        return largestColorValue;
    }

    private Integer[] recursion(String colors, List<Integer>[] adjList, int start, Integer[][] memo) {

        int colorIdx = colors.charAt(start)-'a';

        // If current node has already been processed,
        // simply reuse its frequency array.
        //
        // Checking one color is sufficient because
        // the entire row is filled together.
        if(memo[start][colorIdx]!=null) return memo[start];

        // Initially every color frequency is zero.
        Arrays.fill(memo[start], 0);

        // Every outgoing edge represents
        // one possible continuation of the path.
        //
        // We keep the best frequency
        // obtained from any child.
        for(int next : adjList[start]) {

            Integer[] colorFreqFromNext = recursion(colors, adjList, next, memo);

            // Merge DP of current child.
            //
            // For every color,
            // keep the maximum count
            // obtainable through any child.
            for(int i=0; i<26; i++) {
                memo[start][i] = Math.max(memo[start][i], colorFreqFromNext[i]);
            }
        }

        // Finally include the current node itself.
        //
        // Current node contributes
        // exactly one occurrence
        // of its own color.
        memo[start][colorIdx]++;

        return memo[start];
    }

    private boolean hasCycle(List<Integer>[] adjList, int[] inDegree) {

        int n = inDegree.length;

        Queue<Integer> queue = new LinkedList<>();

        // Nodes having indegree zero
        // can be processed immediately.
        for(int node=0; node<n; node++) {
            if(inDegree[node]==0) queue.add(node);
        }

        // Standard Kahn's Topological Sort.
        while(!queue.isEmpty()) {

            int node = queue.poll();

            for(int next : adjList[node]) {

                // Removing current node
                // removes one incoming edge
                // for every neighbour.
                if(--inDegree[next]==0) queue.add(next);
            }
        }

        // If any node still has indegree,
        // it belongs to a cycle.
        for(int node=0; node<n; node++) {
            if(inDegree[node]!=0) return true;
        }

        return false;
    }
}