class Solution1 {
    public long maxSubarraySum(int[] nums, int k) {
        int n = nums.length;
        long[] maxSubarraySum = new long[] { Long.MIN_VALUE };
        Long[][][] memo = new Long[n][2][3];
        recursion(nums, k, 0, 0, 0, memo, maxSubarraySum);
        recursion(nums, k, 0, 1, 0, memo, maxSubarraySum);
        return maxSubarraySum[0];
    }

    private long recursion(int[] nums, int k, int i, int operationType, int state, Long[][][] memo, long[] maxSubarraySum) {
        if(i==nums.length) return 0;
        if(memo[i][operationType][state]!=null) {
            return memo[i][operationType][state];
        } 
        long num = nums[i];
        long modifiedNum = (operationType==0)? num*k : num/k;
        long maxSum = (state==0 || state==1)? modifiedNum : num;
        long operationNotStarted = recursion(nums, k, i+1, operationType, 0, memo, maxSubarraySum);
        long operationStarted = recursion(nums, k, i+1, operationType, 1, memo, maxSubarraySum);
        long operationEnded = recursion(nums, k, i+1, operationType, 2, memo, maxSubarraySum);
        if(state==0) {
            long result1 = num + operationNotStarted;
            long result2 = num + operationStarted;
            long result3 = modifiedNum + operationStarted;
            long result4 = modifiedNum + operationEnded;
            long maxResult = Math.max(
                Math.max(result1, result2), 
                Math.max(result3, result4)
            );
            maxSum = Math.max(maxSum, maxResult);
        }
        else if(state==1) {
            long maxResult = modifiedNum + Math.max(operationStarted, operationEnded);
            maxSum = Math.max(maxSum, maxResult);
        }
        else {
            maxSum = Math.max(maxSum, num + operationEnded);
        }
        memo[i][operationType][state] = maxSum;
        maxSubarraySum[0] = Math.max(maxSubarraySum[0], maxSum);
        return maxSum;
    }
}

class Solution2 {
    public long maxSubarraySum(int[] nums, int k) {
        int n = nums.length;
        long[][][] dp = new long[n+1][2][3];
        long answer = Long.MIN_VALUE;
        // dp[n][*][*] = 0
        for (int i=n-1; i>=0; i--) {
            for (int operationType=0; operationType<2; operationType++) {
                long num = nums[i];
                long modifiedNum = (operationType==0)? num*k : num/k;
                long operationNotStarted = dp[i+1][operationType][0];
                long operationStarted    = dp[i+1][operationType][1];
                long operationEnded      = dp[i+1][operationType][2];

                // ---------- state = 0 ----------
                long maxSum = modifiedNum;
                long result1 = num + operationNotStarted;
                long result2 = num + operationStarted;
                long result3 = modifiedNum + operationStarted;
                long result4 = modifiedNum + operationEnded;
                maxSum = Math.max(
                    maxSum,
                    Math.max(
                        Math.max(result1, result2),
                        Math.max(result3, result4)
                    )
                );

                dp[i][operationType][0] = maxSum;
                answer = Math.max(answer, maxSum);

                // ---------- state = 1 ----------
                maxSum = modifiedNum;
                long result = modifiedNum + Math.max(operationStarted, operationEnded);
                maxSum = Math.max(maxSum, result);
                dp[i][operationType][1] = maxSum;
                answer = Math.max(answer, maxSum);

                // ---------- state = 2 ----------
                maxSum = num;
                result = num + operationEnded;
                maxSum = Math.max(maxSum, result);
                dp[i][operationType][2] = maxSum;
                answer = Math.max(answer, maxSum);
            }
        }
        return answer;
    }
}