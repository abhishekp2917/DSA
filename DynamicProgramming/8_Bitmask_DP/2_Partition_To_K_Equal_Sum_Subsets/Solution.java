import java.util.Arrays;

class Solution1 {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int n = nums.length;
        int total = Arrays.stream(nums).sum();
        if(total%k!=0) return false;
        int targetSum = total/k;
        int availNumMask = (1<<n)-1;
        Boolean[] memo = new Boolean[availNumMask+1];
        return recursion(nums, targetSum, availNumMask, 0, memo);
    }

    private boolean recursion(int[] nums, int targetSum, int availNumMask, int currSum, Boolean[] memo) {
        int n = nums.length;
        if(availNumMask==0) return true;
        if(memo[availNumMask]!=null) return memo[availNumMask];
        boolean isPoss = false; 
        for(int bit=0; bit<n; bit++) {
            int bitValue = (availNumMask>>bit)&1;
            int num = nums[bit];
            if(bitValue==1 && (currSum+num)<=targetSum) {
                int newAvailNumMask = availNumMask^(1<<bit);
                int newSum = (currSum+num)%targetSum;
                isPoss |= recursion(nums, targetSum, newAvailNumMask, newSum, memo);
            }
        }
        memo[availNumMask] = isPoss;
        return isPoss;
    }
}

class Solution2 {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int n = nums.length;
        int total = Arrays.stream(nums).sum();
        if(total%k!=0) return false;
        int targetSum = total/k;
        int availNumMask = (1<<n)-1;
        Boolean[] dp = new Boolean[availNumMask+1];
        dp[0] = true;
        for(int mask=1; mask<=availNumMask; mask++) {
            boolean isPoss = false; 
            int currSum = getCurrSum(nums, mask)%targetSum;
            for(int bit=0; bit<n; bit++) {
                int bitValue = (mask>>bit)&1;
                int num = nums[bit];
                if(bitValue==1 && (currSum+num)<=targetSum) {
                    int newMask = mask^(1<<bit);
                    isPoss |= dp[newMask];
                }
            }
            dp[mask] = isPoss;
        }
        return dp[availNumMask];
    }

    private int getCurrSum(int[] nums, int availNumMask) {
        int n = nums.length;
        int currSum = 0;
        for(int bit=0; bit<n; bit++) {
            int bitValue = (availNumMask>>bit)&1;
            int num = nums[bit];
            if(bitValue==0) currSum += num;
        }
        return currSum;
    }
}





