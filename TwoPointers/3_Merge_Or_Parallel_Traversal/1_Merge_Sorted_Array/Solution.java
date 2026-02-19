class Solution {

    public void merge(int[] nums1, int m, int[] nums2, int n) {

        // We are given two sorted arrays:
        // nums1 has size m + n
        // First m elements are valid
        // Last n elements are placeholders (0s)
        //
        // nums2 has n valid sorted elements.
        //
        // Goal:
        // Merge nums2 into nums1 in sorted order.

        // Temporary array to store merged result.
        int[] mergedArr = new int[nums1.length];

        int ptr = 0;  // pointer for mergedArr
        int i = 0;    // pointer for nums1 (valid portion only)
        int j = 0;    // pointer for nums2

        // Continue until we exhaust both valid portions.
        // We use OR because we want to continue
        // until both arrays are fully consumed.
        while (i < m || j < n) {

            // If i exceeds valid portion of nums1,
            // treat its value as infinity so nums2 gets picked.
            int num1 = (i < m) ? nums1[i] : Integer.MAX_VALUE;

            // If j exceeds nums2,
            // treat its value as infinity so nums1 gets picked.
            int num2 = (j < n) ? nums2[j] : Integer.MAX_VALUE;

            // Choose the smaller element and move its pointer.
            if (num1 <= num2) {
                mergedArr[ptr] = num1;
                i++;
            } else {
                mergedArr[ptr] = num2;
                j++;
            }

            ptr++;
        }

        // Copy merged result back into nums1.
        for (int idx = 0; idx < nums1.length; idx++) {
            nums1[idx] = mergedArr[idx];
        }
    }
}
