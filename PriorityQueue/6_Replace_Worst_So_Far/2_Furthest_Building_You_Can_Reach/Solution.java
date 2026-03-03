import java.util.Collections;
import java.util.PriorityQueue;

class Solution {

    public int furthestBuilding(int[] heights, int bricks, int ladders) {

        // Max heap to store all positive climbs encountered so far.
        //
        // Why max heap?
        // Because if we ever need to replace
        // a brick usage with a ladder,
        // we should replace the LARGEST climb.
        //
        // Ladder is more valuable on bigger climbs.
        PriorityQueue<Integer> maxHeap =
            new PriorityQueue<>(Collections.reverseOrder());

        int i = 1;

        while (i < heights.length) {

            int currHeightDiff =
                heights[i] - heights[i - 1];

            // Only care about upward climbs.
            //
            // If height decreases or stays same,
            // no resources needed.
            if (currHeightDiff > 0) {

                // Tentatively use bricks first.
                //
                // Why bricks first?
                // Because ladders are more flexible.
                // We want to save ladders for largest climbs.
                maxHeap.add(currHeightDiff);

                if (bricks >= currHeightDiff) {

                    // Use bricks.
                    bricks -= currHeightDiff;
                }
                else if (ladders > 0) {

                    // Not enough bricks.
                    // Use ladder for the LARGEST climb so far.
                    //
                    // Why largest?
                    // Because replacing the biggest brick usage
                    // gives maximum bricks back.
                    int largestHeightDiff =
                        maxHeap.poll();

                    // Refund bricks used on largest climb.
                    bricks += largestHeightDiff;

                    ladders--;

                    // Now use bricks for current climb.
                    bricks -= currHeightDiff;
                }
                else {
                    // No bricks and no ladders.
                    break;
                }
            }

            i++;
        }

        // i-1 because loop exits after failing at i.
        return i - 1;
    }
}
