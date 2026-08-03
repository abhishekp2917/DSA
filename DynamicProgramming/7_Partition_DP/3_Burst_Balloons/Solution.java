class Solution1 {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        Integer[][] memo = new Integer[n][n];
        return recursion(nums, 0, n-1, memo);
    }

    private int recursion(int[] nums, int start, int end, Integer[][] memo) {
        if(start>end) return 0;
        if(memo[start][end]!=null) return memo[start][end];
        int maxCoins = 0;
        for(int i=start; i<=end; i++) {
            int leftVal = ((start-1>=0)? nums[start-1] : 1);
            int rightVal = ((end+1<nums.length)? nums[end+1] : 1);
            int currVal = nums[i];
            int currCoins = leftVal*currVal*rightVal;
            int leftCoins = recursion(nums, start, i-1, memo);
            int rightCoins = recursion(nums, i+1, end, memo);
            maxCoins = Math.max(
                maxCoins,
                (currCoins + leftCoins + rightCoins)
            );
        }
        memo[start][end] = maxCoins;
        return maxCoins;
    }
}

class Solution2 {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        for(int start=n-1; start>=0; start--) {
            for(int end=start; end<n; end++) {
                int maxCoins = 0;
                for(int i=start; i<=end; i++) {
                    int leftVal = ((start-1>=0)? nums[start-1] : 1);
                    int rightVal = ((end+1<nums.length)? nums[end+1] : 1);
                    int currVal = nums[i];
                    int currCoins = leftVal*currVal*rightVal;
                    int leftCoins = (i-1>=0)? dp[start][i-1] : 0;
                    int rightCoins = (i+1<n)? dp[i+1][end] : 0;
                    maxCoins = Math.max(
                        maxCoins,
                        (currCoins + leftCoins + rightCoins)
                    );
                }
                dp[start][end] = maxCoins;
            }
        }
        return dp[0][n-1];
    }
}

