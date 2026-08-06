import java.util.ArrayList;
import java.util.List;

class Solution {

    public int[] sumOfDistancesInTree(int n, int[][] edges) {

        // childNodesCount[node]:
        // stores the total number of nodes
        // present in the subtree rooted at 'node',
        // including the node itself.
        //
        // This information is required during
        // rerooting to know how many nodes
        // become closer and how many become farther
        // when the root shifts to one of its children.
        int[] childNodesCount = new int[n];

        // nodesDistance[node]:
        // stores the sum of distances
        // from 'node' to every other node.
        //
        // Initially only nodesDistance[0]
        // will be computed directly.
        //
        // Distances for every other node
        // will later be derived in O(1)
        // using the rerooting formula.
        int[] nodesDistance = new int[n];

        // Build the tree.
        List<Integer>[] adjList = new ArrayList[n];

        for(int node=0; node<n; node++) {
            adjList[node] = new ArrayList<>();
        }

        for(int[] edge:edges) {
            adjList[edge[0]].add(edge[1]);
            adjList[edge[1]].add(edge[0]);
        }

        // First DFS:
        //
        // 1. Compute subtree size
        //    for every node.
        //
        // 2. Compute answer only
        //    for node 0.
        //
        // These two values are sufficient
        // to derive answers
        // for every remaining node.
        calcStartingNodeDistance(
            adjList,
            0,
            -1,
            childNodesCount,
            nodesDistance,
            0
        );

        // Second DFS (Rerooting):
        //
        // Move the root from parent
        // to each child one by one.
        //
        // Instead of recomputing
        // all distances from scratch,
        // update the answer
        // using subtree sizes.
        rerooting(
            adjList,
            childNodesCount,
            nodesDistance,
            0,
            -1
        );

        return nodesDistance;
    }

    private void calcStartingNodeDistance(List<Integer>[] adjList, int node, int parent, int[] childNodesCount, int[] nodesDistance, int dist) {

        // Every subtree contains
        // the current node itself.
        childNodesCount[node] = 1;

        // While DFS is running from node 0,
        // 'dist' already represents
        // the distance from node 0
        // to the current node.
        //
        // Therefore simply accumulate it.
        nodesDistance[0] += dist;

        for(int child : adjList[node]) {

            // Skip the parent
            // to avoid revisiting
            // the same edge.
            if(child==parent) continue;

            // Child is one edge farther
            // from the original root.
            calcStartingNodeDistance(
                adjList,
                child,
                node,
                childNodesCount,
                nodesDistance,
                dist+1
            );

            // Every node inside
            // the child's subtree
            // also belongs to
            // the current subtree.
            //
            // Therefore add the entire
            // subtree size.
            childNodesCount[node] += childNodesCount[child];
        }
    }

    private void rerooting(List<Integer>[] adjList, int[] childNodesCount, int[] nodesDistance, int node, int parent) {

        int totalNodes = adjList.length;

        for(int child : adjList[node]) {

            if(child==parent) continue;

            // When root moves
            // from 'node'
            // to 'child',
            // every node inside
            // child's subtree
            // becomes one step closer.
            int nearerNodes = childNodesCount[child];

            // Every remaining node
            // lies outside
            // child's subtree.
            //
            // These nodes become
            // one step farther.
            int fartherNodes =
                totalNodes - nearerNodes;

            // Update answer
            // without recomputing distances.
            //
            // Previous answer:
            //
            // nodesDistance[node]
            //
            // Closer nodes contribute:
            //
            // - nearerNodes
            //
            // because each distance
            // decreases by 1.
            //
            // Farther nodes contribute:
            //
            // + fartherNodes
            //
            // because each distance
            // increases by 1.
            nodesDistance[child] =
                nodesDistance[node]
                - nearerNodes
                + fartherNodes;

            // Continue rerooting
            // from this child.
            //
            // Its answer now becomes
            // the base answer
            // for all of its children.
            rerooting(
                adjList,
                childNodesCount,
                nodesDistance,
                child,
                node
            );
        }
    }
}