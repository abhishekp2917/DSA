class Solution1 {

    public int rob(int[] nums) {
        int n = nums.length;
        Integer[] memo = new Integer[n];
        return recursion(nums, n-1, memo);
    }

    private int recursion(int[] nums, int currHouse, Integer[] memo) {
        if (currHouse < 0) return 0;
        if (memo[currHouse]!=null) return memo[currHouse];
        int maxMoneyTillPrevHouse = recursion(nums, currHouse-1, memo);
        int maxMoneyTillBeforePrevHouse = recursion(nums, currHouse-2, memo);
        memo[currHouse] = Math.max(
            nums[currHouse] + maxMoneyTillBeforePrevHouse,
            maxMoneyTillPrevHouse
        );
        return memo[currHouse];
    }
}

class Solution2 {
    
    public int rob(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        for(int currHouse=1; currHouse<n; currHouse++) {
            int maxMoneyTillPrevHouse = (currHouse-1>=0)? dp[currHouse-1] : 0;
            int maxMoneyTillBeforePrevHouse = (currHouse-2>=0)? dp[currHouse-2] : 0;
            dp[currHouse] = Math.max( 
                nums[currHouse] + maxMoneyTillBeforePrevHouse,
                maxMoneyTillPrevHouse);
        }
        return dp[n-1];
    }
}