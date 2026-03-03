import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {

    public long maxScore(int[] nums1, int[] nums2, int k) {

        long maxScore = 0;

        int n = nums1.length;

        // Combine nums1 and nums2 into pairs.
        //
        // Why?
        // Because selection must respect same index.
        int[][] numsPair = new int[n][2];

        for (int i = 0; i < n; i++) {
            numsPair[i] = new int[]{ nums1[i], nums2[i] };
        }

        // IMPORTANT STEP:
        // Sort pairs in descending order of nums1.
        //
        // Why sort by nums1 descending?
        //
        // Final score formula:
        // score = (sum of selected nums1) * (minimum nums2 among them)
        //
        // If we fix the minimum nums2,
        // we want the sum of nums1 to be as large as possible.
        //
        // Instead of fixing min nums2,
        // we iterate possible candidates for min nums2
        // by processing in decreasing nums1 order.
        Arrays.sort(numsPair, (a, b) -> b[0] - a[0]);

        // Min heap ordered by nums2.
        //
        // Why min heap on nums2?
        // Because among selected k elements,
        // the smallest nums2 determines the multiplier.
        //
        // We want to track the smallest nums2
        // in current group of k elements.
        PriorityQueue<int[]> minNum2Heap =
            new PriorityQueue<>(
                (pair1, pair2) ->
                    pair1[1] - pair2[1]
            );

        long num1Sum = 0;

        for (int i = 0; i < n; i++) {

            // Add current element.
            num1Sum += numsPair[i][0];

            minNum2Heap.add(numsPair[i]);

            // Once we have k elements,
            // compute possible score.
            if (minNum2Heap.size() == k) {

                // The smallest nums2 in heap
                // is the multiplier.
                int minNum2 = minNum2Heap.peek()[1];

                maxScore = Math.max(
                    maxScore,
                    num1Sum * minNum2
                );

                // Remove the element with smallest nums2.
                //
                // Why?
                // Because to try next combination,
                // we slide the window by removing
                // weakest multiplier.
                //
                // Also reduce its nums1 from sum.
                num1Sum -= minNum2Heap.poll()[0];
            }
        }

        return maxScore;
    }
}
