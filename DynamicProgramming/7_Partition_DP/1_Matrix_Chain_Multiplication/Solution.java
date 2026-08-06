class Solution1 {

    static int matrixMultiplication(int matrices[]) {

        int n = matrices.length;

        // memo[start][end]:
        // stores the minimum number of scalar multiplications
        // needed to multiply all matrices represented by
        // dimensions from matrices[start] to matrices[end].
        Integer[][] memo = new Integer[n][n];

        return recursion(matrices, 0, n-1, memo);
    }

    private static int recursion(int matrices[], int start, int end, Integer[][] memo) {

        // If there is only one matrix,
        // no multiplication is required.
        //
        // Example:
        // dimensions = [10,20]
        // represents a single 10x20 matrix.
        if(end-start==1) return 0;

        // Reuse previously computed state.
        if(memo[start][end]!=null) return memo[start][end];

        int numOfMultiply = Integer.MAX_VALUE;

        // Try every possible position
        // to split the matrix chain.
        for(int partitionIdx=start+1; partitionIdx<end; partitionIdx++) {

            // Cost of multiplying
            // the left sub-chain.
            int leftMultiplyCount = recursion(matrices, start, partitionIdx, memo);

            // Cost of multiplying
            // the right sub-chain.
            int rightMultiplyCount = recursion(matrices, partitionIdx, end, memo);

            // After both sub-chains become single matrices,
            // multiply the two resulting matrices together.
            //
            // Left matrix dimensions:
            // matrices[start] × matrices[partitionIdx]
            //
            // Right matrix dimensions:
            // matrices[partitionIdx] × matrices[end]
            int leftRightMultiplyCount = matrices[start]*matrices[partitionIdx]*matrices[end];

            // Choose the partition
            // producing the minimum total cost.
            numOfMultiply = Math.min(
                numOfMultiply,
                leftRightMultiplyCount + leftMultiplyCount + rightMultiplyCount
            );
        }

        memo[start][end] = numOfMultiply;

        return numOfMultiply;
    }
}

class Solution2 {

    static int matrixMultiplication(int matrices[]) {

        int n = matrices.length;

        // dp[start][end]:
        // stores the minimum number of scalar multiplications
        // needed to multiply all matrices represented by
        // dimensions from matrices[start] to matrices[end].
        int[][] dp = new int[n][n];

        // Build smaller chains first,
        // because larger chains depend on them.
        for(int start=n-1; start>=0; start--) {

            // A valid chain must contain
            // at least one matrix,
            // i.e. end-start >= 2.
            for(int end=start+2; end<n; end++) {

                int numOfMultiply = Integer.MAX_VALUE;

                // Try every possible partition
                // between the first and last dimension.
                for(int partitionIdx=start+1; partitionIdx<end; partitionIdx++) {

                    // Minimum cost
                    // for the left sub-chain.
                    int leftMultiplyCount = dp[start][partitionIdx];

                    // Minimum cost
                    // for the right sub-chain.
                    int rightMultiplyCount = dp[partitionIdx][end];

                    // Cost of multiplying
                    // the two resulting matrices together.
                    int leftRightMultiplyCount = matrices[start]*matrices[partitionIdx]*matrices[end];

                    // Keep the partition
                    // giving the minimum total cost.
                    numOfMultiply = Math.min(
                        numOfMultiply,
                        leftRightMultiplyCount + leftMultiplyCount + rightMultiplyCount
                    );
                }

                dp[start][end] = numOfMultiply;
            }
        }

        // Entire matrix chain.
        return dp[0][n-1];
    }
}