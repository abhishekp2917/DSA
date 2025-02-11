import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

class Solution {

    public int[] shortestPath(int V, int E, int[][] edges) {
        // Maximum possible edges in a graph with V vertices is V*(V-1)/2
        final int MAX_POSSIBLE_EDGES = V*(V-1)/2;  
        // Create adjacency list from edges where each node has a list of neighbours with edge weight
        List<List<List<Integer>>> adjList = getAdjList(V, edges);
        // Stack to store topological sort of the graph
        Stack<Integer> topologicalSort = new Stack<>();
        boolean[] visited = new boolean[V];
        // Array to store the distance of each node from source node
        int[] ans = new int[V];
        // Initialize the distance of each node from source node as MAX_POSSIBLE_EDGES indicating no path from source to destination
        Arrays.fill(ans, MAX_POSSIBLE_EDGES);
        // Distance of source node from itself is 0
        ans[0] = 0;
        // Get topological sort of the graph in topologicalSort stack
        // we are using stack because when we pop the elements from stack we get the topological sort
        for(int i=0; i<V; i++) {
            getTopologicalSort(adjList, i, visited, topologicalSort);
        }
        while(!topologicalSort.isEmpty()) {
            // For each node in topological sort, update the distance of its neighbours
            int currNode = topologicalSort.pop();
            adjList.get(currNode).stream().forEach(neighbour -> {
                int neighbourNode = neighbour.get(0);
                int neighbourEdgeWeight = neighbour.get(1);
                // get the previous distance of neighbour node from source
                int prevDistance = ans[neighbourNode];
                // get the new distance of neighbour node from source
                int newDistance =  ans[currNode] + neighbourEdgeWeight;
                // If new distance is less than previous distance then update the distance of neighbour node from source
                if(newDistance<prevDistance) ans[neighbourNode] = newDistance;
            });
        }
        // If there is no path from source to destination (i.e. distance if Infinity/Max value) then set the distance as -1
        for(int i=0; i<V; i++) {
            if(ans[i]==MAX_POSSIBLE_EDGES) ans[i] = -1;
        }
        return ans;
    }

    // Get topological sort of the graph using DFS and store it in stack
    private void getTopologicalSort(List<List<List<Integer>>> adjList, Integer curr, boolean[] visited, Stack<Integer> topologicalSort) {
        if(visited[curr]) return;
        visited[curr] = true;
        adjList.get(curr).stream().forEach(neighbour -> getTopologicalSort(adjList, neighbour.get(0), visited, topologicalSort));
        topologicalSort.push(curr);
    }

    // Create adjacency list from edges where each node has a list of neighbours with edge weight
    private List<List<List<Integer>>> getAdjList(int n, int[][] edges) {
        List<List<List<Integer>>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }
        for(int i=0; i<edges.length; i++) {
            adjList.get(edges[i][0]).add(new ArrayList<>(Arrays.asList(new Integer[] { edges[i][1], edges[i][2]})));
        }
        return adjList;
    }
}