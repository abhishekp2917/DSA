class Solution1 {

    public int maxOperations(int[] nums) {

        int n = nums.length;

        int maxOperation = 1;

        // The first operation completely determines
        // the target score that every future operation
        // must produce.
        //
        // Only three possible first moves exist:
        //
        // 1. Remove first two elements.
        // 2. Remove last two elements.
        // 3. Remove first and last element.
        int firstTwoSum = nums[0] + nums[1];
        int lastTwoSum = nums[n-2] + nums[n-1];
        int firstLastSum = nums[0] + nums[n-1];

        // Try all three possible starting moves
        // because each creates a different target score.
        maxOperation = Math.max(
            1 + recursion(nums, firstTwoSum, 2, n-1, new Integer[n][n]),
            Math.max(
                1 + recursion(nums, lastTwoSum, 0, n-3, new Integer[n][n]),
                1 + recursion(nums, firstLastSum, 1, n-2, new Integer[n][n])
            )
        );

        return maxOperation;
    }

    private int recursion(int[] nums, int target, int start, int end, Integer[][] memo) {

        // Fewer than two elements remain,
        // so no further operation is possible.
        if(start>=end) return 0;

        // Exactly one pair remains.
        //
        // It contributes one operation
        // only if its sum matches
        // the required target.
        if(end-start==1) {
            if((nums[start] + nums[end])==target) return 1;
            else return 0;
        }

        // Reuse previously solved interval.
        if(memo[start][end]!=null) return memo[start][end];

        int maxOperation = 0;

        // These are the only three legal removals
        // that preserve the remaining array
        // as one contiguous interval.
        int firstTwoSum = nums[start] + nums[start+1];
        int lastTwoSum = nums[end-1] + nums[end];
        int firstLastSum = nums[start] + nums[end];

        // Remove first two elements
        // if their sum matches the target.
        if(firstTwoSum==target) {
            maxOperation = Math.max(
                maxOperation,
                1 + recursion(nums, target, start+2, end, memo)
            );
        }

        // Remove last two elements
        // if their sum matches the target.
        if(lastTwoSum==target) {
            maxOperation = Math.max(
                maxOperation,
                1 + recursion(nums, target, start, end-2, memo)
            );
        }

        // Remove first and last elements
        // if their sum matches the target.
        if(firstLastSum==target) {
            maxOperation = Math.max(
                maxOperation,
                1 + recursion(nums, target, start+1, end-1, memo)
            );
        }

        memo[start][end] = maxOperation;

        return maxOperation;
    }
}


class Solution2 {

    public int maxOperations(int[] nums) {

        int n = nums.length;

        // The first operation fixes
        // the target score permanently.
        //
        // Therefore solve the DP
        // independently for all
        // three possible starting moves.
        int firstTwoSum = nums[0] + nums[1];
        int lastTwoSum = nums[n-2] + nums[n-1];
        int firstLastSum = nums[0] + nums[n-1];

        return Math.max(
            1 + tabulation(nums, firstTwoSum, 2, n-1),
            Math.max(
                1 + tabulation(nums, lastTwoSum, 0, n-3),
                1 + tabulation(nums, firstLastSum, 1, n-2)
            )
        );
    }

    private int tabulation(int[] nums, int target, int start, int end) {

        // No valid pair exists,
        // so no operation can be performed.
        if(start>=end) return 0;

        // We only build DP
        // for the required subarray
        // instead of the entire array,
        // reducing unnecessary memory usage.
        int len = end-start+1;

        // dp[left][right]:
        // stores the maximum operations
        // possible using the interval
        // represented by
        // nums[start+left ... start+right].
        int[][] dp = new int[len][len];

        // Build intervals
        // from smaller lengths
        // towards larger lengths
        // because every transition
        // depends only on smaller intervals.
        for(int currLen=2; currLen<=len; currLen++) {

            for(int left=0; left+currLen-1<len; left++) {

                int right = left+currLen-1;

                // Convert local indices
                // back to original array indices
                // whenever values are accessed.
                int currStart = start+left;
                int currEnd = start+right;

                // Base case:
                // only one pair remains.
                if(currLen==2) {
                    dp[left][right] =
                        ((nums[currStart]+nums[currEnd])==target)?1:0;
                    continue;
                }

                int maxOperation = 0;

                // These are the only three legal removals
                // because the remaining elements
                // must stay contiguous.
                int firstTwoSum = nums[currStart]+nums[currStart+1];
                int lastTwoSum = nums[currEnd-1]+nums[currEnd];
                int firstLastSum = nums[currStart]+nums[currEnd];

                // Remove first two elements
                // if they produce
                // the required target score.
                if(firstTwoSum==target) {
                    maxOperation = Math.max(
                        maxOperation,
                        1 + dp[left+2][right]
                    );
                }

                // Remove last two elements
                // if they produce
                // the required target score.
                if(lastTwoSum==target) {
                    maxOperation = Math.max(
                        maxOperation,
                        1 + dp[left][right-2]
                    );
                }

                // Remove first and last elements
                // if they produce
                // the required target score.
                if(firstLastSum==target) {
                    maxOperation = Math.max(
                        maxOperation,
                        1 + dp[left+1][right-1]
                    );
                }

                dp[left][right] = maxOperation;
            }
        }

        // Entire required subarray
        // corresponds to local interval
        // [0 ... len-1].
        return dp[0][len-1];
    }
}