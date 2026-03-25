class Solution {

    public int[] corpFlightBookings(int[][] bookings, int n) {

        // This array will initially act as a DIFFERENCE ARRAY
        //
        // After prefix processing, it becomes the final answer
        int[] reservedSeats = new int[n];

        // --------------------------------------------------
        // STEP 1: Apply range updates using difference array
        // --------------------------------------------------
        //
        // Each booking = [start, end, seats]
        //
        // Instead of updating every index in range,
        // we mark only boundaries.
        for (int[] booking : bookings) {

            int start = booking[0] - 1; // convert to 0-based index
            int end = booking[1] - 1;

            int seats = booking[2];

            // Add seats at start
            reservedSeats[start] += seats;

            // Remove seats AFTER end
            //
            // Why?
            // Because range is inclusive
            //
            // So effect should stop after end
            if (end + 1 < n)
                reservedSeats[end + 1] -= seats;
        }


        // --------------------------------------------------
        // STEP 2: Convert difference array to actual values
        // --------------------------------------------------
        //
        // Prefix sum accumulates the effect
        //
        // After this:
        // reservedSeats[i] = total seats booked for flight i
        for (int i = 1; i < n; i++) {

            reservedSeats[i] += reservedSeats[i - 1];
        }

        return reservedSeats;
    }
}