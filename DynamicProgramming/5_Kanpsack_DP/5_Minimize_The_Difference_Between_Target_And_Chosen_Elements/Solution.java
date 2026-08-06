class Solution1 {

    public int minimizeTheDifference(int[][] matrix, int target) {

        int n = matrix.length;

        // Constraints guarantee that
        // maximum possible sum is:
        //
        // 70 rows × 70 value = 4900
        //
        // memo[row][currSum]:
        // stores the minimum absolute difference
        // obtainable after processing rows
        // starting from 'row',
        // when the current accumulated sum
        // equals currSum.
        //
        // Memoization avoids solving
        // identical states repeatedly.
        Integer[][] memo = new Integer[n+1][4901];

        return recursion(matrix, 0, target, 0, memo);
    }

    private int recursion(int[][] matrix, int row, int target, int currSum, Integer[][] memo) {

        // Base Case:
        //
        // One element has been chosen
        // from every row.
        //
        // Current sum is now fixed,
        // so compute its distance
        // from the target.
        if(row==matrix.length) {
            return Math.abs(target-currSum);
        }

        // Return previously computed answer.
        if(memo[row][currSum]!=null) {
            return memo[row][currSum];
        }

        int minAbsDiff = Integer.MAX_VALUE;

        // Current row allows choosing
        // exactly one element.
        //
        // Try every possible choice.
        for(int col=0; col<matrix[0].length; col++) {

            minAbsDiff = Math.min(
                minAbsDiff,
                recursion(
                    matrix,
                    row+1,
                    target,
                    currSum + matrix[row][col],
                    memo
                )
            );
        }

        // Store the best possible answer
        // among all choices of this row.
        memo[row][currSum] = minAbsDiff;

        return memo[row][currSum];
    }
}

class Solution2 {

    public int minimizeTheDifference(int[][] matrix, int target) {

        int n = matrix.length;
        int m = matrix[0].length;

        // dp[row][currSum]:
        // stores the minimum absolute difference
        // obtainable after processing rows
        // starting from 'row',
        // when the accumulated sum so far
        // equals currSum.
        int[][] dp = new int[n+1][4901];

        // Base Case:
        //
        // Every row has already been processed.
        //
        // Current accumulated sum
        // becomes the final sum.
        for(int currSum=0; currSum<=4900; currSum++) {

            dp[n][currSum] =
                Math.abs(target-currSum);
        }

        // Build answers
        // from the last row
        // towards the first row.
        for(int row=n-1; row>=0; row--) {

            for(int currSum=4900; currSum>=0; currSum--) {

                int minAbsDiff = Integer.MAX_VALUE;

                // Try selecting every element
                // from the current row.
                for(int col=0; col<m; col++) {

                    int num = matrix[row][col];

                    // Add current element
                    // to the accumulated sum
                    // and continue
                    // with remaining rows.
                    //
                    // Ignore states
                    // exceeding the maximum
                    // possible sum.
                    minAbsDiff = Math.min(
                        minAbsDiff,
                        (currSum+num<=4900)
                            ? dp[row+1][currSum+num]
                            : Integer.MAX_VALUE
                    );
                }

                // Store the best choice
                // for this state.
                dp[row][currSum] = minAbsDiff;
            }
        }

        // Initially,
        // no rows have been processed
        // and accumulated sum is zero.
        return dp[0][0];
    }
}