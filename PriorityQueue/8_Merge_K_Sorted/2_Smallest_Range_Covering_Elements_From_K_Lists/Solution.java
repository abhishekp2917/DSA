import java.util.*;

class Solution {

    public int[] smallestRange(List<List<Integer>> nums) {

        // k = number of sorted lists
        int k = nums.size();

        // We will maintain:
        // - A MIN HEAP to track the current minimum element among lists
        // - A variable maxValue to track the current maximum among chosen elements
        //
        // Why both?
        // Because at any moment,
        // we are maintaining one selected element from each list.
        //
        // The current range is:
        //      [minValue, maxValue]
        //
        // We try to minimize (maxValue - minValue).

        int maxValue = Integer.MIN_VALUE;

        // Min heap stores {row, col}
        // Comparator compares actual values.
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(
                nums.get(a[0]).get(a[1]),
                nums.get(b[0]).get(b[1])
            )
        );

        // Step 1:
        // Insert first element of each list.
        //
        // Why?
        // Because we must pick at least one element from each list.
        //
        // Initially, the smallest possible window
        // must include the first elements.
        for (int i = 0; i < k; i++) {
            minHeap.offer(new int[]{ i, 0 });

            // Track the maximum among initial elements
            maxValue = Math.max(maxValue, nums.get(i).get(0));
        }

        int start = 0, end = 0;
        int bestRange = Integer.MAX_VALUE;

        // Step 2:
        // Continue while we still have one element from each list.
        //
        // Why condition minHeap.size() == k?
        // Because if any list runs out,
        // we cannot form a valid range covering all lists.
        while (minHeap.size() == k) {

            // Extract the current minimum element.
            int[] curr = minHeap.poll();

            int row = curr[0];
            int col = curr[1];

            int minValue = nums.get(row).get(col);

            // Now current window is:
            // [minValue, maxValue]
            //
            // Check if this window is better.
            if ((maxValue - minValue) < bestRange) {
                bestRange = maxValue - minValue;
                start = minValue;
                end = maxValue;
            }

            // Move forward in the same list.
            //
            // Why?
            // Because:
            // We just removed the smallest element.
            // To potentially shrink the range,
            // we must increase the minimum.
            //
            // So we advance in the list
            // from which the minimum came.
            if (col + 1 < nums.get(row).size()) {

                int nextValue = nums.get(row).get(col + 1);

                minHeap.offer(new int[]{ row, col + 1 });

                // Update maxValue if necessary.
                //
                // Why update max?
                // Because new element might be larger
                // than current maximum.
                maxValue = Math.max(maxValue, nextValue);
            }
        }

        return new int[]{ start, end };
    }
}
