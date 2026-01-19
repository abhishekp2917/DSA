class Solution {
    public int findPeakElement(int[] nums) {
        int i=0, j=nums.length-1;
        while(i<=j) {
            int mid = (i+j)/2;
            long left = (mid-1<0)?Long.MIN_VALUE:nums[mid-1];
            long right = (mid+1==nums.length)?Long.MIN_VALUE:nums[mid+1];
            int curr = nums[mid];
            if(curr>left && curr>right) return mid;
            else if(curr<right) i = mid+1;
            else j = mid-1;
        }
        return -1;
    }
}