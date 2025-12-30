class Solution {
    public int longestNiceSubarray(int[] nums) {
        int maxLen = 0;
        int or = 0;
        int start=0, end=0;
        while(end<nums.length) {
            while(start<=end && (nums[end]&or)!=0) {
                or ^= nums[start++];
            }
            or |= nums[end];
            maxLen = Math.max(maxLen, end-start+1);
            end++;
        }
        return maxLen;
    }
}

