class Solution {
    public int mySqrt(int x) {

        // We are searching for an integer 'sqrt' such that:
        // sqrt * sqrt <= x and (sqrt + 1) * (sqrt + 1) > x
        // This means we are not searching in the array,
        // but searching in the RANGE of possible answers.

        // Smallest possible sqrt is 1 (for x >= 1)
        int start = 1;

        // Largest possible sqrt can be x/2 for x >= 2
        // Because any number greater than x/2 squared will definitely exceed x
        int end = x / 2;

        // This variable will store the best valid answer found so far
        // We initialize it with x (only matters for x = 0 or 1 edge cases)
        int sqrt = x;

        // Binary search on the answer range
        while (start <= end) {

            // Pick middle candidate value as a possible square root
            int mid = (start + end) / 2;

            // We cast to long because mid * mid can overflow int
            long sqr = (long) mid * mid;

            // If mid^2 is less than or equal to x,
            // then mid is a valid candidate for floor(sqrt(x))
            if (sqr <= x) {

                // Store this value as a possible answer
                // because it satisfies mid * mid <= x
                sqrt = mid;

                // Try to find a bigger value that still satisfies the condition
                // because we want the maximum such mid
                start = mid + 1;
            }

            // If mid^2 is greater than x,
            // then mid is too large and cannot be the square root
            else {

                // Discard the right half including mid
                // and continue searching in the left half
                end = mid - 1;
            }
        }

        // At the end, sqrt stores the largest value such that sqrt * sqrt <= x
        // which is exactly floor(sqrt(x))
        return sqrt;
    }
}
