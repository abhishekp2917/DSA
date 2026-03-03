import java.util.PriorityQueue;

class Solution {

    public int kthSmallest(int[][] matrix, int k) {

        int n = matrix.length;
        int m = matrix[0].length;

        int ans = -1;

        // This problem states:
        // Each row is sorted in ascending order.
        //
        // Important observation:
        // This is similar to merging k sorted lists.
        //
        // Each row behaves like a sorted list.
        // We need the kth smallest element overall.

        // We use a MIN HEAP.
        //
        // Heap will store positions (row, col).
        // Comparator compares actual matrix values.
        PriorityQueue<int[]> minHeap =
            new PriorityQueue<>((pos1, pos2) ->
                matrix[pos1[0]][pos1[1]] - matrix[pos2[0]][pos2[1]]
            );

        // Step 1:
        // Insert first element of each row into heap.
        //
        // Why only first column?
        //
        // Because rows are sorted.
        // The smallest element in each row is at column 0.
        //
        // These are the only possible candidates
        // for global smallest initially.
        //
        // Why iterate only min(n, k)?
        //
        // Because we never need more than k rows.
        // If k < n, we don't need to consider all rows.
        for (int i = 0; i < Math.min(n, k); i++) {
            minHeap.add(new int[]{ i, 0 });
        }

        // Step 2:
        // Extract smallest element k times.
        //
        // Each poll gives next smallest element globally.
        while (k > 0) {

            // Peek to access smallest position
            int row = minHeap.peek()[0];
            int col = minHeap.peek()[1];

            // Remove it from heap
            minHeap.poll();

            // Update answer
            ans = matrix[row][col];

            // Important step:
            // If there is a next element in same row,
            // push it into heap.
            //
            // Why?
            //
            // Because:
            // We just removed matrix[row][col].
            // The next smallest candidate from this row
            // must be matrix[row][col+1].
            //
            // This is exactly like merging sorted lists.
            if (col + 1 < m) {
                minHeap.add(new int[]{ row, col + 1 });
            }

            k--;
        }

        return ans;
    }
}
