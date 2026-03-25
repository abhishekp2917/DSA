import java.util.*;

class Solution {

    public List<List<Long>> splitPainting(int[][] segments) {

        List<List<Long>> paintings = new ArrayList<>();

        // TreeMap used to store sweep-line events.
        //
        // key   → position on number line
        // value → color contribution change at that position
        //
        // Why TreeMap?
        // Because we must process events in sorted order of positions.
        Map<Integer, Long> events = new TreeMap<>();

        // Step 1: Convert each segment into sweep line events.
        //
        // Segment: [start, end, color]
        //
        // At start → add color
        // At end   → remove color
        for (int[] segment : segments) {

            int start = segment[0];
            int end = segment[1];
            int color = segment[2];

            events.put(
                start,
                events.getOrDefault(start, 0L) + color
            );

            events.put(
                end,
                events.getOrDefault(end, 0L) - color
            );
        }

        long currColorSum = 0;

        int start = 0;
        int end = 0;

        // Step 2: Sweep through sorted events.
        for (Map.Entry<Integer, Long> event
                : events.entrySet()) {

            end = event.getKey();

            // If there is active color,
            // then interval [start, end]
            // has constant color sum.
            if (currColorSum > 0) {

                paintings.add(
                    List.of(
                        (long) start,
                        (long) end,
                        currColorSum
                    )
                );
            }

            // Update active color contribution.
            currColorSum += event.getValue();

            // Move start forward.
            start = end;
        }

        return paintings;
    }
}