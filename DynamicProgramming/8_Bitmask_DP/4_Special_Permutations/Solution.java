class Solution1 {

    public int specialPerm(int[] nums) {

        final int MOD = 1000_000_007;

        int n = nums.length;

        long specialPermCount = 0;

        // Initially,
        // every number
        // is available.
        int availNumMask = (1<<n)-1;

        // memo[mask][prevIdx]:
        // stores the number of valid permutations
        // that can be formed
        // using the remaining numbers in 'mask',
        // when the previously chosen number
        // is nums[prevIdx].
        Long[][] memo = new Long[availNumMask+1][n];

        // Every number
        // can be chosen
        // as the starting element
        // because there is no previous number
        // to satisfy the divisibility condition.
        for(int prevIdx=0; prevIdx<n; prevIdx++) {

            int newAvailNumMask = availNumMask^(1<<prevIdx);

            specialPermCount =
                (specialPermCount +
                recursion(nums, newAvailNumMask, prevIdx, memo))
                %MOD;
        }

        return (int)specialPermCount;
    }

    private long recursion(int[] nums, int availNumMask, int prevIdx, Long[][] memo) {

        int n = nums.length;

        // Every number
        // has already been placed,
        // so one valid permutation
        // has been formed.
        if(availNumMask==0) return 1;

        // Reuse previously computed state.
        if(memo[availNumMask][prevIdx]!=null) return memo[availNumMask][prevIdx];

        long count = 0;

        int prevNum = nums[prevIdx];

        // Try placing
        // every remaining number next.
        for(int bit=0; bit<n; bit++) {

            int num = nums[bit];

            int isNumAvail = (availNumMask>>bit)&1;

            // The chosen number
            // must still be available
            // and satisfy the divisibility rule.
            if(isNumAvail==1 && (prevNum%num==0 || num%prevNum==0)) {

                int newAvailNumMask = availNumMask^(1<<bit);

                count += recursion(nums, newAvailNumMask, bit, memo);
            }
        }

        memo[availNumMask][prevIdx] = count;

        return count;
    }
}

class Solution2 {

    public int specialPerm(int[] nums) {

        final int MOD = 1_000_000_007;

        int n = nums.length;

        // Initially,
        // every number
        // is still available.
        int availNumMask = (1<<n)-1;

        // dp[mask][prevIdx]:
        // stores the number of valid permutations
        // that can be formed
        // using the remaining numbers in 'mask',
        // when the previously selected number
        // is nums[prevIdx].
        long[][] dp = new long[availNumMask+1][n];

        // Base case:
        // Once every number
        // has already been placed,
        // exactly one valid completion exists.
        for(int prevIdx=0; prevIdx<n; prevIdx++) {
            dp[0][prevIdx] = 1;
        }

        // Build states
        // from smaller masks
        // towards larger masks
        // because every transition
        // removes one number.
        for(int mask=1; mask<=availNumMask; mask++) {

            for(int prevIdx=0; prevIdx<n; prevIdx++) {

                long count = 0;

                int prevNum = nums[prevIdx];

                // Try choosing
                // every remaining number
                // as the next element.
                for(int bit=0; bit<n; bit++) {

                    int num = nums[bit];

                    int isNumAvail = (mask>>bit)&1;

                    // Next number
                    // must satisfy
                    // the divisibility condition.
                    if(isNumAvail==1 && (prevNum%num==0 || num%prevNum==0)) {

                        int newMask = mask^(1<<bit);

                        count += dp[newMask][bit];
                    }
                }

                dp[mask][prevIdx] = count;
            }
        }

        long specialPermCount = 0;

        // Try every possible
        // starting number.
        for(int prevIdx=0; prevIdx<n; prevIdx++) {

            int newMask = availNumMask^(1<<prevIdx);

            specialPermCount =
                (specialPermCount +
                dp[newMask][prevIdx])
                %MOD;
        }

        return (int)specialPermCount;
    }
}