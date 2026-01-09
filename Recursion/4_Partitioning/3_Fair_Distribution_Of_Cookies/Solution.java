class Solution {

    // Pattern: Backtracking / DFS (Distribution / Partition Variant)
    //
    // Core intuition:
    // We are not forming combinations of values; instead, we are assigning each
    // cookie bag to one of k children.
    //
    // At every step, we decide "which child should receive the current cookie bag".
    // The goal is to distribute all cookies such that the maximum load on any
    // child is minimized.
    //
    // This is a classic min–max optimization problem solved via backtracking.

    public int distributeCookies(int[] cookies, int k) {

        // distribution[i] represents the total cookies assigned to child i so far.
        // Initially, all children have 0 cookies.
        //
        // currMax keeps track of the maximum cookies any child has received
        // along the current assignment path.
        return recurrsion(cookies, 0, new int[k], 0);
    }

    private int recurrsion(
        int[] cookies,
        int ptr,
        int[] distribution,
        int currMax
    ) {

        // ptr represents the index of the current cookie bag to assign.
        //
        // Base case:
        // If all cookie bags have been assigned, the current maximum load
        // represents the unfairness of this particular distribution.
        if (ptr == cookies.length) {
            return currMax;
        }

        // We want to minimize the unfairness, so we start with a large value.
        int min = Integer.MAX_VALUE;

        // Try assigning the current cookie bag to every child.
        //
        // This loop represents the choice:
        // "Which child should receive cookies[ptr]?"
        for (int i = 0; i < distribution.length; i++) {

            // Assign cookies[ptr] to child i
            distribution[i] += cookies[ptr];

            // After assignment, update the maximum load seen so far.
            // This is the key optimization metric we are trying to minimize.
            //
            // We recurse to assign the next cookie bag.
            min = Math.min(
                min,
                recurrsion(
                    cookies,
                    ptr + 1,
                    distribution,
                    Math.max(currMax, distribution[i])
                )
            );

            // Backtracking step:
            // Remove the current cookie bag from child i
            // so that we can try assigning it to the next child.
            distribution[i] -= cookies[ptr];
        }

        // Among all possible assignments for cookies[ptr],
        // return the minimum unfairness achievable.
        return min;
    }
}
