import java.util.PriorityQueue;

class Solution {

    public int[] kthSmallestPrimeFraction(int[] nums, int k) {

        int n = nums.length;

        int[] kthPair = new int[2];

        // Min heap ordered by fraction value:
        // nums[u] / nums[v]
        //
        // IMPORTANT:
        // We do NOT use floating point comparison.
        //
        // Instead compare:
        // a/b < c/d
        // ⇔ a*d < c*b
        //
        // So we compare:
        // nums[pair1.u] * nums[pair2.v]
        // and
        // nums[pair2.u] * nums[pair1.v]
        //
        // This avoids precision issues.
        PriorityQueue<Pair> minPairFractionHeap =
            new PriorityQueue<>((pair1, pair2) ->
                nums[pair1.u] * nums[pair2.v]
              - nums[pair2.u] * nums[pair1.v]
            );

        // Key observation:
        // nums is sorted.
        //
        // For fixed u:
        // Fractions are:
        // nums[u] / nums[n-1],
        // nums[u] / nums[n-2],
        // ...
        //
        // As v decreases,
        // fraction value increases.
        //
        // So for each u,
        // smallest fraction is (u, n-1).

        // Step 1:
        // Initialize heap with (u, n-1)
        // for all valid numerators.
        //
        // u goes from 0 to n-2
        // because denominator must be > numerator index.
        for (int u = 0; u < n - 1; u++) {
            minPairFractionHeap.add(new Pair(u, n - 1));
        }

        // Step 2:
        // Extract smallest fraction k-1 times.
        //
        // After removing k-1 smallest,
        // the next one at top is kth smallest.
        while (k > 1) {

            Pair pair = minPairFractionHeap.poll();

            // Move to next larger fraction
            // in same row.
            //
            // Since decreasing denominator
            // increases fraction,
            // we move v one step left.
            pair.v--;

            // Only valid if numerator index < denominator index.
            if (pair.v > pair.u) {
                minPairFractionHeap.add(pair);
            }

            k--;
        }

        // The top of heap is kth smallest fraction.
        Pair resultPair = minPairFractionHeap.peek();

        kthPair[0] = nums[resultPair.u];
        kthPair[1] = nums[resultPair.v];

        return kthPair;
    }
}

class Pair {

    int u; // numerator index
    int v; // denominator index

    Pair(int u, int v) {
        this.u = u;
        this.v = v;
    }
}
