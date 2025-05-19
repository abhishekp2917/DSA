import java.util.PriorityQueue;

class Solution {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        int minCost = 0;
        boolean[] visited = new boolean[n];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((edge1, edge2) -> Integer.compare(edge1[1], edge2[1]));
        minHeap.add(new int[] {0, 0});
        while(!minHeap.isEmpty()) {
            int currNode = minHeap.peek()[0];
            int cost = minHeap.peek()[1];
            minHeap.poll();
            if(!visited[currNode]) {
                visited[currNode] = true;
                minCost += cost;
                for(int neighbourNode=0; neighbourNode<n; neighbourNode++) {
                    if(visited[neighbourNode]) continue;
                    int dist = getManhattanDistance(points[currNode], points[neighbourNode]);
                    minHeap.add(new int[] {neighbourNode, dist});
                }
            }
        }
        return minCost;
    }

    private int getManhattanDistance(int[] point1, int[] point2) {
        int x1 = point1[0], y1 = point1[1];
        int x2 = point2[0], y2 = point2[1];
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}