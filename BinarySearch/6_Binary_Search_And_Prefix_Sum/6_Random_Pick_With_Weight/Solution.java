import java.util.Random;

class Solution {

    int n;

    // prefixWeightSum[i] = sum of weights from 0 → i
    //
    // This creates "ranges" for each index.
    long[] prefixWeightSum;

    Random random = new Random();
    
    public Solution(int[] w) {

        this.n = w.length;

        this.prefixWeightSum = new long[n];

        // Build prefix sum
        //
        // Why?
        // To map weights into cumulative ranges.
        //
        // Example:
        // w = [2, 3, 5]
        //
        // prefix = [2, 5, 10]
        //
        // ranges:
        // index 0 → [0,2)
        // index 1 → [2,5)
        // index 2 → [5,10)
        prefixWeightSum[0] = w[0];

        for (int i = 1; i < n; i++) {
            prefixWeightSum[i] =
                prefixWeightSum[i - 1] + w[i];
        }
    }
    
    public int pickIndex() {

        // Generate random number in range:
        // [0, totalWeight - 1]
        //
        // Why?
        // Because total range length = total weight.
        //
        // Each index occupies a segment proportional
        // to its weight.
        long randomIdx = (long)(random.nextDouble() * prefixWeightSum[n - 1]);

        // Find first index whose prefix sum > randomIdx
        //
        // This maps random number → correct weight range.
        return getUpperBoundIdx(prefixWeightSum, randomIdx);
    }

    // Binary search:
    // Find FIRST index such that arr[i] > target
    //
    // This is "upper bound"
    private int getUpperBoundIdx(long[] arr, long target) {

        int idx = -1;

        int left = 0, right = arr.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (arr[mid] > target) {

                // Valid candidate
                idx = mid;

                // Try to find earlier one
                right = mid - 1;
            }
            else {

                // Need larger prefix sum
                left = mid + 1;
            }
        }

        return idx;
    }
}