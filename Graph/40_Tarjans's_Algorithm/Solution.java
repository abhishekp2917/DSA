import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

class Solution {

    private int[] ids;
    private int[] lowIds;
    private int[] time;
    private boolean[] onStack;
    private boolean[] visited;
    private Stack<Integer> stack;
    private ArrayList<ArrayList<Integer>> result;

    // Function to return a list of lists of integers denoting the members
    // of strongly connected components in the given graph.
    public ArrayList<ArrayList<Integer>> tarjans(int n, ArrayList<ArrayList<Integer>> adj) {
        this.ids = new int[n];
        this.lowIds = new int[n];
        this.time = new int[1];
        this.onStack = new boolean[n];
        this.visited = new boolean[n];
        this.stack = new Stack<>();
        this.result = new ArrayList<>();
        for(int i=0; i<n; i++) {
            if(this.visited[i]) continue;
            dfs(adj, i);
        }
        Collections.sort(result, (comp1, comp2) -> Integer.compare(comp1.get(0), comp2.get(0)));
        return result;
        
    }

    private void dfs(ArrayList<ArrayList<Integer>> adj, int curr) {
        this.stack.push(curr);
        this.onStack[curr] = true;
        this.ids[curr] = this.lowIds[curr] = this.time[0]++;
        this.visited[curr] = true;
        for(int neighbour : adj.get(curr)) {
            if(!visited[neighbour]) {
                dfs(adj, neighbour);
                this.lowIds[curr] = Math.min(lowIds[curr], lowIds[neighbour]);
            }
            else if(onStack[neighbour]) {
                this.lowIds[curr] = Math.min(lowIds[curr], lowIds[neighbour]);
            }
        }
        if(ids[curr]==lowIds[curr]) {
            ArrayList<Integer> components = new ArrayList<>();
            while(true) {
                int node = stack.pop();
                this.onStack[node] = false;
                components.add(node);
                if(node==curr) break;
            }
            Collections.sort(components);
            result.add(components);
        }
    }
}