import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {

    public int countRestrictedPaths(int n, int[][] edges) {

        // Build the weighted undirected graph.
        List<int[]>[] adjList = getAdjList(n, edges);

        // Compute the shortest distance
        // from every node to node n.
        //
        // This distance determines
        // whether moving from one node
        // to another is allowed
        // in a restricted path.
        int[] shortestDistance = getShortestDistanceFromLastNode(n, adjList);

        // memo[node]:
        // stores the number of restricted paths
        // from this node to node n.
        //
        // Since the answer for a node
        // never changes,
        // memoization avoids recomputing
        // the same subtree.
        Long[] memo = new Long[n+1];

        return (int)recursion(shortestDistance, adjList, 1, memo);
    }

    private long recursion(int[] shortestDistance, List<int[]>[] adjList, int source, Long[] memo) {

        final int MOD = 1000_000_007;

        int n = shortestDistance.length-1;

        // Reaching node n
        // completes one valid restricted path.
        if(source==n) {
            return 1;
        }

        // Return previously computed answer.
        if(memo[source]!=null) {
            return memo[source];
        }

        int distanceFromSource = shortestDistance[source];

        long count = 0;

        // Explore every neighbouring node.
        for(int[] next : adjList[source]) {

            int nextNode = next[0];

            int distanceFromNext = shortestDistance[nextNode];

            // Restricted path condition:
            //
            // Every move must strictly decrease
            // the shortest distance to node n.
            //
            // This guarantees
            // that every recursive call
            // moves closer to the destination,
            // preventing cycles.
            if(distanceFromNext < distanceFromSource) {

                count = (
                    count +
                    recursion(
                        shortestDistance,
                        adjList,
                        nextNode,
                        memo
                    )
                ) % MOD;
            }
        }

        memo[source] = count;

        return count;
    }

    private int[] getShortestDistanceFromLastNode(int n, List<int[]>[] adjList) {

        // shortestDistance[node]:
        // stores the shortest distance
        // from node n to this node.
        int[] shortestDistance = new int[n+1];

        Arrays.fill(shortestDistance, Integer.MAX_VALUE);

        shortestDistance[n] = 0;

        // Standard Dijkstra's algorithm.
        //
        // Heap stores:
        // {node, current shortest distance}
        PriorityQueue<int[]> minHeap =
            new PriorityQueue<>(
                (n1, n2) ->
                    Integer.compare(n1[1], n2[1])
            );

        minHeap.add(new int[] { n, 0 });

        while(!minHeap.isEmpty()) {

            int currNode = minHeap.peek()[0];

            int distFromLastNodeToCurr = minHeap.peek()[1];

            minHeap.poll();

            // Ignore stale heap entries.
            //
            // A shorter path
            // has already been discovered.
            if(distFromLastNodeToCurr!=shortestDistance[currNode]) {
                continue;
            }

            // Relax every outgoing edge.
            for(int[] next : adjList[currNode]) {

                int nextNode = next[0];

                int edgeWeight = next[1];

                int prevDistFromLastNodeToNext =
                    shortestDistance[nextNode];

                int newDistFromLastNodeToNext =
                    distFromLastNodeToCurr + edgeWeight;

                // Found a better path.
                if(newDistFromLastNodeToNext < prevDistFromLastNodeToNext) {

                    shortestDistance[nextNode] =
                        newDistFromLastNodeToNext;

                    minHeap.add(
                        new int[] {
                            nextNode,
                            newDistFromLastNodeToNext
                        }
                    );
                }
            }
        }

        return shortestDistance;
    }

    private List<int[]>[] getAdjList(int n, int[][] edges) {

        List<int[]>[] adjList = new ArrayList[n+1];

        for(int i=1; i<=n; i++) {
            adjList[i] = new ArrayList<>();
        }

        // Store:
        // {neighbour, edge weight}
        for(int[] edge : edges) {

            adjList[edge[0]].add(
                new int[] {
                    edge[1],
                    edge[2]
                }
            );

            adjList[edge[1]].add(
                new int[] {
                    edge[0],
                    edge[2]
                }
            );
        }

        return adjList;
    }
}