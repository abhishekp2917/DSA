import java.util.PriorityQueue;

class Solution {

    public int eatenApples(int[] apples, int[] days) {

        int eatenApples = 0;

        // currDay represents the current day in simulation.
        int currDay = 0;

        // Min heap storing indices of days when apples were grown.
        //
        // Comparator:
        // Order by expiry day.
        //
        // expiryDay = growDay + days[growDay]
        //
        // Why min heap by expiry?
        // Because greedy rule:
        // Always eat the apple that expires earliest.
        //
        // This prevents wasting apples that will rot soon.
        PriorityQueue<Integer> minHeap =
            new PriorityQueue<>((i1, i2) ->
                (i1 + days[i1]) - (i2 + days[i2])
            );

        // Continue simulation while:
        // - There are apples in heap
        // OR
        // - We are still within apple-growing days.
        while (!minHeap.isEmpty()
                || currDay < apples.length) {

            // Step 1:
            // If apples grow today and they last > 0 days,
            // add this batch into heap.
            //
            // Why check days[currDay] > 0?
            // Because if days == 0,
            // apples expire immediately.
            if (currDay < apples.length
                    && apples[currDay] > 0
                    && days[currDay] > 0) {

                minHeap.add(currDay);
            }

            // Step 2:
            // Remove all expired apple batches.
            //
            // If expiryDay <= currDay,
            // they cannot be eaten anymore.
            while (!minHeap.isEmpty()
                    && hasAppleExpired(
                        minHeap.peek(),
                        days,
                        currDay)) {

                minHeap.poll();
            }

            // Step 3:
            // If there is any valid apple batch,
            // eat one apple from the earliest expiring batch.
            if (!minHeap.isEmpty()) {

                int day = minHeap.peek();

                eatenApples++;

                // Reduce apple count from that batch.
                apples[day]--;

                // If that batch becomes empty,
                // remove it from heap.
                if (apples[day] == 0) {
                    minHeap.poll();
                }
            }

            // Move to next day.
            currDay++;
        }

        return eatenApples;
    }

    private boolean hasAppleExpired(
        int appleGrownDay,
        int[] days,
        int currDay) {

        int expiryDay =
            appleGrownDay + days[appleGrownDay];

        return expiryDay <= currDay;
    }
}
