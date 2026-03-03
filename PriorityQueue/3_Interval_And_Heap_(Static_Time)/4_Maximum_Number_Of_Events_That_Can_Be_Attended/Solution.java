import java.util.*;

class Solution {

    public int maxEvents(int[][] events) {

        int n = events.length;

        int count = 0;

        // We simulate day by day.
        int currDay = 1;

        // Step 1:
        // Sort events by start day.
        //
        // Why?
        // Because we want to process events
        // in chronological order of availability.
        Arrays.sort(events,
            (event1, event2) ->
                Integer.compare(event1[0], event2[0]));

        // Min heap storing end days of available events.
        //
        // Why min heap?
        // Because if multiple events are available,
        // we should attend the one that ends earliest.
        //
        // Greedy reasoning:
        // Attending the event that finishes earliest
        // leaves maximum room for future events.
        PriorityQueue<Integer> minEndDayHeap =
            new PriorityQueue<>();

        int i = 0;

        // Continue while:
        // - There are unprocessed events
        // OR
        // - There are available events in heap.
        //
        // This ensures we process all possible days.
        while (i < n || !minEndDayHeap.isEmpty()) {

            // Step 2:
            // Remove events that have already expired.
            //
            // If event's end day < currDay,
            // it can no longer be attended.
            while (!minEndDayHeap.isEmpty()
                    && minEndDayHeap.peek() < currDay) {
                minEndDayHeap.poll();
            }

            // Step 3:
            // Add all events that start today.
            //
            // Since events are sorted by start day,
            // we can add them in order.
            while (i < n && events[i][0] == currDay) {

                int endDay = events[i][1];
                minEndDayHeap.add(endDay);
                i++;
            }

            // Step 4:
            // If any event is available,
            // attend the one that ends earliest.
            if (!minEndDayHeap.isEmpty()) {

                count++;

                // Remove that event from heap
                minEndDayHeap.poll();
            }

            // Move to next day
            currDay++;
        }

        return count;
    }
}
