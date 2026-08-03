import java.util.Arrays;

class Solution1 {
    public int lastStoneWeightII(int[] stones) {
        int n = stones.length;
        int totalWeight = Arrays.stream(stones).sum();
        Integer[][] memo = new Integer[n][totalWeight+1];
        return recursion(stones, totalWeight, 0, 0, memo);
    }

    private int recursion(int[] stones, int totalWeight, int i, int sum, Integer[][] memo) {
        if(i==stones.length) return Math.abs(totalWeight-2*sum);
        if(memo[i][sum]!=null) return memo[i][sum];
        int weight = stones[i];
        int minDff = Math.min(
            recursion(stones, totalWeight, i+1, sum+weight, memo),
            recursion(stones, totalWeight, i+1, sum, memo)
        );
        memo[i][sum] = minDff;
        return minDff;
    }
}
 
class Solution2 {
    public int lastStoneWeightII(int[] stones) {
        int n = stones.length;
        int totalWeight = Arrays.stream(stones).sum();
        int[][] dp = new int[n+1][totalWeight+1];
        for(int sum=0; sum<=totalWeight; sum++) {
            dp[n][sum] = Math.abs(totalWeight-2*sum);
        }
        for(int i=n-1; i>=0; i--) {
            int weight = stones[i];
            for(int sum=totalWeight-weight; sum>=0; sum--) {
                int minDff = Math.min(
                    dp[i+1][sum+weight],
                    dp[i+1][sum]
                );
                dp[i][sum] = minDff;
            }
        }
        return dp[0][0];
    }
}
 



