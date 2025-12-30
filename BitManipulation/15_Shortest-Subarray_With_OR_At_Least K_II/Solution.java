class Solution {
    public int minimumSubarrayLength(int[] nums, int k) {
        int n = nums.length;
        int len = Integer.MAX_VALUE; 
        int or = 0; 
        int[] setBitsCount = new int[30];
        int start = 0;
        for(int i=0; i<nums.length; i++) {
            for(int bit=0; bit<30; bit++) {
                setBitsCount[bit] += (nums[i]>>bit)&1;
                or |= (((nums[i]>>bit)&1)<<bit);
            }      
            while(or>=k && start<=i) {
                len = Math.min(len, i-start+1);
                for(int bit=0; bit<30; bit++) {
                    setBitsCount[bit] -= (nums[start]>>bit)&1;
                    if(setBitsCount[bit]==0) or &= ~(1<<bit);
                }  
                start++;
            } 
        }
        return (len!=Integer.MAX_VALUE)?len:-1;
    }
}