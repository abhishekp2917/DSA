class Solution {
    public boolean search(int[] nums, int target) {
        int i=0, j=nums.length-1;
        while(i<=j) {
            int mid = (i+j)/2;
            int temp = nums[mid];
            if(temp==target) return true;
            else if(temp>=nums[i] && temp>=nums[j]) {
                if(temp==nums[i] && temp==nums[j]) {
                    i++;
                    j--;
                }
                else if(target>=nums[i] && target<temp) j = mid-1;
                else i = mid+1;
            }
            else {
                if(target>temp && target<=nums[j]) i = mid+1;
                else j = mid-1;
            }
        }
        return false;
    }
}