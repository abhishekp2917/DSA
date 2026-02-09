import java.util.Deque;
import java.util.LinkedList;

class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;

        /*
         Deque will store INDEXES, not values.

         Why store indexes?
         1) To check if element is out of window.
         2) To compare values using nums[index].

         This deque will maintain a MONOTONIC DECREASING order
         of values (front = largest).
        */
        Deque<Integer> deque = new LinkedList<>();

        int[] windowMax = new int[n - k + 1];
        int idx = 0;

        // Expand window using end pointer
        for (int end = 0; end < n; end++) {

            /*
             Compute start index of window.
             Window = [start ... end]
            */
            int start = end - k + 1;

            int num = nums[end];

            /*
             STEP 1:
             Remove elements from FRONT
             if they are out of window.

             Why?
             Because deque stores indexes.
             If index < start,
             it no longer belongs to current window.
            */
            while (!deque.isEmpty() && deque.peekFirst() < start) {
                deque.pollFirst();
            }

            /*
             STEP 2:
             Maintain MONOTONIC DECREASING order.

             Remove elements from BACK
             while their value is smaller than current num.

             Why?
             Because smaller elements can NEVER become max
             if a bigger element comes after them.

             They are useless for all future windows.
            */
            while (!deque.isEmpty() && nums[deque.peekLast()] < num) {
                deque.pollLast();
            }

            /*
             Add current index to deque.
             It is a potential maximum candidate.
            */
            deque.add(end);

            /*
             If window size >= k,
             record maximum.

             The maximum is always at FRONT
             because we maintain decreasing order.
            */
            if (end >= k - 1) {
                windowMax[idx] = nums[deque.peekFirst()];
                idx++;
            }
        }

        return windowMax;
    }
}
