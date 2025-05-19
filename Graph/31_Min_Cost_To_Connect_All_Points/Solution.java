import java.util.PriorityQueue;

class Solution {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;          // Total number of points/nodes in the graph
        int minCost = 0;                // To store the total minimum cost
        boolean[] visited = new boolean[n];  // To keep track of visited points

        // Min-heap (priority queue) to store edges as {nodeIndex, cost}
        // It gives the edge with the least cost first
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
            (edge1, edge2) -> Integer.compare(edge1[1], edge2[1])
        );

        // Start from point 0 with cost 0
        minHeap.add(new int[] {0, 0});

        // Prim’s Algorithm: Build MST by always choosing the minimum cost edge
        while (!minHeap.isEmpty()) {
            // Get the edge with the smallest cost
            int currNode = minHeap.peek()[0];
            int cost = minHeap.peek()[1];
            minHeap.poll();

            // Skip if target node is already added to MST
            if (!visited[currNode]) {
                visited[currNode] = true;  // Mark node as visited (added to MST)
                minCost += cost;          // Add edge cost to total MST cost

                // For all other nodes, compute the edge cost (Manhattan distance)
                // and add them to the min-heap (if not yet visited)
                for (int neighbourNode = 0; neighbourNode < n; neighbourNode++) {
                    if (!visited[neighbourNode]) {
                        int dist = getManhattanDistance(points[currNode], points[neighbourNode]);
                        minHeap.add(new int[] {neighbourNode, dist});
                    }
                }
            }
        }

        return minCost;
    }

    // Helper method to compute Manhattan distance between two points
    // Manhattan Distance = |x1 - x2| + |y1 - y2|
    private int getManhattanDistance(int[] point1, int[] point2) {
        int x1 = point1[0], y1 = point1[1];
        int x2 = point2[0], y2 = point2[1];
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
