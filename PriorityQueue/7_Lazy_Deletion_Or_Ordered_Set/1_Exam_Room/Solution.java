import java.util.*;

class ExamRoom {

    // TreeSet of empty seat ranges.
    //
    // Each range represents:
    // (start, end)
    // where start and end are OCCUPIED seats,
    // and seats in between are empty.
    //
    // We use virtual boundaries:
    // -1 and n
    //
    // So initial range is (-1, n).
    TreeSet<SeatRange> seatRanges;

    // Tracks occupied seats in sorted order.
    //
    // Why?
    // Needed during leave() to find
    // adjacent occupied seats quickly.
    TreeSet<Integer> occupiedSeats;

    int n;

    public ExamRoom(int n) {

        this.n = n;

        occupiedSeats = new TreeSet<>();

        // Custom comparator:
        // Ranges ordered by:
        // 1. Maximum possible minimum distance (descending)
        // 2. Smaller seat index (ascending)
        seatRanges = new TreeSet<>((range1, range2) -> {

            // Compute best seat in range1
            int mid1 = (range1.start + range1.end) / 2;
            int minDist1 =
                Math.min(mid1 - range1.start,
                         range1.end - mid1);

            // Compute best seat in range2
            int mid2 = (range2.start + range2.end) / 2;
            int minDist2 =
                Math.min(mid2 - range2.start,
                         range2.end - mid2);

            // Edge case: left boundary
            if (range1.start == -1) {
                mid1 = 0;
                minDist1 = range1.end;
            }
            if (range2.start == -1) {
                mid2 = 0;
                minDist2 = range2.end;
            }

            // Edge case: right boundary
            if (range1.end == n) {
                mid1 = n - 1;
                minDist1 = mid1 - range1.start;
            }
            if (range2.end == n) {
                mid2 = n - 1;
                minDist2 = mid2 - range2.start;
            }

            // Prefer larger minimum distance
            if (minDist1 == minDist2)
                return mid1 - mid2;

            return minDist2 - minDist1;
        });

        // Initial state:
        // Entire room is empty.
        seatRanges.add(new SeatRange(-1, n));

        // Add virtual boundaries
        occupiedSeats.add(-1);
        occupiedSeats.add(n);
    }

    public int seat() {

        // Get range that gives maximum distance.
        SeatRange seatRange = seatRanges.pollFirst();

        int start = seatRange.start;
        int end = seatRange.end;

        int midSeat = (start + end) / 2;

        // Handle boundary cases
        if (start == -1) {
            midSeat = 0;
        }
        else if (end == this.n) {
            midSeat = n - 1;
        }

        // Split range into two new ranges.
        //
        // (start, midSeat)
        // (midSeat, end)
        seatRanges.add(new SeatRange(start, midSeat));
        seatRanges.add(new SeatRange(midSeat, end));

        occupiedSeats.add(midSeat);

        return midSeat;
    }

    public void leave(int p) {

        // Find adjacent occupied seats.
        Integer leftOccupiedSeat =
            occupiedSeats.lower(p);

        Integer rightOccupiedSeat =
            occupiedSeats.higher(p);

        // Remove two smaller ranges
        seatRanges.remove(
            new SeatRange(leftOccupiedSeat, p)
        );

        seatRanges.remove(
            new SeatRange(p, rightOccupiedSeat)
        );

        // Merge into one larger range.
        seatRanges.add(
            new SeatRange(leftOccupiedSeat,
                          rightOccupiedSeat)
        );

        occupiedSeats.remove(p);
    }
}

class SeatRange {

    int start, end;

    SeatRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    // Important for TreeSet removal:
    // Must override equals & hashCode in real implementation.
}
