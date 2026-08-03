import java.util.HashMap;
import java.util.Map;

class Solution1 {

    public int lenLongestFibSubseq(int[] nums) {
        int n = nums.length;
        int longestLen = 0;
        Map<Integer, Integer> idxMap = new HashMap<>();
        for(int i=0; i<n; i++) idxMap.put(nums[i], i);
        Integer[][] memo = new Integer[n][n];
        for(int start=0; start<n; start++) {
            for(int prev=0; prev<start; prev++) {
                longestLen = Math.max(
                    longestLen,
                    recurison(nums, idxMap, start, prev, memo)
                );
            }    
        } 
        return (longestLen>2)? longestLen : 0;
    }

    private int recurison(int[] nums, Map<Integer, Integer> idxMap, int start, int prev, Integer[][] memo) {
        if(memo[start][prev]!=null) return memo[start][prev];
        int expectedNum = nums[start] + nums[prev];
        Integer next = idxMap.get(expectedNum);
        int longestLen = 2;
        if(next!=null) {
            longestLen = Math.max(
                longestLen,
                1 + recurison(nums, idxMap, next, start, memo)
            ); 
        }
        memo[start][prev] = longestLen;
        return longestLen;
    }
}

class Solution2 {

    public int lenLongestFibSubseq(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> idxMap = new HashMap<>();
        for (int i = 0; i < n; i++) idxMap.put(nums[i], i);
        int[][] dp = new int[n][n];
        int longestLen = 0;
        for (int start=n-1; start>=0; start--) {
            for (int prev=start-1; prev>=0; prev--) {
                int expected = nums[prev] + nums[start];
                Integer next = idxMap.get(expected);
                dp[start][prev] = 2;
                if (next != null) {
                    dp[start][prev] = 1 + dp[next][start];
                }
                longestLen = Math.max(longestLen, dp[start][prev]);
            }
        }
        return longestLen > 2 ? longestLen : 0;
    }
}