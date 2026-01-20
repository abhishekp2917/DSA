import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {

        // The array is already SORTED.
        // We need to find a CONTIGUOUS subarray of length k
        // such that its elements are the closest to x.

        // Instead of choosing k elements individually,
        // we search for the BEST WINDOW of size k.

        // left and right represent possible starting indices of the window
        //
        // Window can start from:
        //     index 0
        // up to:
        //     index (n - k)
        //
        // Reason:
        //     A window of size k cannot start after n - k
        int left = 0;
        int right = arr.length - k;

        // Binary search on the STARTING INDEX of the window
        while (left < right) {

            int mid = (left + right) / 2;

            // We compare two possible windows:
            //   Window 1 starts at mid       → arr[mid] ... arr[mid + k - 1]
            //   Window 2 starts at mid + 1   → arr[mid + 1] ... arr[mid + k]
            //
            // We only compare the BOUNDARY elements because:
            //   - Internal elements overlap
            //   - Only the extreme ends decide which window is closer to x

            // Distance from x to the LEFT boundary of first window
            int distLeft = x - arr[mid];

            // Distance from x to the RIGHT boundary of second window
            int distRight = arr[mid + k] - x;

            // If left window is WORSE than right window,
            // we should shift the window to the RIGHT
            //
            // Condition:
            //     x - arr[mid] > arr[mid + k] - x
            //
            // Reason:
            //     The far left element is farther from x than
            //     the new right element in the shifted window
            if (distLeft > distRight) {

                // Best window must start AFTER mid
                left = mid + 1;
            }

            // Otherwise, the window starting at mid is better or equal
            // so we keep it and discard right side
            else {

                // Best window is at mid or before
                right = mid;
            }
        }

        // At the end, 'left' is the optimal starting index of the window
        //
        // We return the subarray arr[left ... left + k - 1]
        return Arrays.stream(arr, left, left + k)
                     .boxed()
                     .collect(Collectors.toList());
    }
}
