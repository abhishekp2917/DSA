import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;

        // Generate all possible edges because this is a complete graph:
        // every point can be connected to every other point.
        List<int[]> edges = getEdges(points);

        // Kruskal's algorithm requires edges in increasing order of cost.
        Collections.sort(edges, (e1, e2) -> Integer.compare(e1[2], e2[2]));

        // DSU to efficiently detect whether adding an edge creates a cycle.
        int[] parents = new int[n];
        int[] sizes = new int[n];

        for(int i=0; i<n; i++) parents[i] = i;
        Arrays.fill(sizes, 1);

        int connectedEdges = 0;
        int minConnectionCost = 0;

        // Process edges from cheapest to most expensive.
        for(int[] edge : edges) {
            int p1 = edge[0];
            int p2 = edge[1];
            int cost = edge[2];

            int p1Root = findRootNode(p1, parents);
            int p2Root = findRootNode(p2, parents);

            // Both points are already connected, so adding this edge
            // would create a cycle and add redundant cost. Skip it.
            if(p1Root==p2Root) continue;

            // Connect the two different components.
            mergeNode(p1Root, p2Root, parents, sizes);

            minConnectionCost += cost;
            connectedEdges++;

            // An MST containing n vertices always has exactly n-1 edges.
            if(connectedEdges==n-1) break;
        }

        return minConnectionCost;
    }


    private void mergeNode(int node1, int node2, int[] parents, int[] sizes) {

        // Union by size: attach the smaller component under the larger one
        // to keep the DSU tree shallow.
        int node1Size = sizes[node1];
        int node2Size = sizes[node2];

        if(node1Size<node2Size) {
            sizes[node2] += node1Size;
            parents[node1] = node2;
        }
        else {
            sizes[node1] += node2Size;
            parents[node2] = node1;
        }
    }


    private int findRootNode(int node, int[] parents) {

        // The root is the representative of the connected component.
        if(parents[node]==node) return node;

        // Path compression makes future root lookups faster.
        return parents[node] = findRootNode(parents[node], parents);
    }


    private List<int[]> getEdges(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>();

        // The graph is complete, so generate every possible pair of points.
        for(int i=0; i<n-1; i++) {
            for(int j=i+1; j<n; j++) {
                int[] p1 = points[i];
                int[] p2 = points[j];

                // Edge weight = Manhattan distance between the two points.
                int manhattanDist =
                    Math.abs(p1[0]-p2[0]) +
                    Math.abs(p1[1]-p2[1]);

                edges.add(new int[] { i, j, manhattanDist });
            }
        }

        return edges;
    }
}