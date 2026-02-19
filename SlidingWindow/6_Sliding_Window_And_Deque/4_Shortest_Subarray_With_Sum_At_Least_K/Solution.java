import java.util.Deque;
import java.util.LinkedList;

class Solution {

    public int shortestSubarray(int[] nums, int k) {

        int n = nums.length;

        /*
         We want:
             shortest subarray with sum >= k

         Problem:
             Array can contain negative numbers.
             So normal sliding window doesn't work.
        */

        int minLen = n + 1;

        /*
         Prefix sum:
             prefixSum[i] = sum of first i elements
         
         Subarray sum from j to i-1:
             prefixSum[i] - prefixSum[j]
        */
        long[] prefixSum = new long[n + 1];

        for (int i = 0; i < n; i++)
            prefixSum[i + 1] = prefixSum[i] + nums[i];

        /*
         Deque stores indices of prefixSum.
         
         It will maintain prefix sums
         in increasing order.
        */
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i <= n; i++) {

            /*
             STEP 1:
             Try to shrink from left
             while subarray sum >= k

             prefixSum[i] - prefixSum[j] >= k
             where j = deque.peekFirst()

             If condition satisfied,
             update answer and remove j.
             
             Why remove?
             Because any future i will
             produce longer subarray.
            */
            while (deque.size() > 0 &&
                   prefixSum[i] - prefixSum[deque.peekFirst()] >= k) {

                minLen = Math.min(minLen,
                                  i - deque.pollFirst());
            }

            /*
             STEP 2:
             Maintain increasing prefix sums in deque.

             If current prefixSum[i] is smaller
             than last stored prefix sum,
             then last one is useless.

             Why?
             Because:
                 prefixSum[i] <= prefixSum[j]
                 and i > j

             So using i gives:
                 Larger difference
                 and shorter length.
            */
            while (deque.size() > 0 &&
                   prefixSum[deque.peekLast()] >= prefixSum[i]) {

                deque.pollLast();
            }

            /*
             Add current index
             as future candidate.
            */
            deque.add(i);
        }

        return (minLen != n + 1) ? minLen : -1;
    }
}
