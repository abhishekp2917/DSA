import java.util.*;

class Solution {

    public double[] getCollisionTimes(int[][] cars) {

        int n = cars.length;

        // --------------------------------------------------
        // Result: collision time for each car
        // -1 means no collision
        // --------------------------------------------------
        double[] res = new double[n];
        Arrays.fill(res, -1.0);

        // Stack stores indices of cars
        // IMPORTANT: maintains valid collision candidates
        Stack<Integer> stack = new Stack<>();


        // --------------------------------------------------
        // STEP 1: Traverse from RIGHT → LEFT
        // --------------------------------------------------
        //
        // Why?
        // Because collision depends on cars ahead
        for (int i = n - 1; i >= 0; i--) {

            int pos = cars[i][0];
            int speed = cars[i][1];

            // --------------------------------------------------
            // STEP 2: Remove invalid candidates
            // --------------------------------------------------
            while (!stack.isEmpty()) {

                int j = stack.peek();

                int posJ = cars[j][0];
                int speedJ = cars[j][1];

                // --------------------------------------------------
                // CASE 1: Cannot catch up
                // --------------------------------------------------
                //
                // If current car is slower or equal
                if (speed <= speedJ) {
                    stack.pop();
                }

                else {

                    // Time to collide with j
                    double time =
                        (double)(posJ - pos) / (speed - speedJ);

                    // --------------------------------------------------
                    // CASE 2: Front car collides earlier
                    // --------------------------------------------------
                    //
                    // If j collides BEFORE we reach it
                    // → j changes speed → invalid target
                    if (res[j] > 0 && time > res[j]) {
                        stack.pop();
                    }

                    else {
                        break;
                    }
                }
            }

            // --------------------------------------------------
            // STEP 3: Compute collision time
            // --------------------------------------------------
            if (!stack.isEmpty()) {

                int j = stack.peek();

                res[i] =
                    (double)(cars[j][0] - cars[i][0]) /
                    (cars[i][1] - cars[j][1]);
            }

            // --------------------------------------------------
            // STEP 4: Add current car as candidate
            // --------------------------------------------------
            stack.push(i);
        }

        return res;
    }
}