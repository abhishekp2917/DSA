class Solution {
    public int findKthPositive(int[] arr, int k) {

        // The array contains strictly increasing positive numbers.
        // Some positive numbers are missing between them.
        //
        // We want to find the k-th missing positive number.

        // Instead of scanning linearly, we use Binary Search on INDEX
        // by counting how many numbers are missing up to each position.

        int start = 0, end = arr.length - 1;

        while (start <= end) {

            int mid = start + (end - start) / 2;

            // Number of missing positive numbers BEFORE arr[mid]
            //
            // At index mid, the expected value without missing would be (mid + 1).
            // Actual value is arr[mid].
            //
            // So missing count = arr[mid] - (mid + 1)
            //
            // Reason:
            // Example:
            //   index = 3, arr[3] = 7
            //   expected value = 4
            //   missing = 7 - 4 = 3 missing numbers before this index
            int missing = arr[mid] - mid - 1;

            // If missing numbers before mid are LESS than k,
            // then the k-th missing number is NOT on the left side
            // → move right
            if (missing < k) {

                // We still need to find more missing numbers
                start = mid + 1;
            }

            // If missing numbers are >= k,
            // then the k-th missing lies on the LEFT side (or at mid)
            else {

                // Discard right half
                end = mid - 1;
            }
        }

        // After binary search:
        // 'start' is the number of elements in arr that are <= the answer
        //
        // The k-th missing number =
        //      start + k
        //
        // Reason:
        //   Among numbers 1 ... (start + k),
        //   exactly 'start' numbers exist in the array,
        //   so k numbers are missing
        return start + k;
    }
}
