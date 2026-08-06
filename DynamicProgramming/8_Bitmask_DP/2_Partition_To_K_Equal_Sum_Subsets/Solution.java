import java.util.Arrays;

class Solution1 {

    public boolean canPartitionKSubsets(int[] nums, int k) {

        int n = nums.length;

        int total = Arrays.stream(nums).sum();

        // If the total sum
        // is not divisible by k,
        // equal partitions are impossible.
        if(total%k!=0) return false;

        // Every subset
        // must have this target sum.
        int targetSum = total/k;

        // Initially,
        // every number
        // is available.
        int availNumMask = (1<<n)-1;

        // memo[mask]:
        // stores whether
        // the remaining available numbers
        // represented by 'mask'
        // can complete all remaining subsets.
        //
        // currSum is intentionally omitted
        // because it is uniquely determined
        // by the mask itself.
        Boolean[] memo = new Boolean[availNumMask+1];

        return recursion(nums, targetSum, availNumMask, 0, memo);
    }

    private boolean recursion(int[] nums, int targetSum, int availNumMask, int currSum, Boolean[] memo) {

        int n = nums.length;

        // Every number
        // has been assigned successfully.
        if(availNumMask==0) return true;

        // Every mask always corresponds
        // to exactly one currSum.
        //
        // Therefore memoization
        // depends only on the mask.
        if(memo[availNumMask]!=null) return memo[availNumMask];

        boolean isPoss = false;

        // Try placing every available number
        // into the current subset.
        for(int bit=0; bit<n; bit++) {

            int bitValue = (availNumMask>>bit)&1;

            int num = nums[bit];

            // Number must be available
            // and should not overflow
            // the current subset.
            if(bitValue==1 && (currSum+num)<=targetSum) {

                // Remove the chosen number
                // from the available set.
                int newAvailNumMask = availNumMask^(1<<bit);

                // Once a subset reaches targetSum,
                // begin constructing
                // the next subset.
                //
                // Modulo automatically resets
                // the running sum to zero.
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

        // Equal partitioning
        // is impossible
        // if total is not divisible by k.
        if(total%k!=0) return false;

        int targetSum = total/k;

        // Initially,
        // every number
        // is available.
        int availNumMask = (1<<n)-1;

        // dp[mask]:
        // stores whether
        // the remaining available numbers
        // represented by 'mask'
        // can complete all remaining subsets.
        Boolean[] dp = new Boolean[availNumMask+1];

        // No numbers remain,
        // so partitioning
        // is already complete.
        dp[0] = true;

        // Build answers
        // from smaller masks
        // towards larger masks
        // because every transition
        // removes one bit.
        for(int mask=1; mask<=availNumMask; mask++) {

            boolean isPoss = false;

            // currSum represents
            // the sum accumulated
            // inside the subset
            // currently being built.
            //
            // It is obtained from
            // the numbers already removed
            // from the mask.
            int currSum = getCurrSum(nums, mask)%targetSum;

            // Try selecting
            // every available number.
            for(int bit=0; bit<n; bit++) {

                int bitValue = (mask>>bit)&1;

                int num = nums[bit];

                // Number must fit
                // into the current subset.
                if(bitValue==1 && (currSum+num)<=targetSum) {

                    // Remove this number
                    // and check whether
                    // the smaller mask
                    // is solvable.
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

        // Sum all numbers
        // that have already been removed
        // from the available set.
        for(int bit=0; bit<n; bit++) {

            int bitValue = (availNumMask>>bit)&1;

            int num = nums[bit];

            if(bitValue==0) currSum += num;
        }

        return currSum;
    }
}