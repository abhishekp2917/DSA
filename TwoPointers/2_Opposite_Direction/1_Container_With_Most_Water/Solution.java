class Solution {

    public int maxArea(int[] height) {

        int n = height.length;

        // maxArea will store the maximum water we can trap.
        int maxArea = 0;

        // Start with the widest possible container.
        int left = 0;
        int right = n - 1;

        while (left < right) {

            // Width is the distance between pointers.
            int width = right - left;

            // Height of container is limited by the shorter line.
            int minHeight = Math.min(height[left], height[right]);

            // Current area formed by left and right.
            int area = width * minHeight;

            // Update maximum area seen so far.
            maxArea = Math.max(maxArea, area);

            // Key logic:
            // Move the pointer that has smaller height.
            // Reason:
            // Area = width * min(height[left], height[right])
            // Since width decreases every step,
            // the only way to possibly increase area
            // is to try increasing the smaller height.

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
