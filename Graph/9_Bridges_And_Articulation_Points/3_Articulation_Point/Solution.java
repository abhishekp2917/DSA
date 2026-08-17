import java.util.ArrayList;
import java.util.Arrays;

class Solution {

    
    private int[] ids;                      // Time at which node i was first visited
    private int[] lowIds;                   // Lowest discovery time reachable from node i, including back-edges (visited node) 
    private int time;                       // Time counter to assign unique discovery times
    private boolean[] isArticulationPoint;  // Array to mark articulation points

    public ArrayList<Integer> articulationPoints(int n, ArrayList<ArrayList<Integer>> adjList) {
        // initialize arrays
        this.ids = new int[n];
        this.lowIds = new int[n];
        this.time = 0;
        this.isArticulationPoint = new boolean[n];
        ArrayList<Integer> articulationPoints = new ArrayList<>();
        
        // set all discovery times to -1 to mark nodes as unvisited
        Arrays.fill(this.ids, -1);
        
        // handle disconnected graphs by iterating through all nodes
        for(int i = 0; i < n; i++) {
            if(this.ids[i] == -1) {
                // start DFS from unvisited node
                dfs(adjList, i, -1);
            }
        }

        // collect all articulation points into the result list
        for(int node = 0; node < n; node++) {
            if(isArticulationPoint[node]) {
                articulationPoints.add(node);
            }
        }

        // if no articulation point found, return -1 as per problem convention
        if(articulationPoints.isEmpty()) articulationPoints.add(-1);
        return articulationPoints;
    }

    private void dfs(ArrayList<ArrayList<Integer>> adjList, int curr, int parent) {
        // to track how many children the current node has in DFS tree
        // consider only those which are neighbour of current node and are yet to be visited
        // because there could be N neighbours and some of them are connected via some other edge and if we remove root node, it will not create N components.
        // Let's say the graph looks like this:
        //
        //     0
        //   / | \
        //  1  2 _ 3
        //
        // Here, root node 0 has 3 neighbors. But notice: 2 and 3 are also connected to each other.
        // So, even if we remove node 0, nodes 2 and 3 stay connected through their direct link (2-3).
        int childrenCount = 0; 

        // assign discovery time and low-link value to current node
        this.ids[curr] = this.lowIds[curr] = this.time++;

        // explore all neighbors
        for(Integer neighbour : adjList.get(curr)) {
            // skip the parent node to avoid endless loops
            if(neighbour == parent) continue;

            // if neighbor hasn't been visited, perform DFS on it
            if(this.ids[neighbour] == -1) {
                dfs(adjList, neighbour, curr);

                // update current node’s low-link with the minimum reachable from child
                this.lowIds[curr] = Math.min(this.lowIds[curr], this.lowIds[neighbour]);

                // Articulation Point Condition 1:
                // If current node is not root (root node has parent = -1), and its child cannot reach any ancestor of current node,
                // then current node is an Articulation Point.
                if(parent != -1 && lowIds[neighbour] >= ids[curr]) {
                    this.isArticulationPoint[curr] = true;
                }
                
                // count children in DFS tree
                childrenCount++; 

            } else {
                // Back-edge found (neighbor is already visited and is not parent)
                // So while updating low link value of current node, don't consider low link of neighbour bu it's id because if that node is removed, current node can't reach it's low link.
                this.lowIds[curr] = Math.min(this.lowIds[curr], this.ids[neighbour]);
            }
        }

        // Articulation Point Condition 2 (for root node only):
        // If root has more than one child in DFS tree, it's an articulation point
        if(parent == -1 && childrenCount > 1) {
            this.isArticulationPoint[curr] = true;
        }
    }
}
