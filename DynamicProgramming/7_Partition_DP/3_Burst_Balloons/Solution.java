class Solution1 {

    public int maxCoins(int[] nums) {

        int n = nums.length;

        // memo[start][end]:
        // stores the maximum coins obtainable
        // by bursting every balloon
        // inside the interval [start,end].
        Integer[][] memo = new Integer[n][n];

        return recursion(nums, 0, n-1, memo);
    }

    private int recursion(int[] nums, int start, int end, Integer[][] memo) {

        // No balloons remain.
        if(start>end) return 0;

        // Reuse previously computed interval.
        if(memo[start][end]!=null) return memo[start][end];

        int maxCoins = 0;

        // IMPORTANT:
        // Assume balloon i
        // is burst LAST
        // inside this interval.
        //
        // Thinking "last burst"
        // makes the left and right intervals
        // completely independent.
        for(int i=start; i<=end; i++) {

            // Since i is the LAST balloon,
            // every balloon inside
            // [start,end]
            // except i
            // has already disappeared.
            //
            // Therefore,
            // the immediate neighbours
            // become the balloons
            // just outside the interval.
            int leftVal = ((start-1>=0)? nums[start-1] : 1);
            int rightVal = ((end+1<nums.length)? nums[end+1] : 1);

            int currVal = nums[i];

            // Coins earned
            // when i is burst last.
            int currCoins = leftVal*currVal*rightVal;

            // Solve left interval independently.
            int leftCoins = recursion(nums, start, i-1, memo);

            // Solve right interval independently.
            int rightCoins = recursion(nums, i+1, end, memo);

            maxCoins = Math.max(
                maxCoins,
                currCoins + leftCoins + rightCoins
            );
        }

        memo[start][end] = maxCoins;

        return maxCoins;
    }
}

class Solution2 {

    public int maxCoins(int[] nums) {

        int n = nums.length;

        // dp[start][end]:
        // stores the maximum coins obtainable
        // by bursting every balloon
        // inside interval [start,end].
        int[][] dp = new int[n][n];

        // Build smaller intervals first,
        // because larger intervals
        // depend on them.
        for(int start=n-1; start>=0; start--) {

            for(int end=start; end<n; end++) {

                int maxCoins = 0;

                // Try every balloon
                // as the LAST balloon
                // to burst.
                for(int i=start; i<=end; i++) {

                    int leftVal = ((start-1>=0)? nums[start-1] : 1);
                    int rightVal = ((end+1<nums.length)? nums[end+1] : 1);

                    int currVal = nums[i];

                    // Coins obtained
                    // from the final burst.
                    int currCoins = leftVal*currVal*rightVal;

                    // Maximum coins
                    // from the left interval.
                    int leftCoins = (i-1>=start)? dp[start][i-1] : 0;

                    // Maximum coins
                    // from the right interval.
                    int rightCoins = (i+1<=end)? dp[i+1][end] : 0;

                    maxCoins = Math.max(
                        maxCoins,
                        currCoins + leftCoins + rightCoins
                    );
                }

                dp[start][end] = maxCoins;
            }
        }

        return dp[0][n-1];
    }
}