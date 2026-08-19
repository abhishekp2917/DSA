import java.util.Arrays;

class Solution {
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        int e = edgeList.length;
        int q = queries.length;
        boolean[] result = new boolean[q];

        int[] parents = new int[n];
        int[] sizes = new int[n];

        // Keep the original query index because queries are sorted by limit
        // for the offline DSU processing, but answers must be returned
        // in the original query order.
        int[][] indexedQueries = getIndexedQueriesSortedByLimit(q, queries);

        // Initially every node is its own connected component.
        for(int i=0; i<n; i++) parents[i] = i;
        Arrays.fill(sizes, 1);

        // Process edges from smallest weight to largest weight so that
        // edges can be incrementally added as the query limit increases.
        Arrays.sort(edgeList, (e1, e2) -> Integer.compare(e1[2], e2[2]));

        int eIdx = 0;

        for(int[] query : indexedQueries) {
            int idx = query[0];
            int nodeU = query[1];
            int nodeV = query[2];
            int limit = query[3];

            // Add every edge whose weight is strictly smaller than limit.
            // These are exactly the edges allowed for this query.
            while(eIdx<e && edgeList[eIdx][2]<limit) {
                joinNodes(
                    parents,
                    sizes,
                    edgeList[eIdx][0],
                    edgeList[eIdx][1]
                );
                eIdx++;
            }

            // After adding all allowed edges, u and v are connected in DSU
            // exactly when a path exists whose every edge has weight < limit.
            int uRootNode = findRootNode(parents, nodeU);
            int vRootNode = findRootNode(parents, nodeV);

            result[idx] = (uRootNode==vRootNode);
        }

        return result;
    }


    private void joinNodes(int[] parents, int[] sizes, int nodeU, int nodeV) {
        // Merge the connected components containing nodeU and nodeV.
        int uRootNode = findRootNode(parents, nodeU);
        int vRootNode = findRootNode(parents, nodeV);

        // They are already connected, so this edge does not change
        // the connected components.
        if(uRootNode==vRootNode) return;

        int uSize = sizes[uRootNode];
        int vSize = sizes[vRootNode];

        // Union by size keeps the DSU tree shallow.
        if(uSize<vSize) {
            parents[vRootNode] = uRootNode;
            sizes[uRootNode] += vSize;
        }
        else {
            parents[uRootNode] = vRootNode;
            sizes[vRootNode] += uSize;
        }
    }


    private int findRootNode(int[] parents, int node) {

        // A node whose parent is itself is the representative
        // of its connected component.
        if(parents[node]==node) return node;

        // Path compression:
        // directly connect this node to the component root so
        // future find operations become faster.
        return parents[node] =
            findRootNode(parents, parents[node]);
    }


    private int[][] getIndexedQueriesSortedByLimit(int q, int[][] queries) {
        int[][] indexedQueries = new int[q][4];

        for(int i=0; i<q; i++) {

            // Store:
            // [originalIndex, nodeU, nodeV, limit]
            //
            // The original index lets us put the answer back in the
            // order expected by the input.
            indexedQueries[i] = new int[] {
                i,
                queries[i][0],
                queries[i][1],
                queries[i][2]
            };
        }

        // Processing queries by increasing limit allows us to keep adding
        // edges to the DSU instead of rebuilding it for every query.
        Arrays.sort(
            indexedQueries,
            (q1, q2) -> Integer.compare(q1[3], q2[3])
        );

        return indexedQueries;
    }
}