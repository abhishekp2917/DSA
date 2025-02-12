import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int n = numCourses;
        int[] nodeInDegree = new int[n];
        List<List<Integer>> adjList = getAdjListAndNodeInDegree(n, prerequisites, nodeInDegree);
        Queue<Integer> queue = new LinkedList<>();
        int processedNodeCount = 0;
        // add all nodes with in-degree 0 to the queue to start with
        for(int i=0; i<n; i++) {
            if(nodeInDegree[i]==0) queue.add(i);
        }
        // process all nodes with in-degree 0
        while(!queue.isEmpty()) {
            int node = queue.poll();
            // reduce in-degree of all neighbours of the node by 1
            adjList.get(node).stream().forEach(neighbour -> {
                // if in-degree of a neighbour becomes 0, add it to the queue
                if(--nodeInDegree[neighbour]==0) {
                    queue.add(neighbour);
                } 
            });
            // increment processed node count
            processedNodeCount++;
        }
        // if all nodes are processed, return true indicating that there is no cycle and all courses can be completed
        // else return false indicating that there is a cycle and all courses cannot be completed
        return processedNodeCount==n;
    }

    // Returns adjacency list and node In-degree
    private List<List<Integer>> getAdjListAndNodeInDegree(int n, int[][] prerequisites, int[] nodeInDegree) {
        List<List<Integer>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }
        for(int i=0; i<prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];
            adjList.get(v).add(u);
            nodeInDegree[u]++;
        }
        return adjList;
    }
}