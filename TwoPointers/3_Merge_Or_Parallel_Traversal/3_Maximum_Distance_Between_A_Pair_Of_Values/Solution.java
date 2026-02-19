class Solution {

    public int maxDistance(int[] nums1, int[] nums2) {

        // Both arrays are non-increasing (sorted in descending order).
        // We need to find maximum distance (j - i)
        // such that:
        //      i <= j
        //      nums1[i] <= nums2[j]

        int ptr1 = 0;   // pointer for nums1
        int ptr2 = 0;   // pointer for nums2

        int maxDist = 0;

        // Traverse both arrays
        while (ptr1 < nums1.length && ptr2 < nums2.length) {

            // Ensure j >= i condition.
            // If ptr2 is behind ptr1, move it forward.
            while (ptr2 < ptr1) {
                ptr2++;
            }

            // If ptr2 goes out of bounds, no further valid pair.
            if (ptr2 >= nums2.length) break;

            // Check if current pair satisfies condition.
            if (nums2[ptr2] >= nums1[ptr1]) {

                // Valid pair found.
                // Update maximum distance.
                maxDist = Math.max(maxDist, ptr2 - ptr1);

                // Try to increase distance further
                // by moving ptr2 forward.
                ptr2++;
            }
            else {
                // Condition fails:
                // Since arrays are non-increasing,
                // increasing ptr1 will reduce nums1[ptr1]
                // which makes condition easier to satisfy.
                ptr1++;
            }
        }

        return maxDist;
    }
}
