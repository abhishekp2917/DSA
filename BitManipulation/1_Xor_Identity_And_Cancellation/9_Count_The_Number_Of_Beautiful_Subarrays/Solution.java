import java.util.HashMap;
import java.util.Map;

class Solution {

    // Pattern: Bit Manipulation → XOR Prefix + Frequency Counting
    //
    // Core Observation:
    // A subarray is "beautiful" if the XOR of all its elements is 0.
    //
    // Using prefix XOR:
    // If prefixXor[i] == prefixXor[j],
    // then XOR of subarray (i, j] is 0.
    //
    // This reduces the problem to counting
    // how many times the same prefix XOR value appears.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(n)

    public long beautifulSubarrays(int[] nums) {

        // Map to store frequency of prefix XOR values
        Map<Integer, Long> prefixXorFrequency = new HashMap<>();

        long beautifulCount = 0;
        int prefixXor = 0;

        // Base case: prefix XOR = 0 occurs once before processing any element
        prefixXorFrequency.put(0, 1L);

        for (int num : nums) {

            // Update prefix XOR
            prefixXor ^= num;

            // If this prefix XOR was seen before,
            // it forms beautiful subarrays ending here
            long frequency = prefixXorFrequency.getOrDefault(prefixXor, 0L);
            beautifulCount += frequency;

            // Update frequency map
            prefixXorFrequency.put(prefixXor, frequency + 1);
        }

        return beautifulCount;
    }
}
