class Solution {
    public int findMin(int[] nums) {
        int i=0, j=nums.length-1;
        int ans = nums[0];
        while(i<=j) {
            int mid = (i+j)/2;
            int temp = nums[mid];
            if(temp>=nums[0] && temp>=nums[nums.length-1]) i = mid+1;
            else {
                ans = temp;
                j = mid-1;
            }
        }
        return ans;  
    }
}
