import java.util.Arrays;

class Solution1 {

    public int countPartitions(int[] nums, int diff) {
        int n = nums.length;
        int total = Arrays.stream(nums).sum();
        if (diff>total || (total+diff)%2 != 0) return 0;
        int target = (total+diff)/2;
        Integer[][] memo = new Integer[n+1][target+1];
        return recursion(nums, n, target, memo);
    }

    private int recursion(int[] nums, int arrLen, int target, Integer[][] memo) {
        if (arrLen==0 && target==0) return 1;
        if (arrLen == 0) return 0;
        if (memo[arrLen][target]!=null) return memo[arrLen][target];
        int idx = arrLen-1;
        int num = nums[idx];
        int exclude = recursion(nums, arrLen-1, target, memo);
        int include = 0;
        if (target >= num) {
            include = recursion(nums, arrLen-1, target-num, memo);
        }
        return memo[arrLen][target] = exclude + include;
    }
}

class Solution2 {
    
    public int countPartitions(int[] nums, int diff) {
        int n = nums.length;
        int total = 0;
        for(int num : nums) total += num;
        if(diff>total || (total+diff)%2!=0) {
            return 0;
        }
        int target = (total+diff)/2;
        int[][] dp = new int[n+1][target+1];
        dp[0][0] = 1;
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int num = nums[idx];
            for(int tgt=target; tgt>=0; tgt--) {
                dp[arrLen][tgt] = dp[arrLen-1][tgt] + ((tgt-num>=0)? dp[arrLen-1][tgt-num] : 0);
            }
        }
        return dp[n][target];
        
    }
}

class Solution3 {

    public int countPartitions(int[] nums, int diff) {
        int n = nums.length;
        int total = 0;
        for(int num : nums) total += num;
        if(diff>total || (total+diff)%2!=0) {
            return 0;
        }
        int target = (total+diff)/2;
        int[] dp = new int[target+1];
        dp[0] = 1;
        for(int arrLen=1; arrLen<=n; arrLen++) {
            int idx = arrLen-1;
            int num = nums[idx];
            for(int tgt=target; tgt>=0; tgt--) {
                dp[tgt] = dp[tgt] + ((tgt-num>=0)? dp[tgt-num] : 0);
            }
        }
        return dp[target];
        
    }
}