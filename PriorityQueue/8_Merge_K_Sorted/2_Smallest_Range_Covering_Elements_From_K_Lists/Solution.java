import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
        int k = nums.size();
        int maxValue = Integer.MIN_VALUE;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(
                nums.get(a[0]).get(a[1]),
                nums.get(b[0]).get(b[1])
            )
        );
        for (int i=0; i<k; i++) {
            minHeap.offer(new int[]{i, 0});
            maxValue = Math.max(maxValue, nums.get(i).get(0));
        }
        int start = 0, end = 0;
        int bestRange = Integer.MAX_VALUE;
        while (minHeap.size() == k) {
            int[] curr = minHeap.poll();
            int row = curr[0];
            int col = curr[1];
            int minValue = nums.get(row).get(col);
            if ((maxValue-minValue) < bestRange) {
                bestRange = maxValue-minValue;
                start = minValue;
                end = maxValue;
            }
            if (col + 1 < nums.get(row).size()) {
                int nextValue = nums.get(row).get(col + 1);
                minHeap.offer(new int[]{row, col + 1});
                maxValue = Math.max(maxValue, nextValue);
            }
        }
        return new int[]{start, end};
    }
}
