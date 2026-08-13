import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Solution {

    // Modulo is required because the total area
    // can become very large.
    private final int MOD = 1_000_000_007;

    // At x1, a rectangle starts contributing
    // to the covered height.
    private final int ADD = 1;

    // At x2, a rectangle stops contributing
    // to the covered height.
    private final int REMOVE = -1;

    public int rectangleArea(int[][] rectangles) {
        long totalArea = 0;

        // Sweep-line events are grouped by their x-coordinate.
        //
        // At x1 -> add the rectangle's [y1, y2) interval.
        // At x2 -> remove the rectangle's [y1, y2) interval.
        //
        // TreeMap keeps x-coordinates sorted,
        // allowing us to process the vertical sweep
        // from left to right.
        Map<Integer, List<int[]>> eventsMap = new TreeMap<>();

        // Stores currently active y-intervals.
        //
        // The comparator orders intervals by:
        // 1. starting y-coordinate
        // 2. ending y-coordinate
        //
        // We store a frequency because multiple rectangles
        // can have exactly the same y-interval.
        TreeMap<int[], Integer> activeRectangle = new TreeMap<>(
            (r1, r2) -> {
                int cmp = Integer.compare(r1[0], r2[0]);
                if (cmp != 0) return cmp;
                return Integer.compare(r1[1], r2[1]);
            }
        );

        for (int[] rectangle : rectangles) {
            int x1 = rectangle[0];
            int y1 = rectangle[1];
            int x2 = rectangle[2];
            int y2 = rectangle[3];

            // A rectangle contributes its [y1, y2) interval
            // to every vertical slice between x1 and x2.
            //
            // Therefore, create an ADD event at x1
            // and a REMOVE event at x2.
            List<int[]> events1 = eventsMap.getOrDefault(x1, new ArrayList<>());
            List<int[]> events2 = eventsMap.getOrDefault(x2, new ArrayList<>());

            events1.add(new int[]{y1, y2, ADD});
            events2.add(new int[]{y1, y2, REMOVE});

            eventsMap.put(x1, events1);
            eventsMap.put(x2, events2);
        }

        // x1 represents the previous sweep-line position.
        //
        // Between x1 and the current x2,
        // the set of active rectangles does not change,
        // so the covered height remains constant.
        int x1 = 0;

        // Total union length covered along the y-axis
        // by all currently active rectangles.
        long height = 0;

        for (Map.Entry<Integer, List<int[]>> entry : eventsMap.entrySet()) {
            int x2 = entry.getKey();
            List<int[]> events = entry.getValue();

            // The vertical strip [x1, x2)
            // has width (x2 - x1)
            // and the previously calculated covered height.
            //
            // Therefore:
            //
            // area = width × covered height
            totalArea = (totalArea + (long) (x2 - x1) * height) % MOD;

            // Move the sweep line to x2:
            // rectangles starting at x2 become active,
            // and rectangles ending at x2 stop being active.
            refreshActiveRectangle(activeRectangle, events);

            // After processing all events at x2,
            // calculate the union length of all active
            // y-intervals for the next vertical strip.
            height = calcHeight(activeRectangle);

            x1 = x2;
        }

        return (int) totalArea;
    }

    private void refreshActiveRectangle(TreeMap<int[], Integer> activeRectangle, List<int[]> events) {

        for (int[] event : events) {
            int y1 = event[0];
            int y2 = event[1];
            int type = event[2];

            // TreeMap uses the interval [y1, y2)
            // as the identity of an active rectangle.
            int[] rectangle = new int[]{y1, y2};

            if (type == ADD) {

                // Multiple rectangles may have
                // exactly the same y-interval.
                //
                // Increase the frequency instead of inserting
                // duplicate keys into the TreeMap.
                activeRectangle.put(
                    rectangle,
                    activeRectangle.getOrDefault(rectangle, 0) + 1
                );

            } else {

                // Only one rectangle with this y-interval
                // is ending at this x-coordinate.
                int count = activeRectangle.get(rectangle);

                if (count == 1) {

                    // No rectangle with this interval
                    // remains active.
                    activeRectangle.remove(rectangle);

                } else {

                    // Other rectangles with the same y-interval
                    // are still active, so decrease only its count.
                    activeRectangle.put(rectangle, count - 1);
                }
            }
        }
    }

    private long calcHeight(TreeMap<int[], Integer> activeRectangle) {

        // No active rectangles means
        // the current vertical strip has zero height.
        if (activeRectangle.isEmpty()) return 0L;

        long total = 0;

        // Because the TreeMap is sorted by y1 and then y2,
        // intervals are processed from bottom to top.
        int[] first = activeRectangle.firstKey();
        int start = first[0];
        int end = first[1];

        for (int[] rectangle : activeRectangle.keySet()) {
            int s = rectangle[0];
            int e = rectangle[1];

            if (s > end) {

                // There is a gap between the current merged interval
                // [start, end) and the next interval [s, e).
                //
                // Therefore, the current interval is complete
                // and its length can be added to the total.
                total += end - start;

                // Start tracking the new disconnected interval.
                start = s;
                end = e;

            } else {

                // The intervals overlap or touch.
                //
                // Merge them by extending the right boundary
                // whenever the new interval reaches farther.
                end = Math.max(end, e);
            }
        }

        // Add the final merged interval
        // after the loop finishes.
        total += end - start;

        return total;
    }
}