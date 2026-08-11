class Solution {
    public int singleNonDuplicate(int[] nums) {

        // We are given a sorted array where:
        // - Every element appears exactly twice
        // - Except one element which appears only once
        // The goal is to find that single element in O(log n)

        int i = 0, j = nums.length - 1;

        // Edge case: if array has only one element, that must be the answer
        if (i == j) return nums[i];

        // Binary search on index
        while (i <= j) {

            // Pick middle index
            int mid = (i + j) / 2;

            // Get left and right neighbors safely
            // Using modulo avoids index out of bounds at edges
            int left  = nums[(nums.length + (mid - 1)) % nums.length];
            int right = nums[(nums.length + (mid + 1)) % nums.length];

            // If nums[mid] is different from both neighbors,
            // then it is the single element (not part of any pair)
            if (left != nums[mid] && right != nums[mid]) {
                return nums[mid];
            }

            // Now we determine the index of the "second element" of the pair
            // If left == nums[mid], then mid is the second element of the pair
            // Otherwise, mid is the first element and the pair ends at mid + 1
            int rightIdx = (left == nums[mid]) ? mid : mid + 1;

            // Key idea:
            // Before the single element, pairs start at EVEN indices
            // After the single element, pairs start at ODD indices
            //
            // If rightIdx is even, it means we are still on the LEFT side
            // of the single element → discard right part
            if (rightIdx % 2 == 0) {
                j = mid - 1;
            }
            // If rightIdx is odd, it means we are on the RIGHT side
            // of the single element → discard left part
            else {
                i = mid + 1;
            }
        }

        // If not found (theoretically should not happen as per constraints)
        return -1;
    }
}
