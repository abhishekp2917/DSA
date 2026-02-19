import java.util.Deque;
import java.util.LinkedList;

class Solution {

    public int maximumRobots(int[] chargeTimes,
                             int[] runningCosts,
                             long budget) {

        int n = chargeTimes.length;

        int maxRobot = 0;

        /*
         We need to maintain:

         max(chargeTimes in window)
         +
         windowSize * (sum of runningCosts in window)
         <= budget
        */

        /*
         Monotonic deque to maintain maximum chargeTime.

         Deque stores INDEXES.
         Maintains decreasing order of chargeTimes.
         Front = index of maximum chargeTime.
        */
        Deque<Integer> chargeTimeDeque = new LinkedList<>();

        long currCost = 0;

        /*
         runningCostSum:
             sum of runningCosts in current window
        */
        long runningCostSum = 0;

        int start = 0;

        for (int end = 0; end < n; end++) {

            int chargeTime = chargeTimes[end];

            /*
             STEP 1:
             Maintain decreasing deque for chargeTimes.

             Remove smaller values from back.
             
             Why?
             Because they can never become max
             in this or future windows.
            */
            while (!chargeTimeDeque.isEmpty() &&
                   chargeTimes[chargeTimeDeque.peekLast()] <= chargeTime) {

                chargeTimeDeque.pollLast();
            }

            chargeTimeDeque.add(end);

            /*
             Add running cost of current robot.
            */
            runningCostSum += runningCosts[end];

            /*
             Compute total cost of current window:
             
             cost =
                 max(chargeTime)
                 +
                 windowSize * runningCostSum
            */
            currCost =
                chargeTimes[chargeTimeDeque.peekFirst()]
                +
                (long)(end - start + 1) * runningCostSum;

            /*
             STEP 2:
             If cost exceeds budget,
             shrink window from left.
            */
            while (start <= end && currCost > budget) {

                /*
                 Remove max chargeTime
                 if it goes out of window.
                */
                if (chargeTimeDeque.peekFirst() <= start)
                    chargeTimeDeque.pollFirst();

                /*
                 Remove running cost
                 of outgoing robot.
                */
                runningCostSum -= runningCosts[start];

                start++;

                /*
                 Recompute cost if window valid.
                */
                if (start <= end) {
                    currCost =
                        chargeTimes[chargeTimeDeque.peekFirst()]
                        +
                        (long)(end - start + 1) * runningCostSum;
                }
                else {
                    currCost = 0;
                }
            }

            /*
             Window is valid.
             Update maximum length.
            */
            maxRobot = Math.max(maxRobot,
                                end - start + 1);
        }

        return maxRobot;
    }
}
