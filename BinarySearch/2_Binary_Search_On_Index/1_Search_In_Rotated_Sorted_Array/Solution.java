class Solution {
    public int search(int[] nums, int target) {
        int i=0, j=nums.length-1;
        while(i<=j) {
            int mid = (i+j)/2;
            int temp = nums[mid];
            if(temp==target) return mid;
            else if(temp>=nums[0] && temp>nums[nums.length-1]) {
                if(target>=nums[0] && target<temp) j = mid-1;
                else i = mid+1;
            }
            else {
                if(target>temp && target<=nums[nums.length-1]) i = mid+1;
                else j = mid-1;
            }
        }
        return -1;
    }
}