import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {

    public List<Integer> dfsTraversal(int n, HashMap<Integer, List<Integer>> adjacencyList, int startNode) {

        // List to store the DFS traversal of the graph
        List<Integer> dfsTraversal = new ArrayList<>();

        // Array to keep track of visited nodes in the graph
        // This is used to avoid visiting the same node multiple times
        boolean[] visited = new boolean[n];

        // Call the dfs function to perform the DFS traversal of the graph
        // We are using seperate function to perform the DFS traversal because we need to pass the visited array and the 
        // DFS traversal list
        dfs(adjacencyList, startNode, visited, dfsTraversal);
        
        return dfsTraversal;
    }

    // Function to perform the DFS traversal of the graph
    private void dfs(HashMap<Integer, List<Integer>> adjacencyList, int currNode, boolean[] visited, List<Integer> dfsTraversal) {
        
        // Add the current node to the DFS traversal list and mark it as visited
        // We aren't checking if the node is already visited because we are doing that in the dfsTraversal function itself 
        dfsTraversal.add(currNode);
        visited[currNode] = true;

        // Iterate over all the neighbours of the current node and call the dfs function recursively for the unvisited neighbours
        for(int neighbour : adjacencyList.get(currNode)) {
            
            // If the neighbour is not visited, call the dfs function recursively
            // This makes sure whenever this function is called, the node is not visited
            if(!visited[neighbour]) {
                dfs(adjacencyList, neighbour, visited, dfsTraversal);
            }
        }
    }
}