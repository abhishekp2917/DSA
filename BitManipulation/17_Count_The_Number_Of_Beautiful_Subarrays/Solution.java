class Solution {
    public long beautifulSubarrays(int[] nums) {
        long ans = 0;
        int[] xorCount = new int[(1<<20)];
        xorCount[0] = 1;
        int xor = 0;
        for(int num : nums) {
            xor ^= num;
            ans += xorCount[xor];
            xorCount[xor]++;
        }
        return ans;
    }
}
