class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int[] ans = new int[nums.size()];
        for(int i=0; i<nums.size(); i++) {
            int num = nums.get(i);
            if((num&1)==0) ans[i] = -1;
            else {
                int leastClearBit = (num+1)&(-(num+1));
                ans[i] = num^(leastClearBit>>1);
            }
        }
        return ans;
    }
}
