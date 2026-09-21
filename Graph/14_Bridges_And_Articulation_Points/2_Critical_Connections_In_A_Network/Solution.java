import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    private int[] ids;                                  // Discovery time (index) of each node
    private int[] lowIds;                               // Lowest reachable index (low-link) from the node
    private int time;                                   // Time counter 
    private List<List<Integer>> criticalConnections;    // List to store the Critical Connections
    
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // initialize the global variable
        this.ids = new int[n];
        this.lowIds = new int[n];
        this.time = 0;
        this.criticalConnections = new ArrayList<>();
        List<Integer>[] adjList = getAdjList(n, connections);

        // intializing ids[] with -1 which represents nodes are not visited 
        Arrays.fill(ids, -1);

        // iterating through each node and if it is not already visited, start the dfs traversal from that node
        for(int node=0; node<n; node++) {
            if(this.ids[node]==-1) {
                dfs(adjList, node, -1);
            }
        }
        return this.criticalConnections;
    }

    private void dfs(List<Integer>[] adjList, int curr, int parent) {
        // assign index/time to the current node and then increment the timer 
        // this is the entry time of this node
        this.ids[curr] = this.lowIds[curr] = this.time++;
        
        // iterate over each neighbour of current node
        for(Integer neighbour : adjList[curr]) {
            // if the neighbour is parent, then skip it else it will lead to endless loop
            if(neighbour==parent) continue;

            // if neighbour is not visited, then do dfs traversal from that node
            if(this.ids[neighbour]==-1) {
                dfs(adjList, neighbour, curr);
                // if neighbour node can reach a specific lowest index, then current node can also reach that node via neighbour
                // so update the lowest reachable index of current node if it is lower than current 
                this.lowIds[curr] = Math.min(this.lowIds[curr], this.lowIds[neighbour]);
            }
            // if node is already visited, then just update the lowest reachable index of current node
            else {
                this.lowIds[curr] = Math.min(this.lowIds[curr], this.lowIds[neighbour]);
            }
            // if lowest reachable time/index of neighbour node is greater than current node time/index 
            // then it represents that neighbour node can't reach any node of current node component from any other path if the eadge between current and neighbour is removed
            // this shows edge between current and neighbour is a critical connection, so add it in a list 
            if(lowIds[neighbour]>ids[curr]) {
                this.criticalConnections.add(Arrays.asList(curr, neighbour));
            }
        }
    }

    // function to get adjacency list
    private List<Integer>[] getAdjList(int n, List<List<Integer>> connections) {
        List<Integer>[] adjList = new ArrayList[n];
        for(int i=0; i<n; i++) {
            adjList[i] = new ArrayList<>();
        }
        connections.stream().forEach(connection -> {
            int u = connection.get(0);
            int v = connection.get(1);
            adjList[u].add(v);
            adjList[v].add(u);
        });
        return adjList;
    }
}