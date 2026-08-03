import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution1 {
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        int largestColorValue = 0;
        Integer[][] memo = new Integer[n][26];
        int[] inDegree = new int[n];
        List<Integer>[] adjList = new ArrayList[n];
        for(int i=0; i<n; i++) adjList[i] = new ArrayList<>();
        for(int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
            inDegree[edge[1]]++;
        }
        if(hasCycle(adjList, inDegree)) return -1;
        for(int node=0; node<n; node++) {
            Integer[] colorFreq = recursion(colors, adjList, node, memo);
            for(int i=0; i<26; i++) {
                largestColorValue = Math.max(largestColorValue, colorFreq[i]);
            }
        }
        return largestColorValue;
    }

    private Integer[] recursion(String colors, List<Integer>[] adjList, int start, Integer[][] memo) {
        int colorIdx = colors.charAt(start)-'a';
        if(memo[start][colorIdx]!=null) return memo[start];
        Arrays.fill(memo[start], 0);
        for(int next : adjList[start]) {
            Integer[] colorFreqFromNext = recursion(colors, adjList, next, memo);
            for(int i=0; i<26; i++) {
                memo[start][i] = Math.max(memo[start][i], colorFreqFromNext[i]);
            }
        }
        memo[start][colorIdx]++;
        return memo[start];
    }

    private boolean hasCycle(List<Integer>[] adjList, int[] inDegree) {
        int n = inDegree.length;
        Queue<Integer> queue = new LinkedList<>();
        for(int node=0; node<n; node++) {
            if(inDegree[node]==0) queue.add(node);
        }
        while(!queue.isEmpty()) {
            int node = queue.poll();
            for(int next : adjList[node]) {
                if(--inDegree[next]==0) queue.add(next);
            }
        }
        for(int node=0; node<n; node++) {
            if(inDegree[node]!=0) return true;
        }
        return false;
    }
}