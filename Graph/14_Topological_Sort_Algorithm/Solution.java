import java.util.ArrayList;
import java.util.Collections;

class Solution {
    static ArrayList<Integer> topologicalSort(ArrayList<ArrayList<Integer>> adj) {
        int n = adj.size();
        ArrayList<Integer> topologicallySortedList = new ArrayList<>();
        boolean[] visited = new boolean[n];
        for(int i=0; i<n; i++) {
            // if the node is not visited, then traverse the graph starting from that node and store the topologically sorted list
            if(visited[i]) continue;
            dfs(adj, i, visited, topologicallySortedList);
        }
        // reverse the list to get the topologically sorted list
        Collections.reverse(topologicallySortedList);
        return topologicallySortedList;
    }

    // dfs to traverse the graph and store the topologically sorted list
    private static void dfs(ArrayList<ArrayList<Integer>> adj, int curr, boolean[] visited, ArrayList<Integer> topologicallySortedList) {
        if(visited[curr]) return;
        // mark the current node as visited
        visited[curr] = true;
        // visit all the neighbours of the current node
        adj.get(curr).stream().forEach(neighbour -> dfs(adj, neighbour, visited, topologicallySortedList));
        // while backtracking, add the current node to the topologically sorted list
        // topological sort means that ancestors should come before their descendants in the list
        // we are adding the node while backtracking because we want ancestors to be added after their descendants so that when we reverse the list, we get the topologically sorted list
        topologicallySortedList.add(curr);
    }
}