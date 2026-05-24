import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public int countRangeSum(int[] nums, int lower, int upper) {

        int n = nums.length;
        int result = 0;

        // rangeSum(i...j) = prefixSum[j+1] - prefixSum[i]
        // We need: lower <= (prefixSum[j] - prefixSum[i]) <= upper
        long[] prefixSum = new long[n + 1];

        for(int i=0; i<n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        // Coordinate Compression:
        //
        // For every current prefix sum, we need ranks for: prefixSum[i], (prefixSum[i] - lower) and (prefixSum[i] - upper)
        // because all participate in BIT queries.
        int[][] ranks = getRanks(prefixSum, lower, upper);


        // Fenwick Tree stores frequencies of previously seen prefix sums.
        int[] fenwickTree = new int[3 * (ranks.length) + 1];


        // Process prefix sums from LEFT to RIGHT.
        //
        // WHY?
        // While processing current prefix sum,
        // BIT already contains all previous prefix sums.
        for(int i=0; i<=n; i++) {

            int rank = ranks[i][0];

            // rankLower: rank of prefixSum[i] - upper
            //
            // rankUpper: rank of prefixSum[i] - lower
            int rankLower = ranks[i][1];
            int rankUpper = ranks[i][2];

            // We need previous prefix sums satisfying: (prefixSum[i] - upper) <= prevPrefix <= (prefixSum[i] - lower)
            // query(rankUpper): count <= upper bound
            // query(rankLower - 1): remove counts smaller than lower bound
            result += query(fenwickTree, rankUpper) - query(fenwickTree, rankLower - 1);

            // Insert current prefix sum into BIT
            // so future prefix sums can use it.
            update(fenwickTree, rank);
        }
        return result;
    }


    private int query(int[] fenwickTree, int rank) {
        int prefixCount = 0;

        // Standard BIT prefix query:
        // collect frequencies from [1 ... rank]
        while(rank > 0) {
            prefixCount += fenwickTree[rank];

            // move to parent prefix chunk
            rank -= (rank & -rank);
        }
        return prefixCount;
    }


    private void update(int[] fenwickTree, int rank) {
        // Standard BIT update:
        // update all parent binary ranges containing this rank.
        while(rank < fenwickTree.length) {
            fenwickTree[rank]++;

            // move to next responsible parent range
            rank += (rank & -rank);
        }
    }


    private int[][] getRanks(long[] prefixSum, int lower, int upper) {
        int n = prefixSum.length;
        int[][] ranks = new int[n][3];
        List<Long> sums = new ArrayList<>();

        // Store all values participating in comparisons:
        //
        // prefixSum[i], (prefixSum[i] - lower) and (prefixSum[i] - upper)
        for(long sum : prefixSum) {
            sums.add(sum);
            sums.add(sum - lower);
            sums.add(sum - upper);
        }

        // Sort for coordinate compression.
        Collections.sort(sums);
        Map<Long, Integer> rankMap = new HashMap<>();
        int rank = 1;

        // Assign compact ranks while preserving ordering.
        for(long sum : sums) {
            rankMap.putIfAbsent(sum, rank++);
        }

        // ranks[i][0]: rank of current prefix sum
        // ranks[i][1]: rank of lower bound
        // ranks[i][2]: rank of upper bound
        for(int i=0; i<n; i++) {
            long sum = prefixSum[i];
            int sumRank = rankMap.get(sum);
            int rankLower = rankMap.get(sum - upper);
            int rankUpper = rankMap.get(sum - lower);
            ranks[i] = new int[] { sumRank, rankLower, rankUpper };
        }
        return ranks;
    }
}