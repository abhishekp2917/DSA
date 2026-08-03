class Solution1 {
    public int specialPerm(int[] nums) {
        final int MOD = 1000_000_007;
        int n = nums.length;
        long specialPermCount = 0;
        int availNumMask = (1<<n)-1;
        Long[][] memo = new Long[availNumMask+1][n];
        for(int prevIdx=0; prevIdx<n; prevIdx++) {
            int newAvailNumMask = availNumMask^(1<<prevIdx);
            specialPermCount = (specialPermCount + recursion(nums, newAvailNumMask, prevIdx, memo))%MOD;
        }
        return (int)specialPermCount;
    }

    private long recursion(int[] nums, int availNumMask, int prevIdx, Long[][] memo) {
        int n = nums.length;
        if(availNumMask==0) return 1;
        if(memo[availNumMask][prevIdx]!=null) return memo[availNumMask][prevIdx];
        long count =0;
        int prevNum = nums[prevIdx];
        for(int bit=0; bit<n; bit++) {
            int num = nums[bit];
            int isNumAvail = (availNumMask>>bit)&1;
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
        int availNumMask = (1<<n)-1;
        long[][] dp = new long[availNumMask+1][n];

        // Base case:
        // If no numbers are left, there is exactly one way to finish.
        for (int prevIdx=0; prevIdx<n; prevIdx++) {
            dp[0][prevIdx] = 1;
        }
        for (int mask=1; mask<=availNumMask; mask++) {
            for (int prevIdx=0; prevIdx<n; prevIdx++) {
                long count =0;
                int prevNum = nums[prevIdx];
                for(int bit=0; bit<n; bit++) {
                    int num = nums[bit];
                    int isNumAvail = (mask>>bit)&1;
                    if(isNumAvail==1 && (prevNum%num==0 || num%prevNum==0)) {
                        int newMask = mask^(1<<bit);
                        count += dp[newMask][bit]; 
                    }
                } 
                dp[mask][prevIdx] = count;
            }
        }
        long specialPermCount = 0;
        for (int prevIdx=0; prevIdx<n; prevIdx++) {
            int newMask = availNumMask^(1<<prevIdx);
            specialPermCount = (specialPermCount + dp[newMask][prevIdx])%MOD;
        }
        return (int)specialPermCount;
    }
}