import java.util.PriorityQueue;

class Solution {

    public int[][] kClosest(int[][] points, int k) {

        int n = points.length;

        // Result array of size k
        int[][] result = new int[k][2];

        // We use a MAX HEAP of size k.
        //
        // Why MAX heap?
        // Because we want to keep the k closest points.
        //
        // So whenever heap size exceeds k,
        // we remove the FARTHEST point.
        //
        // That way heap always contains the k closest points seen so far.
        //
        // Instead of storing actual points,
        // we store indices.
        //
        // Why store indices?
        // - Avoid copying arrays
        // - Comparator can access original points array
        // - Cleaner memory usage

        PriorityQueue<Integer> maxHeap =
            new PriorityQueue<>((a, b) ->
                distanceFromOrigin(points[b]) - distanceFromOrigin(points[a])
            );

        for (int i = 0; i < n; i++) {

            // Add current index
            maxHeap.add(i);

            // If heap size exceeds k,
            // remove the farthest point.
            //
            // Why?
            // Because we only want k closest.
            //
            // Since this is a MAX heap,
            // poll() removes the largest distance point.
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // At this point:
        // Heap contains exactly k closest points.
        //
        // Order inside heap is not sorted.
        // But that is fine because problem allows any order.

        // Extract elements.
        //
        // We fill result from end toward start.
        //
        // Why?
        // Because heap pops the largest among the k closest.
        // But order does not matter.
        //
        // Still filling backwards avoids confusion if ordering required.
        while (!maxHeap.isEmpty()) {
            result[--k] = points[maxHeap.poll()];
        }

        return result;
    }

    // Compute squared distance.
    //
    // Why squared distance?
    // Because:
    // sqrt(x^2 + y^2) is monotonic.
    //
    // We do NOT need actual distance.
    // Comparing squared values is sufficient.
    //
    // Avoids floating point + sqrt cost.
    public int distanceFromOrigin(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }
}
