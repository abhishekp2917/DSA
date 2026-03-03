import java.util.*;

class Solution {

    public int smallestChair(int[][] times, int targetFriend) {

        int n = times.length;

        // We will convert input into:
        // [arrivalTime, leavingTime, friendIndex]
        //
        // Why store friendIndex?
        // Because after sorting by arrival time,
        // original order is lost.
        int[][] intervals = new int[n][3];

        // Heap 1:
        // Tracks currently occupied chairs.
        //
        // Each element = {leaveTime, chairNumber}
        //
        // It is a MIN heap by leaveTime.
        //
        // Why?
        // So we can quickly free chairs
        // whose leaveTime <= current arrival time.
        PriorityQueue<int[]> minLeaveTimeHeap =
            new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Heap 2:
        // Tracks available (empty) chairs.
        //
        // MIN heap so that we always assign
        // the smallest numbered available chair.
        PriorityQueue<Integer> minEmptyChairHeap =
            new PriorityQueue<>();

        // Initially:
        // All chairs from 0 to n-1 are empty.
        //
        // Why n chairs?
        // Because at most n friends can be present simultaneously.
        for (int i = 0; i < n; i++) {
            minEmptyChairHeap.add(i);
            intervals[i] = new int[] {
                times[i][0],   // arrival
                times[i][1],   // leaving
                i              // friend index
            };
        }

        // Sort friends by arrival time.
        //
        // Why?
        // Because we simulate events in chronological order.
        Arrays.sort(intervals,
            (interval1, interval2) ->
                interval1[0] - interval2[0]);

        for (int i = 0; i < n; i++) {

            int start = intervals[i][0];
            int end = intervals[i][1];
            int friend = intervals[i][2];

            // Step 1:
            // Free chairs for friends who have already left.
            //
            // While earliest leaving friend leaves before or at this arrival,
            // free their chair.
            //
            // Why <= ?
            // Because if someone leaves exactly at arrival time,
            // that chair becomes available immediately.
            while (!minLeaveTimeHeap.isEmpty()
                    && minLeaveTimeHeap.peek()[0] <= start) {

                int occupiedChair =
                    minLeaveTimeHeap.poll()[1];

                minEmptyChairHeap.add(occupiedChair);
            }

            // If this is the target friend,
            // the smallest available chair now
            // is the answer.
            //
            // Why?
            // Because problem says:
            // Always assign smallest available chair.
            if (friend == targetFriend) break;

            // Step 2:
            // Assign smallest available chair
            // to this friend.
            int emptyChair = minEmptyChairHeap.poll();

            // Mark this chair as occupied until 'end'
            minLeaveTimeHeap.add(new int[] { end, emptyChair });
        }

        // At this moment,
        // smallest available chair is for target friend.
        return minEmptyChairHeap.poll();
    }
}
