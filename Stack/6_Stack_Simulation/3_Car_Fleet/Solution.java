import java.util.*;

class Solution {

    public int carFleet(int target, int[] position, int[] speed) {

        // --------------------------------------------------
        // STEP 1: Build (distanceToTarget, speed)
        // --------------------------------------------------
        //
        // distance = target - position
        //
        // Why distance?
        // Easier to compute time = distance / speed
        List<int[]> cars = new ArrayList<>();

        for (int i = 0; i < position.length; i++) {
            cars.add(new int[]{ target - position[i], speed[i] });
        }

        // --------------------------------------------------
        // STEP 2: Sort by distance DESC
        // --------------------------------------------------
        //
        // Closest to target first
        Collections.sort(cars, (a, b) -> b[0] - a[0]);


        // --------------------------------------------------
        // STEP 3: Use stack to track fleets
        // --------------------------------------------------
        Stack<Float> stack = new Stack<>();

        for (int[] car : cars) {

            float dist = car[0];
            float spd  = car[1];

            // Time to reach target
            float time = dist / spd;

            // --------------------------------------------------
            // If current car catches up with fleet ahead
            // --------------------------------------------------
            //
            // If its time >= fleet ahead time:
            // → it merges into that fleet
            while (!stack.isEmpty() && stack.peek() <= time) {
                stack.pop();
            }

            // New fleet (or merged result)
            stack.push(time);
        }

        return stack.size();
    }
}