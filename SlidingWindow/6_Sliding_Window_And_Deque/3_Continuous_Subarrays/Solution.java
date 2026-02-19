import java.util.Deque;
import java.util.LinkedList;

class Solution {

    public long continuousSubarrays(int[] nums) {

        int n = nums.length;

        /*
         Constraint:
         We want subarrays where
         max - min <= 2
        */
        int limit = 2;

        long count = 0;

        /*
         minQueue:
             Monotonic increasing deque
             Front = minimum in current window
        */
        Deque<Integer> minQueue = new LinkedList<>();

        /*
         maxQueue:
             Monotonic decreasing deque
             Front = maximum in current window
        */
        Deque<Integer> maxQueue = new LinkedList<>();

        int start = 0, end = 0;

        while (end < n) {

            int num = nums[end];

            /*
             STEP 1:
             Maintain increasing order for minQueue.
             Remove larger elements from back.
            */
            while (!minQueue.isEmpty() &&
                   nums[minQueue.peekLast()] >= num) {

                minQueue.pollLast();
            }

            /*
             STEP 2:
             Maintain decreasing order for maxQueue.
             Remove smaller elements from back.
            */
            while (!maxQueue.isEmpty() &&
                   nums[maxQueue.peekLast()] <= num) {

                maxQueue.pollLast();
            }

            // Add current index to both deques
            minQueue.add(end);
            maxQueue.add(end);

            /*
             STEP 3:
             Shrink window if invalid.
             
             Condition:
                 max - min > limit
            */
            while (nums[maxQueue.peekFirst()] -
                   nums[minQueue.peekFirst()] > limit) {

                start++;

                /*
                 Remove outdated indices
                 if they fall out of window.
                */
                if (maxQueue.peekFirst() < start)
                    maxQueue.pollFirst();

                if (minQueue.peekFirst() < start)
                    minQueue.pollFirst();
            }

            /*
             KEY INSIGHT:
             If window [start ... end] is valid,
             then ALL subarrays ending at 'end'
             and starting from any index between
             start and end are valid.

             Number of such subarrays:
                 end - start + 1
            */
            count += end - start + 1;

            end++;
        }

        return count;
    }
}
