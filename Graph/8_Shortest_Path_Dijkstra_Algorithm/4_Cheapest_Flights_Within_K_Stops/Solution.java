import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        int minPrice = Integer.MAX_VALUE;

        // Build adjacency list:
        //
        // edge:
        // currentNode -> {nextNode, flightCost}
        List<List<int[]>> adjList = getAdjList(n, flights);

        // minPrices[node][flightsUsed]
        //
        // stores minimum cost to reach "node"
        // using exactly "flightsUsed" flights.
        //
        // WHY store flightsUsed separately?
        //
        // Reaching same node with fewer flights
        // is not always better.
        //
        // A slightly costlier path with fewer flights
        // may later produce a cheaper final answer.
        //
        // Therefore:
        // node alone cannot represent state.
        int[][] minPrices = new int[n][k+2];

        for(int node=0; node<n; node++) {
            Arrays.fill(minPrices[node], Integer.MAX_VALUE);
        }

        // Source reached with:
        // cost = 0
        // flights used = 0
        minPrices[src][0] = 0;

        // Min Heap stores:
        //
        // {node, totalCost, flightsUsed}
        //
        // Ordered by minimum total cost first,
        // exactly like Dijkstra.
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1]-b[1]);

        minHeap.add(new int[] {src, 0, 0});

        while(!minHeap.isEmpty()) {
            int node = minHeap.peek()[0];
            int price = minHeap.peek()[1];
            int stops = minHeap.peek()[2];
            minHeap.poll();

            // Ignore stale heap entries.
            //
            // A cheaper path for same
            // (node, flightsUsed)
            // has already been processed.
            if(minPrices[node][stops]!=price) continue;

            // Try every outgoing flight.
            for(int[] neighbour : adjList.get(node)) {
                int neighbourNode = neighbour[0];
                int flightPrice = neighbour[1];
                int neighbourStops = stops+1;

                // Maximum allowed flights:
                //
                // k stops means:
                // k+1 flights.
                if(neighbourStops>k+1) continue;
                int prevNeighbourPrice = minPrices[neighbourNode][neighbourStops];
                int newNeighbourPrice = price + flightPrice;

                // Standard Dijkstra relaxation.
                if(newNeighbourPrice<prevNeighbourPrice) {
                    minPrices[neighbourNode][neighbourStops] = newNeighbourPrice;
                    minHeap.add(
                        new int[] {
                            neighbourNode,
                            newNeighbourPrice,
                            neighbourStops
                        }
                    );
                }
            }
        }

        // Destination may be reached
        // using different valid flight counts.
        //
        // Choose minimum among all.
        for(int i=0; i<=k+1; i++) {
            minPrice = Math.min(minPrice, minPrices[dst][i]);
        }

        return (minPrice!=Integer.MAX_VALUE)? minPrice : -1;
    }

    private List<List<int[]>> getAdjList(int n, int[][] flights) {
        List<List<int[]>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }
        // Directed graph:
        // from -> {to, price}
        for(int i=0; i<flights.length; i++) {
            adjList.get(flights[i][0]).add(
                new int[]{
                    flights[i][1],
                    flights[i][2]
                }
            );
        }
        return adjList;
    }
}