import java.util.ArrayList;
import java.util.List;

class Solution {
    public int maxSum(int[] nums1, int[] nums2) {

        int n1 = nums1.length;
        int n2 = nums2.length;

        long maxSum = 0;

        final int MOD = 1000000007;

        // We compute prefix sums for both arrays.
        //
        // Why?
        // Because we will split arrays at common elements
        // and need fast range sum computation.
        //
        // prefixSum[i] stores sum of elements before index i.
        long[] prefixSum1 = new long[n1+1];
        long[] prefixSum2 = new long[n2+1];

        for(int i=0; i<n1; i++)
            prefixSum1[i+1] = prefixSum1[i] + nums1[i];

        for(int i=0; i<n2; i++)
            prefixSum2[i+1] = prefixSum2[i] + nums2[i];

        // Store indices of common elements.
        //
        // Why?
        // Because at common elements,
        // we are allowed to switch from one array to the other.
        //
        // Between two common elements,
        // we must choose one array entirely.
        List<int[]> commonNumIndices = new ArrayList<>();

        // Add virtual starting boundary.
        // This simplifies segment calculation.
        commonNumIndices.add(new int[]{-1, -1});

        int i=0, j=0;

        // Two pointer traversal to find common elements.
        //
        // Arrays are sorted.
        while(i<n1 && j<n2) {

            int num1 = nums1[i];
            int num2 = nums2[j];

            if(num1<num2) {
                i++;
            }
            else if(num1>num2) {
                j++;
            }
            else {
                // Found common element.
                // Record indices where switching is allowed.
                commonNumIndices.add(new int[] {i, j});
                i++;
                j++;
            }
        }

        // Add virtual ending boundary.
        // This ensures final segment is processed.
        commonNumIndices.add(new int[]{n1-1, n2-1});

        int ptr = 0;

        // Now process segments between consecutive common points.
        while(ptr<commonNumIndices.size()-1) {

            int[] start = commonNumIndices.get(ptr);
            int[] end = commonNumIndices.get(ptr+1);

            // Compute sum of segment in nums1:
            //
            // prefixSum1[endIndex+1] - prefixSum1[startIndex+1]
            //
            // Why +1?
            // Because prefixSum is 1-index shifted.
            long sum1 =
                prefixSum1[end[0]+1]
              - prefixSum1[start[0]+1];

            long sum2 =
                prefixSum2[end[1]+1]
              - prefixSum2[start[1]+1];

            // Between two common elements,
            // we must choose the path giving maximum sum.
            maxSum = (maxSum + Math.max(sum1, sum2))%MOD;

            ptr++;
        }

        return (int)maxSum;
    }
}
