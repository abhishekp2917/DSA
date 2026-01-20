class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // We need to find the median of two sorted arrays
        // in O(log(min(n, m))) time.

        int n1 = nums1.length, n2 = nums2.length;

        // If total length is EVEN:
        // median = average of (total/2)-th and (total/2 + 1)-th elements
        if ((n1 + n2) % 2 == 0) {

            double a = findKth(nums1, nums2, (n1 + n2) / 2);
            double b = findKth(nums1, nums2, (n1 + n2) / 2 + 1);

            return (a + b) / 2.0;
        }

        // If total length is ODD:
        // median = (total/2 + 1)-th element
        else {
            return findKth(nums1, nums2, (n1 + n2) / 2 + 1);
        }
    }

    private double findKth(int[] nums1, int[] nums2, int k) {

        // We want to find the k-th smallest element
        // in the combined sorted array nums1 + nums2.

        int n1 = nums1.length, n2 = nums2.length;

        // We binary search on how many elements we take from nums1.

        // cnt1 = number of elements taken from nums1
        // cnt2 = number of elements taken from nums2 = k - cnt1

        // cnt1 must satisfy:
        //   0 <= cnt1 <= n1
        //   0 <= cnt2 <= n2  →  k - cnt1 <= n2  →  cnt1 >= k - n2

        // So valid range of cnt1:
        int low  = Math.max(0, k - n2);
        int high = Math.min(k, n1);

        // Binary search on cnt1
        while (low <= high) {

            // Try taking cnt1 elements from nums1
            int cnt1 = low + (high - low) / 2;

            // Remaining elements must come from nums2
            int cnt2 = k - cnt1;

            // Define the boundary values around the cut:

            // left1  = largest element on left side from nums1
            // right1 = smallest element on right side from nums1
            int left1  = (cnt1 == 0)  ? Integer.MIN_VALUE : nums1[cnt1 - 1];
            int right1 = (cnt1 == n1) ? Integer.MAX_VALUE : nums1[cnt1];

            // left2  = largest element on left side from nums2
            // right2 = smallest element on right side from nums2
            int left2  = (cnt2 == 0)  ? Integer.MIN_VALUE : nums2[cnt2 - 1];
            int right2 = (cnt2 == n2) ? Integer.MAX_VALUE : nums2[cnt2];

            // Check if we found a VALID partition:
            //
            // All elements on left side must be <= all elements on right side
            if (left1 <= right2 && left2 <= right1) {

                // Exactly k elements are on the left side
                // The k-th smallest is the maximum of left boundaries
                return Math.max(left1, left2);
            }

            // If left1 is too big, we took too many from nums1
            // We must move left in nums1
            else if (left1 > right2) {

                high = cnt1 - 1;
            }

            // If left2 is too big, we took too few from nums1
            // We must move right in nums1
            else {

                low = cnt1 + 1;
            }
        }

        // This line should never be reached (problem guarantees answer exists)
        return -1;
    }
}
