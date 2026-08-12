import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int maxTwoEvents(int[][] events) {

        // Each event = [start, end, value]
        // We need to pick at most TWO non-overlapping events
        // such that their total value is maximized.

        // First, sort all events by their END time
        // This allows us to binary search previous non-overlapping events
        Arrays.sort(events, Comparator.comparingInt(a -> a[1]));

        // maxEventValue[i] will store:
        // maximum value among events[0 ... i]
        // This helps us quickly get the best event before index i
        int[] maxEventValue = new int[events.length];

        // Initialize first prefix value
        maxEventValue[0] = events[0][2];

        // Build prefix maximum array
        for (int i = 1; i < maxEventValue.length; i++) {
            maxEventValue[i] = Math.max(maxEventValue[i - 1], events[i][2]);
        }

        int maxValue = 0;

        // Try each event as the SECOND event
        for (int i = 0; i < events.length; i++) {

            // Find the best possible FIRST event that ends before this event starts
            int bestPreviousValue =
                    getMaxValue(events, maxEventValue, i - 1, events[i]);

            // Combine current event value + best compatible previous event
            maxValue = Math.max(maxValue, events[i][2] + bestPreviousValue);
        }

        // maxValue stores the maximum sum of values of two non-overlapping events
        return maxValue;
    }

    // This function finds:
    // The maximum value among all events with:
    //      events[k].end < current_event.start
    //
    // We binary search in the sorted events array (by end time)
    private int getMaxValue(int[][] events,
                            int[] maxEventValue,
                            int j,
                            int[] event) {

        int i = 0;
        int value = 0;

        // Binary search to find the RIGHTMOST event
        // whose end time is strictly less than current event's start time
        while (i <= j) {

            int mid = (i + j) / 2;

            // If this event ends at or after current event starts,
            // then it OVERLAPS → move left
            if (events[mid][1] >= event[0]) {
                j = mid - 1;
            }

            // If this event ends before current event starts,
            // then it is a valid non-overlapping candidate
            else {

                // Store the best value seen so far up to this index
                value = maxEventValue[mid];

                // Try to find a later valid event to maximize value
                i = mid + 1;
            }
        }

        // value contains the maximum value of a compatible previous event
        return value;
    }
}
