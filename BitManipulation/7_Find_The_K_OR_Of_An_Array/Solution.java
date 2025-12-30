class Solution {
    public int findKOr(int[] nums, int k) {
        int[] setBitCount = new int[31];
        int ans = 0;
        for(int i=0; i<31; i++) {
            for(int num : nums) {
                setBitCount[i] += isKthBitSet(num, i);
            }
            if(setBitCount[i]>=k) {
                ans = setKthBit(ans, i);
            }
        }
        return ans;
    }

    private int setKthBit(int n, int k) {
        int mask = (1<<k);
        return n | mask;
    }

    private int isKthBitSet(int n, int k) {
        return (n>>k) & 1;
    }
}
