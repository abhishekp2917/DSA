import java.util.List;
import java.util.PriorityQueue;

class Solution {
    static int spanningTree(int V, int E, List<List<int[]>> adj) {
        // array to keep track of nodes that are already visited
        boolean[] visited = new boolean[V];
        int minCost = 0;
        // min heap which will store the edges where top of the heap will have the edge with lowest cost
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((node1, node2) -> Integer.compare(node1[1], node2[1]));
        minHeap.add(new int[] {0, 0});
        while(!minHeap.isEmpty()) {
            // poll the edge wich has lowest edge weight 
            int currNode = minHeap.peek()[0];
            int currCost = minHeap.peek()[1];
            minHeap.poll();
            // if the node is not visited then add the weight of the edge to the minCost 
            // and add all its neighbour nodes along with the weight to the min heap.
            if(!visited[currNode]) {
                visited[currNode] = true;
                minCost += currCost;
                adj.get(currNode).stream().forEach(neighbour -> {
                    int neighbourNode = neighbour[0];
                    int neighbourCost = neighbour[1];
                    minHeap.add(new int[] {neighbourNode, neighbourCost});
                });
            }
        }
        return minCost;
    }
}