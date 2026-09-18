import java.util.ArrayList;
import java.util.List;

class Solution {

    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {

        // nodeFreq[node]:
        // stores how many times
        // this node appears
        // across all trip paths.
        //
        // Final contribution of a node is:
        //
        // price[node] × nodeFreq[node]
        int[] nodeFreq = new int[n];

        // Build the tree.
        List<Integer>[] adjList = new ArrayList[n];

        for(int i=0; i<n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for(int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
            adjList[edge[1]].add(edge[0]);
        }

        // Process every trip.
        //
        // For every path,
        // increment the frequency
        // of every node lying on that path.
        //
        // After this preprocessing,
        // the original problem reduces to:
        //
        // Which nodes should be halved?
        for(int[] trip : trips) {

            int start = trip[0];
            int end = trip[1];

            traverse(
                adjList,
                nodeFreq,
                -1,
                start,
                end
            );
        }

        // memo[node][parentHalfed]:
        //
        // stores the minimum total cost
        // of the subtree rooted at 'node'.
        //
        // parentHalfed == 0
        // -> parent was NOT halved.
        //    Current node may either
        //    be halved or not.
        //
        // parentHalfed == 1
        // -> parent was halved.
        //    Current node cannot be halved
        //    because adjacent nodes
        //    cannot both be halved.
        Integer[][] memo = new Integer[n][2];

        return recursion(
            adjList,
            nodeFreq,
            price,
            -1,
            0,
            0,
            memo
        );
    }

    private int recursion(List<Integer>[] adjList, int[] nodeFreq, int[] price, int prev, int start, int prevHalfed, Integer[][] memo) {

        // Return previously computed answer.
        if(memo[start][prevHalfed]!=null) {
            return memo[start][prevHalfed];
        }

        // Option 1:
        // Do NOT halve current node.
        //
        // Pay full price
        // for every occurrence
        // of this node.
        int minPriceWithoutHalfed =
            price[start] * nodeFreq[start];

        for(int next : adjList[start]) {

            if(next==prev) continue;

            // Since current node
            // was not halved,
            // children remain free
            // to decide independently.
            minPriceWithoutHalfed +=
                recursion(
                    adjList,
                    nodeFreq,
                    price,
                    start,
                    next,
                    0,
                    memo
                );
        }

        int minPriceWithHalfed = Integer.MAX_VALUE;

        // Option 2:
        // Halve current node.
        //
        // Possible only if:
        //
        // Parent was not halved.
        //
        // Also skip unnecessary work
        // when this node never appears
        // in any trip.
        if(prevHalfed==0 && nodeFreq[start]>0) {

            // Current node contributes
            // half its original cost.
            minPriceWithHalfed =
                (price[start] * nodeFreq[start]) / 2;

            for(int next : adjList[start]) {

                if(next==prev) continue;

                // Since current node
                // is halved,
                // children are forbidden
                // from being halved.
                minPriceWithHalfed +=
                    recursion(
                        adjList,
                        nodeFreq,
                        price,
                        start,
                        next,
                        1,
                        memo
                    );
            }
        }

        // Choose whichever decision
        // produces the smaller total cost.
        memo[start][prevHalfed] =
            Math.min(
                minPriceWithoutHalfed,
                minPriceWithHalfed
            );

        return memo[start][prevHalfed];
    }

    private boolean traverse(List<Integer>[] adjList, int[] nodeFreq, int prev, int start, int end) {

        // Destination reached.
        //
        // This node belongs
        // to the required trip path.
        if(start==end) {

            nodeFreq[start]++;

            return true;
        }

        // DFS to locate
        // the destination.
        for(int next : adjList[start]) {

            if(next==prev) continue;

            // If destination exists
            // in this subtree,
            // then current node
            // also lies on the trip path.
            if(traverse(adjList, nodeFreq, start, next, end)) {

                nodeFreq[start]++;

                return true;
            }
        }

        // Destination not found
        // in this subtree.
        return false;
    }
}