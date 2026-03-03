import java.util.PriorityQueue;

class Solution {

    public long totalCost(int[] costs, int k, int candidates) {

        int n = costs.length;

        long totalCost = 0;

        // Two pointers:
        // leftPtr → start of array
        // rightPtr → end of array
        int leftPtr = 0;
        int rightPtr = n - 1;

        // Min heap for left candidates
        PriorityQueue<Integer> leftMinHeap =
            new PriorityQueue<>();

        // Min heap for right candidates
        PriorityQueue<Integer> rightMinHeap =
            new PriorityQueue<>();

        // We need to hire k workers.
        while (k > 0) {

            // Step 1:
            // Fill left heap up to "candidates" size.
            //
            // Why?
            // Because at any moment,
            // we can only consider up to "candidates"
            // workers from the left side.
            while (leftPtr <= rightPtr
                    && leftMinHeap.size() < candidates) {

                leftMinHeap.offer(costs[leftPtr++]);
            }

            // Step 2:
            // Fill right heap similarly.
            //
            // Important:
            // leftPtr <= rightPtr ensures
            // we don't double count elements.
            while (leftPtr <= rightPtr
                    && rightMinHeap.size() < candidates) {

                rightMinHeap.offer(costs[rightPtr--]);
            }

            // Step 3:
            // Compare smallest from both heaps.
            //
            // If one heap is empty,
            // treat its value as infinity.
            int cost1 =
                (!leftMinHeap.isEmpty())
                ? leftMinHeap.peek()
                : Integer.MAX_VALUE;

            int cost2 =
                (!rightMinHeap.isEmpty())
                ? rightMinHeap.peek()
                : Integer.MAX_VALUE;

            // Step 4:
            // Pick the smaller cost.
            //
            // If equal, left side is preferred
            // because condition is <=.
            if (cost1 <= cost2) {

                totalCost += cost1;
                leftMinHeap.poll();
            }
            else {

                totalCost += cost2;
                rightMinHeap.poll();
            }

            k--;
        }

        return totalCost;
    }
}
