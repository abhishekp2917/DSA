import java.util.Arrays;

class Solution {
    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];  
        Arrays.fill(ans, 1);      
        for(int bit=0; bit<30; bit++) {
            int nearestRightSetBitIdx = n;
            for(int i=n-1; i>=0; i--) {
                int num = nums[i];
                if(((num>>bit)&1)==1) {
                    nearestRightSetBitIdx = i;
                }
                if(nearestRightSetBitIdx!=n) ans[i] = Math.max(ans[i],  nearestRightSetBitIdx-i+1);
            }
        }
        return ans;
    }
}