import java.util.*;

class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;

        // List to store all eventually safe nodes
        List<Integer> safeNodes = new ArrayList<>();

        // inDegree[i] represents the number of outgoing edges from node i in the original graph.
        // But since we reverse the graph, it becomes the number of incoming edges in the reversed graph.
        int[] inDegree = new int[n];

        // reverseGraph[i] contains a list of nodes that point to i in the original graph.
        // In other words, for each edge u -> v in the original graph, we add v -> u in reverseGraph.
        List<Integer>[] reverseGraph = new ArrayList[n];

        // Queue for BFS-based topological sort (Kahn's algorithm)
        Queue<Integer> queue = new LinkedList<>();

        // Initialize reverseGraph with empty lists for each node
        for (int i = 0; i < n; i++) {
            reverseGraph[i] = new ArrayList<>();
        }

        // Build the reverse graph and compute in-degree for each node
        for (int node = 0; node < n; node++) {
            for (int neighbour : graph[node]) {
                // Reverse the edge: instead of node -> neighbour, add neighbour -> node
                reverseGraph[neighbour].add(node);
                // Since 'node' has an outgoing edge to 'neighbour' in the original graph,
                // increase out-degree (here, treated as in-degree in reversed graph)
                inDegree[node]++;
            }
        }

        // A terminal nodes in the original graph has 0 outgoing edge
        // but in reversed graph, a terminal node will have 0 incoming edge
        for (int i = 0; i < n; i++) {
            // if a node has 0 incoming edge, it is a terminal node
            if (inDegree[i] == 0) {
                // since terminal nodes are always safe, add it to the final result
                queue.add(i);
                safeNodes.add(i);
            }
        }

        // we would start from the terminal node
        // since we are starting BFS from terminal node, if any node in-degree becomes zero, then it means it's all the outgoing edge in original graph leads to terminal node
        while (!queue.isEmpty()) {
            int node = queue.poll();

            // Go through all nodes that originally had an edge to this node
            for (int neighbour : reverseGraph[node]) {
                // Reduce the out-degree (in reversed graph it's in-degree)
                inDegree[neighbour]--;

                // If in-degree becomes zero, it means all its outgoing paths lead to safe nodes
                // that means it is a safe node, so add it into the queue to process 
                if (inDegree[neighbour] == 0) {
                    queue.add(neighbour);
                    safeNodes.add(neighbour);
                }
            }
        }

        // Sort the result to return nodes in increasing order as required
        Collections.sort(safeNodes);
        return safeNodes;
    }
}
