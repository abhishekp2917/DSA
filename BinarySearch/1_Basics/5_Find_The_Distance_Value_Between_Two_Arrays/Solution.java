import java.util.Arrays;

class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {

        // We sort arr2 because we want to apply binary search on it
        // Sorting allows us to quickly find nearest values to any element in arr1
        Arrays.sort(arr2);

        int count = 0;

        // For each element in arr1, we check if there exists any element in arr2
        // such that |arr1[i] - arr2[j]| <= d
        for (int i = 0; i < arr1.length; i++) {

            int num = arr1[i];

            // Find the greatest element in arr2 that is <= num
            // This gives the closest candidate on the LEFT side
            int lowerBound = findLowerBound(arr2, num);

            // Find the smallest element in arr2 that is >= num
            // This gives the closest candidate on the RIGHT side
            int upperBound = findUpperBound(arr2, num);

            // If BOTH nearest candidates are farther than distance d,
            // then no element in arr2 lies within distance d from num
            if (Math.abs(lowerBound - num) > d && Math.abs(upperBound - num) > d) {
                count++;
            }
        }

        // Count stores how many elements in arr1 satisfy the condition
        return count;
    }

    // This function finds the largest value in arr that is <= target
    // (i.e., floor of target in sorted array)
    public int findLowerBound(int[] arr, int target) {

        int start = 0, end = arr.length - 1;

        // Default value if no element <= target exists
        int lowerBound = Integer.MIN_VALUE;

        while (start <= end) {

            int mid = (start + end) / 2;

            // If arr[mid] is greater than target,
            // then this value and everything to the right is too large
            if (arr[mid] > target) {
                end = mid - 1;
            }

            // If arr[mid] is less than or equal to target,
            // then this is a valid candidate for lower bound
            // But there might exist a closer one on the right
            else {
                lowerBound = arr[mid];
                start = mid + 1;
            }
        }

        // Returns the closest value <= target
        return lowerBound;
    }

    // This function finds the smallest value in arr that is >= target
    // (i.e., ceil of target in sorted array)
    public int findUpperBound(int[] arr, int target) {

        int start = 0, end = arr.length - 1;

        // Default value if no element >= target exists
        int upperBound = Integer.MAX_VALUE;

        while (start <= end) {

            int mid = (start + end) / 2;

            // If arr[mid] is smaller than target,
            // then this value and everything to the left is too small
            if (arr[mid] < target) {
                start = mid + 1;
            }

            // If arr[mid] is greater than or equal to target,
            // then this is a valid candidate for upper bound
            // But there might exist a closer one on the left
            else {
                upperBound = arr[mid];
                end = mid - 1;
            }
        }

        // Returns the closest value >= target
        return upperBound;
    }
}
