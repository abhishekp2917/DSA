class Solution1 {

    public int rob(int[] nums) {

        int n = nums.length;

        // memo[i]:
        // stores maximum money that can be robbed
        // considering houses from 0 to i.
        //
        // Prevents solving the same subproblem repeatedly.
        Integer[] memo = new Integer[n];

        return recursion(nums, n-1, memo);
    }

    private int recursion(int[] nums, int currHouse, Integer[] memo) {

        // Base Case:
        //
        // No house left to consider.
        // Therefore no money can be robbed.
        if (currHouse < 0) return 0;

        // Return previously computed answer.
        if (memo[currHouse]!=null) return memo[currHouse];

        // Option 1:
        // Skip current house.
        //
        // Then answer becomes whatever maximum
        // could be robbed till previous house.
        int maxMoneyTillPrevHouse =
            recursion(nums, currHouse-1, memo);

        // Option 2:
        // Rob current house.
        //
        // Adjacent house cannot be robbed,
        // so continue from house (currHouse-2).
        int maxMoneyTillBeforePrevHouse =
            recursion(nums, currHouse-2, memo);

        // Choose better of:
        // rob current house
        // OR
        // skip current house.
        memo[currHouse] = Math.max(
            nums[currHouse] + maxMoneyTillBeforePrevHouse,
            maxMoneyTillPrevHouse
        );

        return memo[currHouse];
    }
}

class Solution2 {
    
    public int rob(int[] nums) {

        int n = nums.length;

        // dp[i]:
        // maximum money that can be robbed
        // considering houses from 0 to i.
        int[] dp = new int[n];

        // Base Case:
        // Only first house is available.
        dp[0] = nums[0];

        // Build answers from left to right.
        for(int currHouse=1; currHouse<n; currHouse++) {

            // Option 1:
            // Skip current house.
            int maxMoneyTillPrevHouse =
                dp[currHouse-1];

            // Option 2:
            // Rob current house.
            //
            // Adjacent house cannot be robbed,
            // so add answer till (currHouse-2).
            int maxMoneyTillBeforePrevHouse =
                (currHouse-2>=0)
                ? dp[currHouse-2]
                : 0;

            // Choose the better decision.
            dp[currHouse] = Math.max(
                nums[currHouse] + maxMoneyTillBeforePrevHouse,
                maxMoneyTillPrevHouse
            );
        }

        // Last state stores answer
        // for all houses.
        return dp[n-1];
    }
}