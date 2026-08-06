class Solution1 {

    public long maxSubarraySum(int[] nums, int k) {

        int n = nums.length;

        // Stores the global maximum subarray sum
        // among every possible starting index,
        // operation type and operation state.
        //
        // Array is used so recursion
        // can update it by reference.
        long[] maxSubarraySum = new long[] { Long.MIN_VALUE };

        // memo[i][operationType][state]:
        //
        // stores the maximum subarray sum
        // starting exactly from index i
        // under the given operation type
        // and current operation state.
        Long[][][] memo = new Long[n][2][3];

        // Operation Type:
        //
        // 0 -> Multiply every element
        //      inside the chosen subarray by k.
        //
        // 1 -> Divide every element
        //      inside the chosen subarray by k.
        recursion(nums, k, 0, 0, 0, memo, maxSubarraySum);
        recursion(nums, k, 0, 1, 0, memo, maxSubarraySum);

        return maxSubarraySum[0];
    }

    private long recursion(int[] nums, int k, int i, int operationType, int state, Long[][][] memo, long[] maxSubarraySum) {

        // No elements remain,
        // so contribution becomes zero.
        if(i==nums.length) return 0;

        // Reuse previously computed state.
        if(memo[i][operationType][state]!=null) {
            return memo[i][operationType][state];
        }

        long num = nums[i];

        // Compute the modified value
        // depending on the chosen operation.
        long modifiedNum = (operationType==0) ? num*k : num/k;

        // Every state may start
        // a completely new subarray
        // from the current index.
        //
        // If operation has already ended,
        // only the original value is allowed.
        long maxSum = (state==0 || state==1) ? modifiedNum : num;

        // Pre-compute every possible future state.
        long operationNotStarted = recursion(nums, k, i+1, operationType, 0, memo, maxSubarraySum);
        long operationStarted = recursion(nums, k, i+1, operationType, 1, memo, maxSubarraySum);
        long operationEnded = recursion(nums, k, i+1, operationType, 2, memo, maxSubarraySum);

        if(state==0) {

            // Operation has not started yet.
            //
            // Four possibilities:
            //
            // 1. Keep current element unchanged
            //    and still don't start operation.
            //
            // 2. Keep current element unchanged,
            //    but start operation later.
            //
            // 3. Start operation at current element.
            //
            // 4. Start and immediately finish
            //    the operation at current element.
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
        }
        else if(state==1) {

            // Operation is currently active.
            //
            // Two possibilities:
            //
            // 1. Continue applying operation.
            //
            // 2. End operation after
            //    the current element.
            long maxResult =
                modifiedNum +
                Math.max(
                    operationStarted,
                    operationEnded
                );

            maxSum = Math.max(maxSum, maxResult);
        }
        else {

            // Operation has already finished.
            //
            // Remaining elements
            // must stay unchanged.
            maxSum = Math.max(
                maxSum,
                num + operationEnded
            );
        }

        memo[i][operationType][state] = maxSum;

        // Update global answer
        // because optimal subarray
        // may start at any index.
        maxSubarraySum[0] = Math.max(
            maxSubarraySum[0],
            maxSum
        );

        return maxSum;
    }
}

class Solution2 {

    public long maxSubarraySum(int[] nums, int k) {

        int n = nums.length;

        // dp[i][operationType][state]:
        //
        // stores the maximum subarray sum
        // starting exactly from index i
        // for the given operation type
        // and operation state.
        long[][][] dp = new long[n+1][2][3];

        long answer = Long.MIN_VALUE;

        // dp[n][*][*] = 0
        //
        // No elements remain,
        // so future contribution is zero.

        for (int i=n-1; i>=0; i--) {

            for (int operationType=0; operationType<2; operationType++) {

                long num = nums[i];

                // Compute modified value
                // according to chosen operation.
                long modifiedNum = (operationType==0) ? num*k : num/k;

                // Retrieve all future states.
                long operationNotStarted = dp[i+1][operationType][0];
                long operationStarted = dp[i+1][operationType][1];
                long operationEnded = dp[i+1][operationType][2];

                // ---------- state = 0 ----------
                //
                // Operation has not started yet.
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
                //
                // Operation is currently active.
                maxSum = modifiedNum;

                long result =
                    modifiedNum +
                    Math.max(
                        operationStarted,
                        operationEnded
                    );

                maxSum = Math.max(maxSum, result);

                dp[i][operationType][1] = maxSum;

                answer = Math.max(answer, maxSum);

                // ---------- state = 2 ----------
                //
                // Operation has already ended.
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