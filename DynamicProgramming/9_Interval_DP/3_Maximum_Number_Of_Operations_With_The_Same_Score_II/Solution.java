class Solution1 {
    public int maxOperations(int[] nums) {
        int n = nums.length;
        int maxOperation = 1;
        int firstTwoSum = nums[0] + nums[1];
        int lastTwoSum = nums[n-2] + nums[n-1];
        int firstLastSum = nums[0] + nums[n-1];
        maxOperation = Math.max(
            1 + recursion(nums, firstTwoSum, 2, n-1, new Integer[n][n]),
            Math.max(
                1 + recursion(nums, lastTwoSum, 0, n-3, new Integer[n][n]),
                1 + recursion(nums, firstLastSum, 1, n-2, new Integer[n][n])
            )
        );
        return maxOperation;
    }

    private int recursion(int[] nums, int target, int start, int end, Integer[][] memo) {
        if(start>=end) return 0;
        if(end-start==1) {
            if((nums[start] + nums[end])==target) return 1;
            else return 0;
        }
        if(memo[start][end]!=null) return memo[start][end];
        int maxOperation = 0;
        int firstTwoSum = nums[start] + nums[start+1];
        int lastTwoSum = nums[end-1] + nums[end];
        int firstLastSum = nums[start] + nums[end];
        if(firstTwoSum==target) {
            maxOperation = Math.max(
                maxOperation, 
                1 + recursion(nums, target, start+2, end, memo)
            );
        }
        if(lastTwoSum==target) {
            maxOperation = Math.max(
                maxOperation, 
                1 + recursion(nums, target, start, end-2, memo)
            );
        }
        if(firstLastSum==target) {
            maxOperation = Math.max(
                maxOperation, 
                1 + recursion(nums, target, start+1, end-1, memo)
            );
        }
        memo[start][end] = maxOperation;
        return maxOperation;
    }
}