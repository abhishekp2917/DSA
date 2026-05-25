import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {

    public long numberOfPairs(int[] nums1, int[] nums2, int diff) {
        int n = nums1.length;
        long pairCount = 0;

        // Original condition: nums1[i] - nums1[j] <= nums2[i] - nums2[j] + diff
        // Rearranging: (nums1[i] - nums2[i]) <= (nums1[j] - nums2[j]) + diff
        //
        // Let: numsDiff[i] = nums1[i] - nums2[i]
        // Then: numsDiff[i] <= numsDiff[j] + diff
        //
        // For every current index j, we need count of previous i values satisfying above condition.
        int[] numsDiff = new int[n];

        for(int i=0; i<n; i++) {
            numsDiff[i] = nums1[i] - nums2[i];
        }

        // Coordinate Compression: numsDiff values can be large/negative, so we compress them into compact ranks.
        //
        // IMPORTANT:
        // We need ranks for BOTH:
        //      numsDiff[i]
        //      numsDiff[i] + diff
        //
        // because both participate in queries.
        int[] ranks = getRanks(numsDiff, diff);

        // Fenwick Tree stores frequency of previously processed ranks.
        int[] fenwickTree = new int[2*n + 1];

        // Process from LEFT to RIGHT.
        //
        // WHY?
        // While processing current index, BIT already contains all previous values.
        for(int i=0; i<n; i++) {

            int rank = ranks[i];

            // diffRank represents: numsDiff[i] + diff
            int diffRank = ranks[i+n];

            // query(diffRank) gives count of previous values <= (numsDiff[i] + diff)
            // which directly satisfies required condition.
            int lowerRankCount = query(fenwickTree, diffRank);
            pairCount += lowerRankCount;

            // Insert current numsDiff into BIT so future indices can use it.
            update(fenwickTree, rank);
        }

        return pairCount;
    }

    private void update(int[] fenwickTree, int rank) {

        // Standard BIT updates all parent binary ranges containing this rank.
        while(rank < fenwickTree.length) {
            fenwickTree[rank]++;

            // move to next responsible parent range
            rank += (rank & -rank);
        }
    }

    private int query(int[] fenwickTree, int rank) {

        int prefixCount = 0;

        // Standard BIT prefix query collects frequencies from [1 ... rank]
        while(rank > 0) {
            prefixCount += fenwickTree[rank];

            // move to parent prefix chunk
            rank -= (rank & -rank);
        }
        return prefixCount;
    }

    private int[] getRanks(int[] diffArr, int diff) {

        int n = diffArr.length;
        int[] ranks = new int[2*n];
        Map<Integer, Integer> rankMap = new HashMap<>();

        // Store both:
        //      numsDiff[i]
        //      numsDiff[i] + diff
        //
        // because both values participate in comparisons.
        int[] sortedDiffArr = new int[2*n];

        for(int i=0; i<n; i++) {
            sortedDiffArr[i] = diffArr[i];
            sortedDiffArr[i+n] = diffArr[i] + diff;
        }

        // Sort for coordinate compression.
        Arrays.sort(sortedDiffArr);
        int rank = 1;

        // Assign compact ranks while preserving ordering.
        // Equal values must get same rank.
        for(int num : sortedDiffArr) {
            rankMap.putIfAbsent(num, rank++);
        }

        // First n positions: ranks of numsDiff[i]
        // Next n positions: ranks of numsDiff[i] + diff
        for(int i=0; i<n; i++) {
            ranks[i] = rankMap.get(diffArr[i]);
            ranks[i+n] = rankMap.get(diffArr[i] + diff);
        }

        return ranks;
    }
}