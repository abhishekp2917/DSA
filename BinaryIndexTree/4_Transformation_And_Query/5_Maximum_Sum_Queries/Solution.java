import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int n = nums1.length;
        int q = queries.length;
        int[] ans = new int[q];

        // pairs[i] = { nums1[i], nums2[i] }
        int[][] pairs = new int[n][2];

        // Store: { originalQueryIndex, x, y }
        // We store original index because queries will be sorted offline.
        int[][] idxQueries = new int[q][3];


        // Coordinate Compression on nums2 + query y values.
        //
        // IMPORTANT:
        // We use INVERSE ranks.
        //
        // WHY?
        // Query condition is (nums2 >= y) i.e (rank(nums2) >= rank(y)). So, the query range will become [MAX_RANK, rank(y)]
        // But Fenwick Tree query works on PREFIX i.e [1, rank(y)]
        // Inverse ranking converts larger nums2 -> smaller rank
        //
        // So, new condition will become (rank(nums2) <= rank(y)), which makes query range [1, rank(y)] 
        Map<Integer, Integer> rankMap = getInverseRankMap(nums2, queries);
        final int MAX_RANK = rankMap.size();
        // Build pair array.
        for(int i=0; i<n; i++) {
            pairs[i] = new int[] { nums1[i], nums2[i] };
        }
        // Store query index with query values.
        for(int i=0; i<q; i++) {
            idxQueries[i] = new int[] { i, queries[i][0], queries[i][1] };
        }
        // Sort pairs in descending nums1 order.
        //
        // WHY?
        // Queries require:
        // nums1 >= x
        //
        // While processing query x,
        // all processed pairs automatically satisfy nums1 >= x.
        Arrays.sort(pairs, (p1, p2) -> p2[0] - p1[0]);

        // Sort queries in descending x order.
        Arrays.sort(idxQueries, (q1, q2) -> q2[1] - q1[1]);

        // Fenwick Tree stores MAXIMUM:
        // nums1 + nums2
        //
        // for processed nums2 ranks.
        //
        // BIT here is NOT storing frequency/sum.
        // It stores PREFIX MAXIMUM.
        int[] fenwickTree = new int[MAX_RANK + 1];
        Arrays.fill(fenwickTree, -1);
        int pairIdx = 0;
        // Process queries offline in descending x order.
        for(int[] query : idxQueries) {
            int idx = query[0];
            int x = query[1];
            int y = query[2];
            int yRank = rankMap.get(y);

            // Insert all pairs satisfying:
            // nums1 >= x
            //
            // Since pairs are sorted descending,
            // once condition fails, remaining pairs also fail.
            while(pairIdx < pairs.length && pairs[pairIdx][0] >= x) {
                int num2 = pairs[pairIdx][1];
                int nums2Rank = rankMap.get(num2);
                // candidate answer:
                // nums1 + nums2
                int val = pairs[pairIdx][0] + pairs[pairIdx][1];
                // Update BIT with maximum value for this nums2 rank.
                update(nums2Rank, val, fenwickTree);
                pairIdx++;
            }

            // query(yRank):
            // gives maximum value among:
            //
            // nums2 >= y
            //
            // because inverse ranking converts suffix query into prefix query.
            ans[idx] = query(yRank, fenwickTree);
        }
        return ans;
    }


    private int query(int rank, int[] fenwickTree) {
        int prefixMax = -1;
        // Standard BIT prefix query.
        //
        // Instead of sum,
        // we collect MAXIMUM from prefix chunks.
        while(rank > 0) {
            prefixMax = Math.max(prefixMax, fenwickTree[rank]);

            // move to parent prefix chunk
            rank -= (rank & -rank);
        }
        return prefixMax;
    }


    private void update(int rank, int val, int[] fenwickTree) {
        // Standard BIT update.
        //
        // Every parent binary range containing this rank
        // must also maintain updated maximum.
        while(rank < fenwickTree.length) {
            fenwickTree[rank] = Math.max(fenwickTree[rank], val);

            // move to next responsible parent range
            rank += (rank & -rank);
        }
    }


    private Map<Integer, Integer> getInverseRankMap(int[] nums2, int[][] queries) {
        List<Integer> nums = new ArrayList<>();
        Map<Integer, Integer> rankMap = new HashMap<>();

        // Collect all nums2 values.
        for(int num : nums2) {
            nums.add(num);
        }
        // Collect all query y values.
        //
        // IMPORTANT:
        // query values also need ranks
        // because queries happen on compressed space.
        for(int[] query : queries) {
            nums.add(query[1]);
        }

        // Sort for coordinate compression.
        Collections.sort(nums);
        int rank = 1;
        // Assign normal increasing ranks.
        for(int num : nums) {
            if(!rankMap.containsKey(num)) {
                rankMap.put(num, rank++);
            }
        }

        // Convert into INVERSE ranks.
        //
        // Example:
        //
        // original:
        // 1 -> 1
        // 5 -> 2
        // 10 -> 3
        //
        // inverse:
        // 1 -> 3
        // 5 -> 2
        // 10 -> 1
        //
        // WHY?
        // We need:
        // nums2 >= y
        //
        // Inverse ranking converts suffix query into prefix query.
        int MAX_RANK = rankMap.size();
        for(Integer num : rankMap.keySet()) {
            rankMap.put(num, MAX_RANK - rankMap.get(num) + 1);
        }
        return rankMap;
    }
}