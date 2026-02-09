import java.util.Deque;
import java.util.LinkedList;

class Solution {

    public int longestSubarray(int[] nums, int limit) {

        int n = nums.length;
        int maxLen = 0;

        /*
         minQueue:
             Maintains indexes of elements
             in MONOTONIC INCREASING order.
             
             Front = smallest element in window.
        */
        Deque<Integer> minQueue = new LinkedList<>();

        /*
         maxQueue:
             Maintains indexes of elements
             in MONOTONIC DECREASING order.
             
             Front = largest element in window.
        */
        Deque<Integer> maxQueue = new LinkedList<>();

        int start = 0, end = 0;

        while (end < n) {

            int num = nums[end];

            /*
             STEP 1:
             Maintain minQueue increasing.
             
             Remove all elements from back
             that are >= current number.

             Why?
             Because they can never be minimum
             in this or any future window
             that includes current number.
            */
            while (!minQueue.isEmpty() &&
                   nums[minQueue.peekLast()] >= num) {

                minQueue.pollLast();
            }

            /*
             STEP 2:
             Maintain maxQueue decreasing.

             Remove all elements from back
             that are <= current number.

             Why?
             Because they can never be maximum
             anymore.
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
             Check window validity.

             Condition:
                 max - min <= limit

             If invalid,
                 shrink from left.
            */
            while (nums[maxQueue.peekFirst()] -
                   nums[minQueue.peekFirst()] > limit) {

                start++;

                /*
                 Remove outdated indexes
                 from front of deques.
                */
                if (maxQueue.peekFirst() < start)
                    maxQueue.pollFirst();

                if (minQueue.peekFirst() < start)
                    minQueue.pollFirst();
            }

            /*
             At this point,
             window is valid.

             Update maximum length.
            */
            maxLen = Math.max(maxLen, end - start + 1);

            end++;
        }

        return maxLen;
    }
}
