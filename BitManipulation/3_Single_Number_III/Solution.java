class Solution {
    public int[] singleNumber(int[] nums) {
        int xor = 0;
        int[] ans = new int[2];
        // find the XOR of all the elements
        // since only two num are distinct, we will get XOR of those two num.
        for(int num : nums) xor ^= num;
        // get the lowest set bit which is the lowest bit at which both num differs
        // we could have used any set bit, but getting lowest set bit is quite fast and easy 
        int lowestSetBit = xor & (-xor);
        // we will divide array into two groups based on this lowest set bit position
        // i.e. one which has bit set at this position and one which is clear
        // this way both distinct num will be seperated in two different groups and if we find XOR of these group separately, all the num will cancel out except the single number in each group.
        for(int num : nums) {
            // if bit is clear, move it to group-0
            if((num&lowestSetBit)==0) {
                ans[0] ^= num;
            }
            // else move it to group-1
            else ans[1] ^= num;
        }
        return ans;
    }
}

