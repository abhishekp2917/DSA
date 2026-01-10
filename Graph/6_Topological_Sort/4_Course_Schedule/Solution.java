import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {

    public boolean canFinish(int numCourses, int[][] prerequisites) {

        int n = numCourses;

        // nodeDegree[i] represents the indegree of course i,
        // i.e., how many prerequisites course i has.
        int[] nodeDegree = new int[n];

        // Build the adjacency list and fill indegree array
        List<List<Integer>> adjList =
            getAdjListAndNodeDegree(n, prerequisites, nodeDegree);

        // Queue to process courses that currently have no prerequisites
        Queue<Integer> queue = new LinkedList<>();

        // Counts how many courses we are able to process
        int processedNodeCount = 0;

        // Initially, add all courses with indegree 0
        // These courses can be taken immediately
        for (int i = 0; i < n; i++) {
            if (nodeDegree[i] == 0) queue.add(i);
        }

        // Process courses level by level
        while (!queue.isEmpty()) {

            // Take one course that has no remaining prerequisites
            int node = queue.poll();

            // For every course that depends on the current course
            adjList.get(node).stream().forEach(neighbour -> {

                // Reduce indegree since one prerequisite is now completed
                if (--nodeDegree[neighbour] == 0) {

                    // If all prerequisites are satisfied,
                    // the course becomes available to take
                    queue.add(neighbour);
                }
            });

            // One course successfully processed
            processedNodeCount++;
        }

        // If we managed to process all courses,
        // then no cycle exists and all courses can be finished
        return processedNodeCount == n;
    }

    private List<List<Integer>> getAdjListAndNodeDegree(
        int n,
        int[][] prerequisites,
        int[] nodeDegree
    ) {

        // Initialize adjacency list
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        // Build graph:
        // If [u, v] is in prerequisites,
        // it means v must be taken before u.
        // So we add an edge v -> u
        // and increase indegree of u.
        for (int i = 0; i < prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];

            adjList.get(v).add(u);
            nodeDegree[u]++;
        }

        return adjList;
    }
}
