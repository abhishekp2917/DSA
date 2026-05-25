import java.util.ArrayList;
import java.util.List;

class Solution {

    public List<Integer> countOfPeaks(int[] nums, int[][] queries) {
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();

        // peak[i] = 1 if nums[i] is peak
        //
        // Peak condition:
        // nums[i] > nums[i-1]
        // AND
        // nums[i] > nums[i+1]
        int[] peak = new int[n];

        // First transform the input into Peak array and use this transformed array to build Fenwick Tree
        for(int i=1; i<n-1; i++) {
            if(nums[i] > nums[i-1] && nums[i] > nums[i+1]) {
                peak[i] = 1;
            }
        }

        // Fenwick Tree stores frequency of peaks.
        //
        // query(r) - query(l-1):
        // gives number of peaks in range.
        int[] fenwickTree = new int[n + 1];

        // Build BIT using initial peak array.
        build(peak, fenwickTree);

        for(int[] query : queries) {

            // Type 1:
            // count peaks in range
            if(query[0] == 1) {
                int left = query[1];
                int right = query[2];

                // Range smaller than size 3
                // cannot contain any peak.
                if(right - left + 1 < 3) {
                    ans.add(0);
                }
                else {

                    // IMPORTANT:
                    // Boundary elements cannot become peak
                    // because peak needs both neighbors.
                    //
                    // So valid peak positions are:
                    // [left+1 ... right-1]
                    ans.add(
                        query(right - 1, fenwickTree) - query(left, fenwickTree)
                    );
                }
            }

            // Type 2:
            // update nums[idx]
            else {
                int idx = query[1];
                int val = query[2];
                nums[idx] = val;

                // Updating nums[idx] can only affect:
                //
                // idx-1
                // idx
                // idx+1
                //
                // because peak condition depends only on neighbors.
                for(int i=Math.max(idx-1, 1); i<=Math.min(idx+1, n-2); i++) {

                    // Recalculate peak state.
                    int newPeak = (nums[i] > nums[i-1] && nums[i] > nums[i+1])? 1 : 0;

                    // delta tells how BIT frequency changes.
                    //
                    // Example:
                    // old peak = 1
                    // new peak = 0
                    //
                    // delta = -1
                    int delta = newPeak - peak[i];
                    peak[i] = newPeak;

                    // Update BIT with frequency change.
                    update(i, delta, fenwickTree);
                }
            }
        }
        return ans;
    }


    private void build(int[] peak, int[] fenwickTree) {
        // O(n) BIT build.
        //
        // Each node pushes contribution upward once.
        for(int i=1; i<=peak.length; i++) {
            fenwickTree[i] += peak[i-1];
            int nextIdx = i + (i & -i);
            if(nextIdx < fenwickTree.length) {
                fenwickTree[nextIdx] += fenwickTree[i];
            }
        }
    }


    private int query(int idx, int[] fenwickTree) {
        // incrementing by 1 because fenwick tree has 1-based indexing
        idx++;
        int prefixCount = 0;

        // Standard BIT prefix query:
        // collect frequencies from [0...idx]
        while(idx > 0) {
            prefixCount += fenwickTree[idx];

            // move to parent prefix chunk
            idx -= (idx & -idx);
        }
        return prefixCount;
    }


    private void update(int idx, int val, int[] fenwickTree) {
        // incrementing by 1 because fenwick tree has 1-based indexing
        idx++;

        // Standard BIT update:
        // update all parent binary ranges containing idx.
        while(idx < fenwickTree.length) {
            fenwickTree[idx] += val;

            // move to next responsible parent range
            idx += (idx & -idx);
        }
    }
}