import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {

    public int minGroups(int[][] intervals) {

        // Step 1:
        // Sort intervals by starting time.
        //
        // Why?
        // Because we want to process intervals
        // in chronological order.
        //
        // Once sorted, when we process an interval,
        // all previously seen intervals start before or at this one.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Min heap to store END TIMES of active groups.
        //
        // Why store end times?
        // Because for grouping intervals,
        // we only care when a group becomes free.
        //
        // The smallest end time tells us
        // which group frees up earliest.
        PriorityQueue<Integer> minEndHeap = new PriorityQueue<>();

        for (int[] interval : intervals) {

            int start = interval[0];
            int end = interval[1];

            // Check if the earliest ending group
            // finishes BEFORE current interval starts.
            //
            // Important: condition is strictly < start
            // because intervals are inclusive.
            //
            // If previous end == start,
            // they still overlap.
            if (!minEndHeap.isEmpty() && minEndHeap.peek() < start) {

                // That group is now free.
                // We can reuse it.
                minEndHeap.poll();
            }

            // Whether reused or not,
            // current interval now occupies a group.
            //
            // We push its end time into heap.
            minEndHeap.add(end);
        }

        // Heap size represents
        // maximum number of overlapping intervals at any time.
        //
        // That is exactly the minimum number of groups required.
        return minEndHeap.size();
    }
}
