import java.util.HashMap;
import java.util.List;

class Solution {
    public int longestEqualSubarray(List<Integer> nums, int k) {

        // We want to find the MAXIMUM possible length of a subarray
        // such that after deleting at most k elements,
        // all remaining elements are equal.

        // Instead of trying all sizes, we binary search on the ANSWER (length)

        // Minimum possible equal subarray length = 1
        int i = 1;

        // Maximum possible length = nums.size()
        int j = nums.size();

        // This will store the best valid length found so far
        int ans = 0;

        // Binary search on the possible length of equal subarray
        while (i <= j) {

            // Try a candidate length = mid
            int mid = (i + j) / 2;

            // Check if it is possible to form an equal subarray of size 'mid'
            // by deleting at most k elements
            if (isPoss(nums, k, mid)) {

                // If possible, store it as answer
                ans = mid;

                // Try to find a larger valid size
                i = mid + 1;
            }

            // If not possible, size is too large
            // We must try smaller sizes
            else {
                j = mid - 1;
            }
        }

        // ans stores the maximum possible equal subarray length
        return ans;
    }

    // This function checks:
    // Is there any subarray such that:
    //   - Some number appears 'size' times
    //   - Total window length <= size + k
    //     (meaning at most k deletions are needed)
    private boolean isPoss(List<Integer> list, int k, int size) {

        // Frequency map to track counts of numbers in current window
        HashMap<Integer, Integer> map = new HashMap<>();

        // Sliding window pointers
        int i = 0, j = 0;

        while (j < list.size()) {

            // Add current element into frequency map
            map.put(list.get(j), map.getOrDefault(list.get(j), 0) + 1);

            // If any number appears at least 'size' times in the window,
            // then we can delete the remaining elements (at most k)
            // and form an equal subarray of length 'size'
            if (map.get(list.get(j)) >= size) {
                return true;
            }

            // Window size is (j - i + 1)
            // To keep deletions <= k, window length must satisfy:
            //      window_length - max_frequency <= k
            //
            // Since we are checking for max_frequency >= size,
            // we enforce:
            //      window_length <= size + k
            //
            // If window grows beyond (size + k),
            // we must shrink from the left
            if (j - i + 1 >= k + size) {

                // Remove left element from the window
                map.put(list.get(i), map.get(list.get(i)) - 1);

                // Clean up zero frequency to keep map small
                if (map.get(list.get(i)) == 0) {
                    map.remove(list.get(i));
                }

                // Move left pointer forward
                i++;
            }

            // Move right pointer forward
            j++;
        }

        // If no valid window found, return false
        return false;
    }
}
