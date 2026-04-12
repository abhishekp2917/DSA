import java.util.*;

class Solution {

    public int[] asteroidCollision(int[] asteroids) {

        // Stack stores surviving asteroids
        Deque<Integer> stack = new ArrayDeque<>();

        // --------------------------------------------------
        // STEP 1: Process each asteroid
        // --------------------------------------------------
        for (int asteroid : asteroids) {

            // --------------------------------------------------
            // CASE 1: Moving LEFT (negative)
            // --------------------------------------------------
            if (asteroid < 0) {

                // Collision possible ONLY when:
                // top is positive (moving right)
                while (!stack.isEmpty() &&
                       stack.peek() > 0 &&
                       stack.peek() < Math.abs(asteroid)) {

                    // smaller right-moving asteroid explodes
                    stack.pop();
                }

                // --------------------------------------------------
                // Now handle remaining cases
                // --------------------------------------------------

                if (stack.isEmpty()) {

                    // no collision → push
                    stack.push(asteroid);
                }

                else {

                    if (stack.peek() > 0 &&
                        stack.peek() == Math.abs(asteroid)) {

                        // equal size → both explode
                        stack.pop();
                    }

                    else if (stack.peek() < 0) {

                        // same direction (both left)
                        stack.push(asteroid);
                    }

                    // else:
                    // stack.peek() > abs(asteroid)
                    // → current asteroid destroyed
                }
            }

            // --------------------------------------------------
            // CASE 2: Moving RIGHT (positive)
            // --------------------------------------------------
            else {
                stack.push(asteroid);
            }
        }


        // --------------------------------------------------
        // STEP 2: Build result array
        // --------------------------------------------------
        int[] ans = new int[stack.size()];

        int i = stack.size() - 1;

        while (!stack.isEmpty()) {
            ans[i--] = stack.pop();
        }

        return ans;
    }
}