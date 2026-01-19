class Solution {

    public double minMaxDist(int[] stations, int k) {

        // We want to MINIMIZE the maximum distance between adjacent stations
        // after adding at most k new stations.

        // We are NOT searching positions —
        // we are searching the ANSWER: the minimum possible maximum distance.

        // Lower bound of distance = 0
        // Upper bound can be very large (given by constraints)
        double minDist = 0;
        double maxDist = 1000000;

        // This will store the best (smallest) valid maximum distance found
        double minimumMaxDist = 0;

        // We binary search on DOUBLE values.
        // We stop when the search range becomes sufficiently small.
        while (maxDist - minDist > 0.000001) {

            // Try a candidate maximum distance
            double distance = minDist + (maxDist - minDist) / 2;

            // Check if it is possible to make all gaps <= distance
            // by adding at most k stations
            if (isPossToHaveStationsAtGivenDistance(stations, k, distance)) {

                // If possible, this distance works
                minimumMaxDist = distance;

                // Try to minimize it further
                // Reason: we want the smallest possible maximum distance
                maxDist = distance;
            }

            // If not possible, this distance is too small
            // We must allow larger gaps
            else {
                minDist = distance;
            }
        }

        // minimumMaxDist is the smallest achievable maximum distance
        return minimumMaxDist;
    }

    private boolean isPossToHaveStationsAtGivenDistance(int[] stations,
                                                       int k,
                                                       double distance) {

        // We check how many new stations are REQUIRED
        // so that every adjacent gap is <= distance

        for (int i = 0; i < stations.length - 1; i++) {

            double currStationPos = stations[i];
            double nextStationPos = stations[i + 1];

            // Length of the current gap
            double gap = nextStationPos - currStationPos;

            // Number of new stations needed in this gap so that:
            //     each segment length <= distance
            //
            // If gap = 10 and distance = 3,
            // we need stations at 3, 6, 9  → 3 new stations
            //
            // Formula:
            //     number of segments = gap / distance
            //     number of new stations = floor(gap / distance)
            //
            // Reason:
            // We split the gap into pieces of size at most 'distance'
            int numOfStationsReq = (int) (gap / distance);

            // Deduct required stations from available k
            k -= numOfStationsReq;

            // If we need more stations than allowed,
            // then this distance is NOT feasible
            if (k < 0) {
                return false;
            }
        }

        // If all gaps can be fixed within k stations,
        // then this distance is feasible
        return true;
    }
}
