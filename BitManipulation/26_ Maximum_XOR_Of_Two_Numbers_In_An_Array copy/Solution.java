import java.util.HashSet;
import java.util.Set;

class Solution {
    public int findMaximumXOR(int[] nums) {
        int maxXor = 0;
        int bitMask = 0;
        for(int i=31; i>=0; i--) {
            bitMask |= (1<<i);
            Set<Integer> masks = new HashSet<>();
            for(int num : nums) masks.add(num&bitMask);
            int tempXor = maxXor | (1<<i);
            for(int mask : masks) {
                if(masks.contains(mask^tempXor)) {
                    maxXor = tempXor;
                    break;
                }
            }
        }
        return maxXor;
    }
}

