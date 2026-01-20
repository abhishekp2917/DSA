class Solution {

    public long findMaximumNumber(long k, int x) {

        // We need to find the MAXIMUM number N such that:
        //     Total count of set bits at positions divisible by x
        //     in all numbers from 1 to N  <= k

        // We are NOT iterating numbers from 1 to N.
        // We are searching the ANSWER: the largest valid N.

        // Binary search on N (the final number)

        long ans = 0;

        // Minimum possible N = 0
        long left = 0;

        // Maximum possible N = 2^50 - 1 (safe upper bound from constraints)
        // Reason:
        // Bit positions considered go up to 50
        long right = (long) Math.pow(2, 50) - 1;

        // Binary search on N
        while (left <= right) {

            // Try a candidate N
            long mid = (left + right) / 2;

            // Count how many special set bits exist from 1 to mid
            // at positions that are multiples of x
            long count = getSetBitFrom1ToN(mid, x);

            // If total count is within allowed k,
            // then this N is FEASIBLE
            if (count <= k) {

                // Store as possible answer
                ans = mid;

                // Try to increase N further
                // Reason:
                // We want the MAXIMUM possible N
                left = mid + 1;
            }

            // If count exceeds k,
            // then N is too large
            else {

                // Try smaller N
                right = mid - 1;
            }
        }

        // ans is the largest N satisfying the constraint
        return ans;
    }

    private long getSetBitFrom1ToN(long n, int x) {

        // This function counts:
        // Total number of SET BITS at bit positions:
        //     (x-1), (2x-1), (3x-1), ...
        // across all numbers from 1 to n.

        long count = 0;

        // We iterate over only those bit positions
        // which are multiples of x (0-based indexing adjusted)
        //
        // i = x-1, 2x-1, 3x-1, ...
        for (int i = x - 1; i < 50; i += x) {

            // For a fixed bit position i,
            // bits follow a repeating pattern in blocks of size:
            //
            //     window = 2^(i+1)
            //
            // In every such window:
            //     lower half  -> bit = 0
            //     upper half  -> bit = 1
            long window = 1L << (i + 1);

            // In each full window, number of set bits at position i =
            //     2^i
            long setBitPerWindow = 1L << i;

            // Number of FULL windows in range [0 ... n]
            long quotient = ((n + 1) / window) * setBitPerWindow;

            // Remaining part after full windows
            long remainder = (n + 1) % window;

            // Extra set bits from the partial window:
            //
            // If remainder <= 2^i:
            //     no extra set bits
            //
            // Else:
            //     extra = remainder - 2^i
            long extra =
                    (remainder <= setBitPerWindow)
                            ? 0
                            : (remainder - setBitPerWindow);

            // Add contribution of this bit position
            count += quotient + extra;
        }

        // Return total count of required set bits
        return count;
    }
}
