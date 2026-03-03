import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {

        List<List<Integer>> pairs = new ArrayList<>();

        // Min heap ordered by pair sum:
        // nums1[u] + nums2[v]
        //
        // Why min heap?
        // Because we want to always extract
        // the smallest available pair sum.
        PriorityQueue<Pair> minHeap =
            new PriorityQueue<>(
                (pair1, pair2) ->
                    (nums1[pair1.u] + nums2[pair1.v])
                  - (nums1[pair2.u] + nums2[pair2.v])
            );

        // Key observation:
        // Both nums1 and nums2 are sorted.
        //
        // If we fix u and increase v,
        // sums increase.
        //
        // So for each index u in nums1,
        // the smallest possible pair is (u, 0).

        // Step 1:
        // Insert (u, 0) for all u.
        //
        // Each represents the smallest pair
        // starting from nums1[u].
        for (int u = 0; u < nums1.length; u++) {
            minHeap.add(new Pair(u, 0));
        }

        // Step 2:
        // Extract smallest pair k times.
        while (k > 0) {

            Pair pair = minHeap.poll();

            // Add actual values to result.
            pairs.add(Arrays.asList(
                nums1[pair.u],
                nums2[pair.v]
            ));

            // Move to next element in nums2
            // while keeping nums1 index fixed.
            //
            // Why?
            // Because next candidate for this u
            // is (u, v+1).
            pair.v++;

            // Only push if within bounds.
            if (pair.v < nums2.length) {
                minHeap.add(pair);
            }

            k--;
        }

        return pairs;
    }
}

class Pair {

    int u; // index in nums1
    int v; // index in nums2

    Pair(int u, int v) {
        this.u = u;
        this.v = v;
    }
}
