import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution1 {
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        Map<Integer, Integer>[] memo = new Map[n + 1];
        for (int i = 0; i <= n; i++) memo[i] = new HashMap<>();
        return recursion(nums, n, target, memo);
    }

    private int recursion(int[] nums, int arrLen, int target, Map<Integer, Integer>[] memo) {
        if (arrLen==0) return (target==0)? 1 : 0;
        if (memo[arrLen].containsKey(target)) return memo[arrLen].get(target);
        int idx = arrLen-1;
        int num = nums[idx];
        int positive = recursion(nums, arrLen-1, target+num, memo);
        int negative = recursion(nums, arrLen-1, target-num, memo);
        int ways = positive + negative;
        memo[arrLen].put(target, ways);
        return ways;
    }
}

class Solution2 {
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int total = Arrays.stream(nums).sum();
        Map<Integer, Integer>[] dp = new Map[n+1];
        for(int i=0; i<=n; i++) dp[i] = new HashMap<>();
        for(int tgt=-total; tgt<=total; tgt++) dp[0].put(tgt, 0);
        dp[0].put(0, 1);
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int num = nums[idx];
            for(int tgt=-total; tgt<=total; tgt++) {
                int positive = dp[arrLen-1].getOrDefault(tgt+num, 0);
                int negative = dp[arrLen-1].getOrDefault(tgt-num, 0);
                dp[arrLen].put(tgt, positive + negative);
            }
        }
        return dp[n].getOrDefault(target, 0);
    }
}
