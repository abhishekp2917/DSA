import java.util.*;

class Solution {
    // Problem requires modulo because area can be very large
    private final int MOD = 1000_000_007;
    // Constants representing event types
    // ADD  -> rectangle starts at this x
    // REMOVE -> rectangle ends at this x
    private final int ADD = 1;
    private final int REMOVE = -1; 

    public int rectangleArea(int[][] rectangles) {

        // Total accumulated area of union of rectangles
        long totalArea = 0;
        
        // TreeMap used for sweep line events.
        //
        // Key   : x-coordinate where something changes
        // Value : list of events at this x
        //
        // Each event = {y1, y2, type}
        //
        // Why TreeMap?
        // Because sweep-line algorithms require
        // processing events in sorted coordinate order.
        Map<Integer, List<int[]>> eventsMap = new TreeMap<>();


        // This list maintains all rectangles currently intersecting
        // the sweep line.
        //
        // Each element represents a vertical interval [y1, y2].
        //
        // These intervals represent the vertical coverage
        // between current x and next x.
        List<int[]> activeRectangle = new ArrayList<>();


        // --------------------------------------------------
        // STEP 1: Convert rectangles into sweep-line events
        // --------------------------------------------------
        //
        // Rectangle = [x1, y1, x2, y2]
        //
        // At x1 → rectangle enters the sweep region
        // At x2 → rectangle leaves the sweep region
        //
        // Instead of tracking rectangles directly,
        // we convert them into events.
        //
        // This is the core sweep-line trick.
        for (int[] rectangle : rectangles) {

            int x1 = rectangle[0];
            int y1 = rectangle[1];
            int x2 = rectangle[2];
            int y2 = rectangle[3];

            // Events for start x
            List<int[]> events1 =
                eventsMap.getOrDefault(x1, new ArrayList<>());

            // Events for end x
            List<int[]> events2 =
                eventsMap.getOrDefault(x2, new ArrayList<>());

            // Rectangle begins covering y-range
            events1.add(new int[]{y1, y2, ADD});

            // Rectangle stops covering y-range
            events2.add(new int[]{y1, y2, REMOVE});

            eventsMap.put(x1, events1);
            eventsMap.put(x2, events2);
        }


        // Previous sweep x position
        int prevX = 0;

        // Current total vertical coverage (union of active y intervals)
        long currHeight = 0;


        // --------------------------------------------------
        // STEP 2: Sweep across the x-axis
        // --------------------------------------------------
        //
        // Imagine a vertical line sweeping from left to right.
        //
        // Between two event positions:
        //
        //   width = (x2 - x1)
        //
        // Vertical coverage remains constant.
        //
        // Area contributed = width * activeHeight
        //
        for (Map.Entry<Integer, List<int[]>> entry : eventsMap.entrySet()) {

            int currX = entry.getKey();

            List<int[]> events = entry.getValue();

            // Width of current vertical strip
            int width = currX - prevX;

            // Area contribution of this strip
            //
            // height = union of all active y intervals
            totalArea =
                (totalArea + width * currHeight) % MOD;


            // --------------------------------------------------
            // STEP 3: Update active rectangles
            // --------------------------------------------------
            //
            // We now process all events occurring at currX.
            //
            // Some rectangles start → add intervals
            // Some rectangles end   → remove intervals
            //
            refreshActiveRectangle(activeRectangle, events);


            // --------------------------------------------------
            // STEP 4: Recompute vertical coverage
            // --------------------------------------------------
            //
            // After updating active rectangles,
            // we compute union of all y intervals.
            //
            // This gives total height covered.
            currHeight = calcHeight(activeRectangle);

            // Move sweep line
            prevX = currX;
        }

        return (int) totalArea;
    }


    // --------------------------------------------------
    // Update active rectangles based on events
    // --------------------------------------------------
    //
    // ADD event:
    //     Insert interval
    //
    // REMOVE event:
    //     Remove interval
    //
    private void refreshActiveRectangle(
            List<int[]> activeRectangle,
            List<int[]> events) {

        for (int[] event : events) {

            int y1 = event[0];
            int y2 = event[1];
            int type = event[2];

            if (type == 1) {

                // Rectangle begins covering this y interval
                activeRectangle.add(new int[]{y1, y2});
            }
            else {

                // Rectangle ends → remove the interval
                //
                // Since intervals may overlap,
                // we must remove the exact interval.
                for (int i = 0; i < activeRectangle.size(); i++) {

                    if (activeRectangle.get(i)[0] == y1
                            && activeRectangle.get(i)[1] == y2) {

                        activeRectangle.remove(i);
                        break;
                    }
                }
            }
        }
    }


    // --------------------------------------------------
    // Compute union length of active y intervals
    // --------------------------------------------------
    //
    // Example:
    //
    // intervals:
    // [1,5]
    // [3,7]
    // [10,12]
    //
    // merged:
    // [1,7] → length 6
    // [10,12] → length 2
    //
    // total height = 8
    //
    private long calcHeight(List<int[]> activeRectangle) {

        if (activeRectangle.isEmpty())
            return 0L;

        // Sort intervals by start coordinate
        activeRectangle.sort((a, b) -> a[0] - b[0]);

        long total = 0;

        int start = activeRectangle.get(0)[0];
        int end = activeRectangle.get(0)[1];

        for (int i = 1; i < activeRectangle.size(); i++) {

            int s = activeRectangle.get(i)[0];
            int e = activeRectangle.get(i)[1];

            // Disjoint interval
            if (s > end) {

                // close previous interval
                total += end - start;

                start = s;
                end = e;
            }
            else {

                // overlap → extend interval
                end = Math.max(end, e);
            }
        }

        // add last interval
        total += end - start;

        return total;
    }
}