import java.util.Arrays;

class Solution1 {

    static Boolean isSubsetSum(int arr[], int target) {
        int n = arr.length;
        Boolean[][] memo = new Boolean[target+1][n+1];
        return recursion(arr, target, n, memo);
    }

    static boolean recursion(int[] arr, int target, int arrLen, Boolean[][] memo) {
        if (target == 0) return true;
        if (arrLen == 0) return false;
        if (memo[target][arrLen] != null) return memo[target][arrLen];
        int idx = arrLen - 1;
        int currNum = arr[idx];
        boolean exclude = recursion(arr, target, arrLen-1, memo);
        boolean include = false;
        if (target >= currNum) {
            include = recursion(arr, target-currNum, arrLen-1, memo);
        }
        memo[target][arrLen] = exclude || include;
        return memo[target][arrLen];
    }
}

class Solution2 {

    static Boolean isSubsetSum(int arr[], int target) {
        int n = arr.length;
        boolean[][] dp = new boolean[target+1][n+1];
        Arrays.fill(dp[0], true);
        for(int arrLen=1; arrLen<=n; arrLen++) {
            for(int tgt=1; tgt<=target; tgt++) {
                int idx = arrLen-1;
                int currNum = arr[idx];
                dp[tgt][arrLen] = dp[tgt][arrLen-1] || ((tgt>=currNum)? dp[tgt-currNum][arrLen-1] : false);
            }
        }
        
        return dp[target][n];
    }
}

class Solution3 {

    static Boolean isSubsetSum(int arr[], int target) {
        int n = arr.length;
        boolean[] dp = new boolean[target+1];
        dp[0] = true;
        for(int arrLen=1; arrLen<=n; arrLen++) {
            for(int tgt=target; tgt>0; tgt--) {
                int idx = arrLen-1;
                int currNum = arr[idx];
                dp[tgt] = dp[tgt] || ((tgt>=currNum)? dp[tgt-currNum] : false);
            }
        }
        
        return dp[target];
    }
}
