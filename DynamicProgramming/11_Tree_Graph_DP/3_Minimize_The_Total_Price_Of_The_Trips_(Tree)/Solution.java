import java.util.ArrayList;
import java.util.List;

class Solution1 {
    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        int[] nodeFreq = new int[n];
        List<Integer>[] adjList = new ArrayList[n];
        for(int i=0; i<n; i++) adjList[i] = new ArrayList<>();
        for(int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
            adjList[edge[1]].add(edge[0]);
        }
        for(int[] trip : trips) {
            int start = trip[0];
            int end = trip[1];
            traverse(adjList, nodeFreq, -1, start, end);
        }
        Integer[][] memo = new Integer[n][2];
        return recursion(adjList, nodeFreq, price, -1, 0, 0, memo);
    }

    private int recursion(List<Integer>[] adjList, int[] nodeFreq, int[] price, int prev, int start, int prevHalfed, Integer[][] memo) {
        if(memo[start][prevHalfed]!=null) return memo[start][prevHalfed];
        int minPriceWithoutHalfed = price[start]*nodeFreq[start];
        int minPriceWithHalfed = Integer.MAX_VALUE;
        for(int next : adjList[start]) {
            if(next==prev) continue;
            minPriceWithoutHalfed += recursion(adjList, nodeFreq, price, start, next, 0, memo);
        }
        if(prevHalfed==0 && nodeFreq[start]>0) {
            minPriceWithHalfed = (price[start]*nodeFreq[start])/2;
            for(int next : adjList[start]) {
                if(next==prev) continue;
                minPriceWithHalfed += recursion(adjList, nodeFreq, price, start, next, 1, memo);
            }
        }
        memo[start][prevHalfed] = Math.min(minPriceWithoutHalfed, minPriceWithHalfed);
        return memo[start][prevHalfed];
    }

    private boolean traverse(List<Integer>[] adjList, int[] nodeFreq, int prev, int start, int end) {
        if(start==end) {
            nodeFreq[start]++;
            return true;
        }
        for(int next : adjList[start]) {
            if(next==prev) continue;
            if(traverse(adjList, nodeFreq, start, next, end)) {
                nodeFreq[start]++;
                return true;
            }
        }
        return false;
    }
}