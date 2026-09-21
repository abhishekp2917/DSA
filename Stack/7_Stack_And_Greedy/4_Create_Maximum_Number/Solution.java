import java.util.*;

class Solution {

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {

        int n = nums1.length;
        int m = nums2.length;

        // Final answer
        int[] maxNums = new int[k];

        // --------------------------------------------------
        // STEP 1: Try all possible splits
        // --------------------------------------------------
        //
        // k1 → how many elements from nums1
        // k2 → from nums2
        //
        // Constraints:
        //   k1 ≤ n
        //   k2 ≤ m
        int minK1 = Math.max(0, k - m);
        int maxK1 = Math.min(k, n);

        for (int k1 = minK1; k1 <= maxK1; k1++) {

            int k2 = k - k1;

            // --------------------------------------------------
            // STEP 2: Get best subsequences from both arrays
            // --------------------------------------------------
            int[] part1 = getNumsOfLengthK(nums1, k1);
            int[] part2 = getNumsOfLengthK(nums2, k2);

            // --------------------------------------------------
            // STEP 3: Merge to form largest possible number
            // --------------------------------------------------
            int[] candidate = merge(part1, part2);

            // --------------------------------------------------
            // STEP 4: Keep best answer
            // --------------------------------------------------
            if (greater(candidate, 0, maxNums, 0)) {
                maxNums = candidate;
            }
        }

        return maxNums;
    }


    // ======================================================
    // SUBPROBLEM 1:
    // Get maximum subsequence of length k
    // ======================================================
    //
    // Monotonic decreasing stack
    //
    // SAME pattern as:
    //   - mostCompetitive (reverse logic)
    //   - removeKdigits (maximize instead of minimize)
    //
    private int[] getNumsOfLengthK(int[] nums, int k) {

        int n = nums.length;

        // How many elements we can remove
        int toRemove = n - k;

        Deque<Integer> stack = new ArrayDeque<>();

        for (int num : nums) {

            // --------------------------------------------------
            // Remove smaller elements to maximize number
            // --------------------------------------------------
            while (!stack.isEmpty() &&
                   stack.peekLast() < num &&
                   toRemove > 0) {

                stack.removeLast();
                toRemove--;
            }

            stack.addLast(num);
        }

        // Remove extra elements from end
        while (toRemove > 0) {
            stack.removeLast();
            toRemove--;
        }

        // Build result
        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = stack.removeFirst();
        }

        return result;
    }


    // ======================================================
    // SUBPROBLEM 2:
    // Merge two arrays into lexicographically largest
    // ======================================================
    //
    // KEY TRICK:
    // Compare remaining suffixes
    //
    private int[] merge(int[] nums1, int[] nums2) {

        int n = nums1.length;
        int m = nums2.length;

        int[] result = new int[n + m];

        int i = 0, j = 0, k = 0;

        while (i < n || j < m) {

            // Choose from the array which gives larger suffix
            if (greater(nums1, i, nums2, j)) {
                result[k++] = nums1[i++];
            } else {
                result[k++] = nums2[j++];
            }
        }

        return result;
    }


    // ======================================================
    // SUBPROBLEM 3:
    // Compare two arrays lexicographically from index
    // ======================================================
    //
    // Returns true if:
    //   a[i...] > b[j...]
    //
    private boolean greater(int[] a, int i, int[] b, int j) {

        // Skip equal prefix
        while (i < a.length &&
               j < b.length &&
               a[i] == b[j]) {

            i++;
            j++;
        }

        // If b finished → a is larger
        if (j == b.length) return true;

        // If a finished → b is larger
        if (i == a.length) return false;

        // Compare first different digit
        return a[i] > b[j];
    }
}