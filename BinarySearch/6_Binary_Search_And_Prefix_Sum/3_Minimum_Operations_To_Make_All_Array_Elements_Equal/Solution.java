import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    public List<Long> minOperations(int[] nums, int[] queries) {

        // We want, for each query, the total operations needed
        // to make ALL elements of nums equal to queryValue.
        //
        // Operation cost = |nums[i] - queryValue| for each element.

        int arrayLength = nums.length;

        // Sort nums so that:
        // - All values <= query are on the left
        // - All values >  query are on the right
        // This allows us to split the cost computation into two parts
        Arrays.sort(nums);

        // Build prefix sum:
        // prefixSum[i] = sum of nums[0 ... i-1]
        // This helps compute sum of any prefix in O(1)
        long[] prefixSum = new long[arrayLength + 1];
        for (int index = 1; index <= arrayLength; index++) {
            prefixSum[index] = prefixSum[index - 1] + nums[index - 1];
        }

        List<Long> result = new ArrayList<>();

        // Process each query independently
        for (int queryValue : queries) {

            // Find how many elements are <= queryValue
            // This gives the split point between left and right parts
            int elementsOnLeft = findFirstGreaterIndex(nums, queryValue);

            // ---------------- Left Part Cost ----------------
            // For all nums[i] <= queryValue:
            // Cost = queryValue - nums[i]
            //
            // Total left cost =
            //     queryValue * countLeft - sum(left elements)
            long leftCost =
                    (long) queryValue * elementsOnLeft
                            - prefixSum[elementsOnLeft];

            // ---------------- Right Part Cost ----------------
            // For all nums[i] > queryValue:
            // Cost = nums[i] - queryValue
            //
            // Total right cost =
            //     sum(right elements) - queryValue * countRight
            long rightCost =
                    (prefixSum[arrayLength] - prefixSum[elementsOnLeft])
                            - (long) queryValue * (arrayLength - elementsOnLeft);

            // Total cost = left cost + right cost
            result.add(leftCost + rightCost);
        }

        return result;
    }

    /**
     * Finds:
     *   index of the FIRST element that is > target
     * Which also means:
     *   count of elements <= target
     */
    private int findFirstGreaterIndex(int[] nums, int target) {

        int left = 0, right = nums.length - 1;

        // Will store the last index where nums[index] <= target
        int lastValidIndex = -1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            // If nums[mid] <= target,
            // then this index is valid for the LEFT part
            // But there might be more on the right
            if (nums[mid] <= target) {
                lastValidIndex = mid;
                left = mid + 1;
            }

            // If nums[mid] > target,
            // then all elements to the right are also > target
            // So we move left
            else {
                right = mid - 1;
            }
        }

        // Number of elements <= target = lastValidIndex + 1
        return lastValidIndex + 1;
    }
}
