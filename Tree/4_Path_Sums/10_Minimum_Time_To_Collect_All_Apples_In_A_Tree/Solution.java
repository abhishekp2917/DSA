import java.util.ArrayList;
import java.util.List;

class Solution {

    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        List<Integer>[] adjList = new ArrayList[n];

        for (int node = 0; node < n; node++) {
            adjList[node] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
            adjList[edge[1]].add(edge[0]);
        }
        return findMinTime(adjList, 0, -1, hasApple);
    }

    private int findMinTime(List<Integer>[] adjList, int root, int parent, List<Boolean> hasApple) {

        int time = 0;

        for (int child : adjList[root]) {

            if (child == parent) {
                continue;
            }
            int childTime = findMinTime(adjList, child, root, hasApple);
            if (childTime != 0 || hasApple.get(child)) {
                time += childTime + 2;
            }
        }

        return time;
    }
}