import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {

    public int shortestPath(int n, int[][] edges, String labels, int k) {

        int minWeight = Integer.MAX_VALUE;

        // Build adjacency list:
        //
        // edge:
        // currentNode -> {nextNode, edgeWeight}
        List<List<int[]>> adjList = getAdjList(n, edges);

        // minWeights[node][sameCharCount]
        //
        // stores minimum weight required to reach "node"
        // where current consecutive equal-character streak
        // has length "sameCharCount".
        //
        // WHY include sameCharCount in state?
        //
        // Reaching the same node with different streak lengths
        // affects whether future moves are valid.
        //
        // Therefore:
        // node alone cannot represent the complete state.
        int[][] minWeights = new int[n][k+1];

        for(int node=0; node<n; node++) {
            Arrays.fill(minWeights[node], Integer.MAX_VALUE);
        }

        // Start from node 0.
        //
        // Initial streak length is 1
        // because first node itself starts a sequence.
        minWeights[0][1] = 0;

        // Min Heap stores:
        //
        // {node, totalWeight, currentStreakLength}
        //
        // Ordered by minimum path weight.
        PriorityQueue<int[]> minHeap =
            new PriorityQueue<>((a, b) -> a[1]-b[1]);

        minHeap.add(new int[] {0, 0, 1});

        while(!minHeap.isEmpty()) {

            int node = minHeap.peek()[0];

            int weight = minHeap.peek()[1];

            int charCount = minHeap.peek()[2];

            minHeap.poll();

            // Ignore stale heap entries.
            //
            // A better path to the same
            // (node, streakLength)
            // has already been discovered.
            if(minWeights[node][charCount]!=weight) continue;

            // Explore every outgoing edge.
            for(int[] neighbour : adjList.get(node)) {

                int neighbourNode = neighbour[0];

                int edgeWeight = neighbour[1];

                // If adjacent labels match,
                // consecutive streak increases.
                //
                // Otherwise streak restarts from 1.
                int neighbourCharCount =
                    (labels.charAt(node)==labels.charAt(neighbourNode))
                    ? charCount+1
                    : 1;

                // Constraint violated.
                if(neighbourCharCount>k) continue;

                int prevNeighbourWeight =
                    minWeights[neighbourNode][neighbourCharCount];

                int newNeighbourWeight =
                    weight + edgeWeight;

                // Standard Dijkstra relaxation.
                if(newNeighbourWeight<prevNeighbourWeight) {

                    minWeights[neighbourNode][neighbourCharCount] =
                        newNeighbourWeight;

                    minHeap.add(
                        new int[] {
                            neighbourNode,
                            newNeighbourWeight,
                            neighbourCharCount
                        }
                    );
                }
            }
        }

        // Destination may be reached
        // with different valid streak lengths.
        //
        // Choose minimum among all.
        for(int i=1; i<=k; i++) {

            minWeight =
                Math.min(minWeight, minWeights[n-1][i]);
        }

        return (minWeight!=Integer.MAX_VALUE)
                ? minWeight
                : -1;
    }

    private List<List<int[]>> getAdjList(int n, int[][] edges) {

        List<List<int[]>> adjList = new ArrayList<>();

        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }

        // Directed graph:
        // from -> {to, weight}
        for(int i=0; i<edges.length; i++) {

            adjList.get(edges[i][0]).add(
                new int[]{
                    edges[i][1],
                    edges[i][2]
                }
            );
        }

        return adjList;
    }
}