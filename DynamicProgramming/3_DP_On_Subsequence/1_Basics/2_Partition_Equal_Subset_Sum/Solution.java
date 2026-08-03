import java.util.Arrays;

class Solution1 {
    public boolean canPartition(int[] nums) {
        int total = Arrays.stream(nums).sum();
        if (total%2!=0) return false;
        int target = total/2;
        int n = nums.length;
        Boolean[][] memo = new Boolean[target+1][n+1];
        return recursion(nums, target, n, memo);
    }

    private boolean recursion(int[] nums, int target, int arrLen, Boolean[][] memo) {
        if (target == 0) return true;
        if (arrLen == 0) return false;
        if (memo[target][arrLen]!=null) return memo[target][arrLen];
        int idx = arrLen-1;
        int num = nums[idx];
        boolean exclude = recursion(nums, target, arrLen-1, memo);
        boolean include = false;
        if (target >= num) {
            include = recursion(nums, target-num, arrLen-1, memo);
        }
        memo[target][arrLen] = exclude || include;
        return memo[target][arrLen];
    }
}

class Solution2 {
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int total = Arrays.stream(nums).sum();
        if(total%2!=0) return false;
        int target = total/2;
        boolean[] dp = new boolean[target+1];
        dp[0] = true;
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int num = nums[idx];
            for(int tgt=target; tgt>=0; tgt--) {
                dp[tgt] = ((tgt>=num)?dp[tgt-num]:false) || dp[tgt];
            }
        }
        return dp[target];
    }
}