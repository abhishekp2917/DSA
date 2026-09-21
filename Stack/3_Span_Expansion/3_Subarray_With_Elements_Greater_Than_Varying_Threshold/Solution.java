import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int validSubarraySize(int[] nums, int threshold) {
        int n = nums.length;

        // For every index i:
        // nearestSmallerRight[i] = index of the first element to the right
        //                         that is strictly smaller than nums[i].
        //
        // If no smaller element exists on the right, store n as a sentinel.
        //
        // nearestSmallerLeft[i] = index of the first element to the left
        //                        that is strictly smaller than nums[i].
        //
        // If no smaller element exists on the left, store -1 as a sentinel.
        int[] nearestSmallerRight = new int[n];
        int[] nearestSmallerLeft = new int[n];

        // Monotonic increasing stack implemented using a Deque.
        // We store indices instead of values so that we can:
        // 1. Compare values using nums[index].
        // 2. Directly obtain the boundary indices.
        Deque<Integer> minStack = new ArrayDeque<>();

        // ------------------------------------------------------------
        // STEP 1: Find the nearest smaller element on the right.
        // ------------------------------------------------------------
        //
        // Traverse from right to left because we want to find the
        // closest smaller element among the elements to the right.
        for (int i = n - 1; i >= 0; i--) {
            int num = nums[i];

            // Remove all elements greater than or equal to nums[i].
            //
            // Why?
            // - An element >= nums[i] cannot be the answer for nums[i]
            //   because we need a strictly smaller element.
            // - It also cannot be useful for any future element to the
            //   left because nums[i] is smaller than or equal to it
            //   and is closer to those elements.
            //
            // After this loop, the stack contains a monotonically
            // increasing sequence of values from bottom to top.
            while (!minStack.isEmpty()
                    && nums[minStack.peekLast()] >= num) {
                minStack.pollLast();
            }

            // The last element of the stack is the closest smaller
            // element on the right.
            //
            // If the stack is empty, there is no smaller element
            // to the right, so use n as the right boundary.
            nearestSmallerRight[i] =
                    (!minStack.isEmpty()) ? minStack.peekLast() : n;

            // Add the current index so that it can act as a possible
            // smaller boundary for elements to its left.
            minStack.addLast(i);
        }

        // Reuse the same deque for finding smaller elements on the left.
        minStack.clear();

        // ------------------------------------------------------------
        // STEP 2: Find the nearest smaller element on the left.
        // ------------------------------------------------------------
        //
        // Traverse from left to right because we want to find the
        // closest smaller element among the elements to the left.
        for (int i = 0; i < n; i++) {
            int num = nums[i];

            // Remove all elements greater than or equal to nums[i].
            //
            // We need a strictly smaller element. Any element >= num
            // cannot serve as a valid boundary.
            //
            // Removing these elements also maintains the monotonic
            // increasing property of the stack.
            while (!minStack.isEmpty()
                    && nums[minStack.peekLast()] >= num) {
                minStack.pollLast();
            }

            // The last element remaining in the stack is the nearest
            // smaller element on the left.
            //
            // If the stack is empty, there is no smaller element
            // to the left, so use -1 as the left boundary.
            nearestSmallerLeft[i] =
                    (!minStack.isEmpty()) ? minStack.peekLast() : -1;

            // Add the current index for future elements on the right.
            minStack.addLast(i);
        }

        // ------------------------------------------------------------
        // STEP 3: Consider each element as the minimum of a subarray.
        // ------------------------------------------------------------
        //
        // For every index i, we know:
        //
        // leftIdx  = nearest smaller element on the left.
        // rightIdx = nearest smaller element on the right.
        //
        // Therefore, nums[i] is the minimum value in the largest
        // possible subarray bounded by these smaller elements:
        //
        //        (leftIdx, rightIdx)
        //
        // The valid subarray indices are:
        //        leftIdx + 1 ... rightIdx - 1
        //
        // Its length is:
        //        rightIdx - leftIdx - 1
        //
        // If a subarray of this maximum length satisfies the condition,
        // then nums[i] * subarraySize > threshold.
        //
        // Since nums[i] is the minimum element in this subarray:
        //        minimum * length > threshold
        //
        // This means the subarray satisfies the problem's requirement.
        for (int i = 0; i < n; i++) {
            int num = nums[i];

            int leftIdx = nearestSmallerLeft[i];
            int rightIdx = nearestSmallerRight[i];

            // Calculate the largest range in which nums[i] remains
            // the minimum element.
            int subarraySize = rightIdx - leftIdx - 1;

            // Check whether the minimum value multiplied by the
            // subarray length exceeds the given threshold.
            //
            // Use long multiplication if the constraints allow
            // nums[i] * subarraySize to exceed the int range.
            if ((long) num * subarraySize > threshold) {
                return subarraySize;
            }
        }

        // No valid subarray was found.
        return -1;
    }
}
