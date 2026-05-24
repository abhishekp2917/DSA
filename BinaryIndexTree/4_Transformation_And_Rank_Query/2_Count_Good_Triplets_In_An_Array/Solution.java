class Solution {

    public long goodTriplets(int[] nums1, int[] nums2) {

        int n = nums1.length;
        long tripletsCount = 0;

        // Convert nums1 into ranks based on nums2 ordering.
        // WHY?
        // We need triplets having SAME relative ordering in both arrays.
        //
        // Example:
        //
        // nums1 = [2,0,1,3]
        // nums2 = [0,1,2,3]
        //
        // indexMap in nums2:
        // 0 -> 0
        // 1 -> 1
        // 2 -> 2
        // 3 -> 3
        //
        // ranks for nums1:
        // [3,1,2,4]
        //
        // Now problem becomes:
        // count increasing subsequences of length 3.
        int[] ranks = getRanks(nums1, nums2);

        // Fenwick Tree stores frequency of ranks already processed.
        int[] fenwickTree = new int[n + 1];

        // Treat every element as middle element of triplet.
        // We need: left smaller ranks and right larger ranks
        for(int i=0; i<n-1; i++) {

            int rank = ranks[i];

            // Total ranks larger than current rank.
            //
            // Example:
            // n = 5
            // rank = 2
            //
            // higher ranks:
            // 3,4,5
            //
            // count = 3
            int totalHigherRanks = n - rank;

            // query(rank - 1):
            // gives count of smaller ranks already processed.
            // These become valid LEFT elements.
            int lowerRanksOnLeft = query(fenwickTree, rank - 1);

            // Elements already processed = i
            //
            // Among them:
            // lowerRanksOnLeft are smaller
            //
            // Remaining must be greater.
            int higherRankOnLeft = i - lowerRanksOnLeft;

            // totalHigherRanks:
            // total ranks larger than current rank in whole array
            //
            // higherRankOnLeft:
            // larger ranks already used on left side
            //
            // Remaining larger ranks must exist on RIGHT side.
            int higherRanksOnRight = totalHigherRanks - higherRankOnLeft;

            // Valid triplets:
            //
            // smaller on left × larger on right
            tripletsCount += ((long) lowerRanksOnLeft * higherRanksOnRight);

            // Insert current rank into BIT
            // so future elements can use it as LEFT element.
            update(fenwickTree, rank, 1);
        }

        return tripletsCount;
    }

    private void update(int[] fenwickTree, int rank, int val) {

        // Standard BIT update:
        // update all parent binary ranges containing this rank.
        while(rank < fenwickTree.length) {

            fenwickTree[rank] += val;

            // move to next responsible parent range
            rank += (rank & -rank);
        }
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

    private int[] getRanks(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] ranks = new int[n];

        // indexMap[value] = index of value in nums2
        //
        // Since nums contain permutation [0...n-1],
        // array works as direct hashmap.
        int[] indexMap = new int[n];
        for(int i=0; i<n; i++) {
            indexMap[nums2[i]] = i;
        }

        // Convert nums1 values into nums2 positions.
        // +1 because BIT uses 1-indexing.
        for(int i=0; i<n; i++) {
            ranks[i] = indexMap[nums1[i]] + 1;
        }
        return ranks;
    }
}