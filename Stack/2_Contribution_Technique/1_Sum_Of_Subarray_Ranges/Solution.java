import java.util.*;

class Solution {

    public long subArrayRanges(int[] nums) {

        int n = nums.length;

        long subarrRangeSum = 0;

        // --------------------------------------------------
        // We need:
        // sum(max - min) over all subarrays
        //
        // Instead of:
        //   computing each subarray
        //
        // We compute:
        //   contribution of each element
        //
        // FINAL FORM:
        // answer = SUM(contribution as max - contribution as min)
        // --------------------------------------------------

        int[] nearestSmallLeft = new int[n];
        int[] nearestSmallRight = new int[n];
        int[] nearestLargeLeft = new int[n];
        int[] nearestLargeRight = new int[n];

        Stack<Integer> minStack = new Stack<>();
        Stack<Integer> maxStack = new Stack<>();


        // --------------------------------------------------
        // STEP 1: Nearest smaller & greater to LEFT
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            int num = nums[i];

            // Maintain increasing stack (for minimum)
            while (!minStack.isEmpty() &&
                   nums[minStack.peek()] > num) {

                minStack.pop();
            }

            // Maintain decreasing stack (for maximum)
            while (!maxStack.isEmpty() &&
                   nums[maxStack.peek()] < num) {

                maxStack.pop();
            }

            // Store nearest smaller to left
            nearestSmallLeft[i] =
                (!minStack.isEmpty()) ? minStack.peek() : -1;

            // Store nearest greater to left
            nearestLargeLeft[i] =
                (!maxStack.isEmpty()) ? maxStack.peek() : -1;

            minStack.push(i);
            maxStack.push(i);
        }


        // Reset stacks
        minStack = new Stack<>();
        maxStack = new Stack<>();


        // --------------------------------------------------
        // STEP 2: Nearest smaller & greater to RIGHT
        // --------------------------------------------------
        for (int i = n - 1; i >= 0; i--) {

            int num = nums[i];

            // For minimum (handle duplicates carefully)
            while (!minStack.isEmpty() &&
                   nums[minStack.peek()] >= num) {

                minStack.pop();
            }

            // For maximum
            while (!maxStack.isEmpty() &&
                   nums[maxStack.peek()] <= num) {

                maxStack.pop();
            }

            nearestSmallRight[i] =
                (!minStack.isEmpty()) ? minStack.peek() : n;

            nearestLargeRight[i] =
                (!maxStack.isEmpty()) ? maxStack.peek() : n;

            minStack.push(i);
            maxStack.push(i);
        }


        // --------------------------------------------------
        // STEP 3: Contribution of each element
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            long num = nums[i];

            // --------------------------------------------------
            // Count how many subarrays where nums[i] is MIN
            // --------------------------------------------------
            int minLeftCount = i - nearestSmallLeft[i];
            int minRightCount = nearestSmallRight[i] - i;

            // Total subarrays where nums[i] is minimum
            int minContribution =
                minLeftCount * minRightCount;


            // --------------------------------------------------
            // Count how many subarrays where nums[i] is MAX
            // --------------------------------------------------
            int maxLeftCount = i - nearestLargeLeft[i];
            int maxRightCount = nearestLargeRight[i] - i;

            int maxContribution =
                maxLeftCount * maxRightCount;


            // --------------------------------------------------
            // Add net contribution
            // --------------------------------------------------
            //
            // Each subarray contributes:
            // max - min
            //
            // So:
            subarrRangeSum +=
                (long)(maxContribution - minContribution) * num;
        }

        return subarrRangeSum;
    }
}