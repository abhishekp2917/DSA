class Solution1 {
    static int matrixMultiplication(int matrices[]) {
        int n = matrices.length;
        Integer[][] memo = new Integer[n][n];
        return recursion(matrices, 0, n-1, memo);
    }
    
    private static int recursion(int matrices[], int start, int end, Integer[][] memo) {
        if(end-start==1) return 0;
        if(memo[start][end]!=null) return memo[start][end];
        int numOfMultiply = Integer.MAX_VALUE;
        for(int partitionIdx=start+1; partitionIdx<end; partitionIdx++) {
            int leftMultiplyCount = recursion(matrices, start, partitionIdx, memo);
            int rightMultiplyCount = recursion(matrices, partitionIdx, end, memo);
            int leftRightMultiplyCount = matrices[start]*matrices[partitionIdx]*matrices[end];
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
        int[][] dp = new int[n][n];
        for(int start=n-1; start>=0; start--) {
            for(int end=start+2; end<n; end++) {
                int numOfMultiply = Integer.MAX_VALUE;
                for(int partitionIdx=start+1; partitionIdx<end; partitionIdx++) {
                    int leftMultiplyCount = dp[start][partitionIdx];
                    int rightMultiplyCount = dp[partitionIdx][end];
                    int leftRightMultiplyCount = matrices[start]*matrices[partitionIdx]*matrices[end];
                    numOfMultiply = Math.min(
                        numOfMultiply,
                        leftRightMultiplyCount + leftMultiplyCount + rightMultiplyCount
                    );
                }
                dp[start][end] = numOfMultiply;
            }  
        }
        return dp[0][n-1];
    }
}