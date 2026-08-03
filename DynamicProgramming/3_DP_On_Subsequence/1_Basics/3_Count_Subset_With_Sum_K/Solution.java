class Solution1 {

    public int perfectSum(int[] nums, int target) {
        int n = nums.length;
        Integer[][] memo = new Integer[target+1][n+1];
        return recursion(nums, target, n, memo);
    }

    private int recursion(int[] nums, int target, int arrLen, Integer[][] memo) {
        if (target==0 && arrLen==0) return 1;
        if (arrLen==0) return 0;
        if (memo[target][arrLen]!=null) return memo[target][arrLen];
        int idx = arrLen-1;
        int num = nums[idx];
        int coutWithoutCurrNum = recursion(nums, target, arrLen-1, memo);
        int coutWithCurrNum = 0;
        if (target >= num) {
            coutWithCurrNum = recursion(nums, target-num, arrLen-1, memo);
        }
        memo[target][arrLen] = coutWithoutCurrNum + coutWithCurrNum;
        return memo[target][arrLen];
    }
}

class Solution2 {

    public int perfectSum(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[target+1][n+1];
        dp[0][0] = 1;
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int num = nums[idx];
            for(int tgt=0; tgt<=target; tgt++) {
                dp[tgt][arrLen] = 
                dp[tgt][arrLen-1] + ((tgt-num>=0)? dp[tgt-num][arrLen-1] : 0);
            }   
        }
        return dp[target][n];
    }
}

class Solution3 {

    public int perfectSum(int[] nums, int target) {
        int n = nums.length;
        int[] dp = new int[target+1];
        dp[0] = 1;
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int num = nums[idx];
            for(int tgt=target; tgt>=0; tgt--) {
                dp[tgt] = 
                dp[tgt] + ((tgt-num>=0)? dp[tgt-num] : 0);
            }   
        }
        return dp[target];
    }
}