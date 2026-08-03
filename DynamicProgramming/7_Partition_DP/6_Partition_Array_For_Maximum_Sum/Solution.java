class Solution1 {
    public int maxSumAfterPartitioning(int[] nums, int k) {
        int n = nums.length;
        Integer[] memo = new Integer[n];
        return recursion(nums, k, 0, memo);
    }

    private int recursion(int[] nums, int k, int start, Integer[] memo) {
        int n = nums.length;
        if(start==nums.length) return 0;
        if(memo[start]!=null) return memo[start];
        int maxSum = 0;
        int maxNum = 0;
        int length = 1;
        for(int end=start; end<Math.min(start+k, n); end++, length++) {
            maxNum = Math.max(maxNum, nums[end]);
            maxSum = Math.max(
                maxSum,
                maxNum*length + recursion(nums, k, end+1, memo)
            );
        }
        memo[start] = maxSum;
        return maxSum;
    }
}

class Solution2 {
    public int maxSumAfterPartitioning(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n+1];
        for(int start=n-1; start>=0; start--) {
            int maxSum = 0;
            int maxNum = 0;
            int length = 1;
            for(int end=start; end<Math.min(start+k, n); end++, length++) {
                maxNum = Math.max(maxNum, nums[end]);
                maxSum = Math.max(
                    maxSum,
                    maxNum*length + dp[end+1]
                );
            }
            dp[start] = maxSum;
        }
        return dp[0];
    }
}