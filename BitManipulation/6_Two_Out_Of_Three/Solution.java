import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        Set<Integer> ans = new HashSet<>();
        long[] num1Mask = getBitMask(nums1);
        long[] num2Mask = getBitMask(nums2);
        long[] num3Mask = getBitMask(nums3);
        getSetBitPos(num1Mask[0] & num2Mask[0], ans, -1);
        getSetBitPos(num1Mask[1] & num2Mask[1], ans, 49);
        getSetBitPos(num1Mask[0] & num3Mask[0], ans, -1);
        getSetBitPos(num1Mask[1] & num3Mask[1], ans, 49);
        getSetBitPos(num2Mask[0] & num3Mask[0], ans, -1);
        getSetBitPos(num2Mask[1] & num3Mask[1], ans, 49);
        return new ArrayList<>(ans);

    }

    private void getSetBitPos(long num, Set<Integer> list, int start) {
        int pos = start;
        while(num>0) {
            pos++;
            if((num&1)==1) {
                list.add(pos);
            }
            num >>= 1;
        }
    }

    private long[] getBitMask(int[] nums) {
        long[] mask = new long[2];
        for(int num : nums) {
            if(num>50) {
                mask[1] = setKthBit(mask[1], num-50);
            }
            else {
                mask[0] = setKthBit(mask[0], num);
            }
        }
        return mask;
    }

    private long setKthBit(long num, int k) {
        return num | (1L << k);
    }
}

