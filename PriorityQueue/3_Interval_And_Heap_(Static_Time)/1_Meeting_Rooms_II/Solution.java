import java.util.*;

class Solution {

    public int minMeetingRooms(int[] start, int[] end) {

        int n = start.length;

        // Min heap to track ending times of meetings currently using rooms.
        //
        // Why min heap?
        // Because we want to know which meeting finishes earliest.
        // If the earliest finishing meeting ends before
        // the next meeting starts, we can reuse that room.
        PriorityQueue<Integer> minEndTimeHeap = new PriorityQueue<>();

        // Combine start and end arrays into single structure.
        //
        // Why?
        // To sort meetings by start time.
        int[][] meetings = new int[n][2];

        for (int i = 0; i < n; i++) {
            meetings[i] = new int[]{ start[i], end[i] };
        }

        // Step 1:
        // Sort meetings by start time.
        //
        // Why?
        // So we process meetings in chronological order.
        Arrays.sort(meetings,
            (meeting1, meeting2) ->
                meeting1[0] - meeting2[0]);

        // Step 2:
        // Process each meeting.
        for (int[] meeting : meetings) {

            int startTime = meeting[0];
            int endTime = meeting[1];

            // If the room that frees up the earliest
            // is available before or exactly at startTime,
            // we can reuse it.
            //
            // Why <= ?
            // Because if a meeting ends exactly at start time,
            // the room becomes free immediately.
            if (!minEndTimeHeap.isEmpty()
                    && minEndTimeHeap.peek() <= startTime) {

                // Reuse that room
                minEndTimeHeap.poll();
            }

            // Allocate a room for this meeting
            // (either reused or new one).
            minEndTimeHeap.add(endTime);
        }

        // The heap size represents
        // the maximum number of overlapping meetings.
        //
        // That equals the minimum number of rooms required.
        return minEndTimeHeap.size();
    }
}
