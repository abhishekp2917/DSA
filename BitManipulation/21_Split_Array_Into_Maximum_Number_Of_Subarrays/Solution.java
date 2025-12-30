class Solution {
    public int maxSubarrays(int[] nums) {
        int count = 0;
        int minAnd = nums[0];
        for(int i=0; i<nums.length; i++) minAnd &= nums[i];
        if(minAnd!=0) return 1;
        int and = nums[0];
        for(int i=0; i<nums.length; i++) {
            and &= nums[i];
            if(and==0) {
                count++;
                if(i+1<nums.length) and = nums[i+1];
            }
        }
        return count;
    }
}