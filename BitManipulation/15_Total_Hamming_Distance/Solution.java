class Solution {
    public int totalHammingDistance(int[] nums) {
        int sum = 0;
        for(int i=0; i<32; i++) {
            int setBitCount = 0;
            for(int j=0; j<nums.length; j++) {
                if(((nums[j]>>i)&1)==0) {
                    sum += setBitCount;
                }
                else {
                    sum += (j-setBitCount);
                }
                setBitCount += ((nums[j]>>i)&1);
            }   
        }
        return sum;
    }
}

