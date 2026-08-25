import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    // Pattern: Bit Manipulation → Bit Mask Aggregation Across Multiple Arrays
    //
    // Goal:
    // Find all numbers that appear in at least two of the three arrays.

    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {

        // Map to store bitmask representing
        // presence of a number across arrays
        Map<Integer, Integer> map = new HashMap<>();

        // Mark presence in nums1 using bit 0 (001)
        for (int n : nums1) {
            map.put(n, map.getOrDefault(n, 0) | 1);
        }

        // Mark presence in nums2 using bit 1 (010)
        for (int n : nums2) {
            map.put(n, map.getOrDefault(n, 0) | 2);
        }

        // Mark presence in nums3 using bit 2 (100)
        for (int n : nums3) {
            map.put(n, map.getOrDefault(n, 0) | 4);
        }

        List<Integer> ans = new ArrayList<>();

        // Check how many arrays each number appears in
        for (int num : map.keySet()) {
            int mask = map.get(num);

            // If number appears in at least two arrays,
            // the bit count of the mask will be >= 2
            if (Integer.bitCount(mask) >= 2) {
                ans.add(num);
            }
        }

        return ans;
    }
}
