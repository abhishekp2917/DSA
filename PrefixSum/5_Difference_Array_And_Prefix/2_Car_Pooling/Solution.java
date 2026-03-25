class Solution {

    public boolean carPooling(int[][] trips, int capacity) {

        // Difference array to track passenger changes at each location
        //
        // Index represents location (0 → 1000 as per constraints)
        int[] passengers = new int[1001];

        // --------------------------------------------------
        // STEP 1: Apply range updates using difference array
        // --------------------------------------------------
        for (int[] trip : trips) {

            int passenger = trip[0];
            int from = trip[1];
            int to = trip[2];

            // Passengers get IN at 'from'
            passengers[from] += passenger;

            // Passengers get OUT at 'to'
            //
            // Important:
            // Trip is [from, to), NOT including 'to'
            // So we remove passengers at 'to'
            passengers[to] -= passenger;
        }

        // --------------------------------------------------
        // STEP 2: Build prefix sum and check capacity
        // --------------------------------------------------
        int prev = 0;

        for (int i = 0; i < passengers.length; i++) {

            // Build current passenger count
            passengers[i] += prev;

            // Check capacity constraint
            //
            // If at any point passengers exceed capacity,
            // it's not possible
            if (passengers[i] > capacity)
                return false;

            // Carry forward for prefix sum
            prev = passengers[i];
        }

        return true;
    }
}