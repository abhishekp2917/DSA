import java.util.*;

class Solution {

    public List<List<Integer>> getSkyline(int[][] buildings) {

        // Final skyline result
        List<List<Integer>> skyline = new ArrayList<>();

        // List of sweep line events
        //
        // Each event = {x, height}
        //
        // height > 0  → building start
        // height < 0  → building end
        List<int[]> events = new ArrayList<>();


        // TreeMap storing currently active building heights
        //
        // key   = height
        // value = count of buildings having this height
        //
        // Why TreeMap?
        // Because we need to quickly get:
        //      current maximum height → lastKey()
        //
        // Using frequency avoids duplicates issues.
        TreeMap<Integer, Integer> activeBuilding = new TreeMap<>();


        // Initialize with ground level
        //
        // Why?
        // Because when all buildings end,
        // skyline must drop to height 0.
        activeBuilding.put(0, 1);


        // -------------------------------------------------
        // STEP 1: Convert buildings into sweep line events
        // -------------------------------------------------
        //
        // Building = [x1, x2, height]
        //
        // Start event:
        //      (x1, +height)
        //
        // End event:
        //      (x2, -height)
        //
        // Why negative height for end event?
        // Because it helps differentiate event types.
        for (int[] building : buildings) {

            int x1 = building[0];
            int x2 = building[1];
            int height = building[2];

            // Building starts
            events.add(new int[]{x1, height});

            // Building ends
            events.add(new int[]{x2, -height});
        }


        // -------------------------------------------------
        // STEP 2: Sort events
        // -------------------------------------------------
        //
        // Primary sort: by x-coordinate
        //
        // Secondary rule:
        // If same x:
        //     start events processed BEFORE end events
        //
        // Why?
        // Because building starting at same x
        // should affect skyline immediately.
        //
        // Comparator trick:
        // height positive > height negative
        //
        // Also ensures taller buildings processed first.
        Collections.sort(events, (e1, e2) -> {

            if (e1[0] == e2[0])
                return e2[1] - e1[1];

            return e1[0] - e2[0];
        });


        int prevMaxHeight = 0;


        // -------------------------------------------------
        // STEP 3: Sweep line across x-axis
        // -------------------------------------------------
        for (int[] event : events) {

            int x = event[0];
            int height = event[1];


            // -----------------------------
            // Case 1: Building start
            // -----------------------------
            if (height > 0) {

                // Add building height
                activeBuilding.put(
                        height,
                        activeBuilding.getOrDefault(height, 0) + 1
                );
            }

            // -----------------------------
            // Case 2: Building end
            // -----------------------------
            else {

                int actualHeight = -height;

                activeBuilding.put(
                        actualHeight,
                        activeBuilding.get(actualHeight) - 1
                );

                // Remove height if no buildings left
                if (activeBuilding.get(actualHeight) == 0) {
                    activeBuilding.remove(actualHeight);
                }
            }


            // Current tallest building
            int currMaxHeight = activeBuilding.lastKey();


            // If skyline height did not change
            // nothing to record
            if (currMaxHeight == prevMaxHeight)
                continue;


            // Skyline changed → record key point
            skyline.add(List.of(x, currMaxHeight));

            prevMaxHeight = currMaxHeight;
        }

        return skyline;
    }
}