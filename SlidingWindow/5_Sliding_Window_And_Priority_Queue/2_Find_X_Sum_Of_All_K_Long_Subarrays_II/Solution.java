import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class Solution {

    public long[] findXSum(int[] nums, int k, int x) {

        int n = nums.length;

        /*
         freqMap:
         Stores mapping:
             number -> {number, frequency}
         
         We store Integer[] pair so that:
             pair[0] = number
             pair[1] = frequency in current window

         Why store pair object?
         Because TreeSet stores references.
         If we update frequency, we must remove
         old pair and reinsert updated pair.
        */
        Map<Integer, Integer[]> freqMap = new HashMap<>();


        /*
         minHeap:
         Stores the TOP X frequent elements.

         Comparator:
             1) Sort by frequency ascending
             2) If frequency same → sort by number ascending

         Why ascending?
         Because we want smallest frequency among top X
         at the front (so we can remove it easily).

         This heap maintains the chosen X best elements.
        */
        TreeSet<Integer[]> minHeap = new TreeSet<>((a, b) -> {
            int cmp = Integer.compare(a[1], b[1]);   
            if (cmp == 0) {
                return Integer.compare(a[0], b[0]); 
            }
            return cmp;
        });


        /*
         maxHeap:
         Stores all remaining elements (not in top X).

         Comparator:
             1) Sort by frequency descending
             2) If tie → number descending

         Why descending?
         Because when we need a better candidate
         to move into top X,
         we want the largest frequency available.
        */
        TreeSet<Integer[]> maxHeap = new TreeSet<>((a, b) -> {
            int cmp = Integer.compare(b[1], a[1]);   
            if (cmp == 0) {
                return Integer.compare(b[0], a[0]); 
            }
            return cmp;
        });

        long[] xSum = new long[n - k + 1];

        /*
         sum[0] maintains:
             sum of (number * frequency)
             for elements currently inside minHeap (top X)

         Why array of size 1?
         So it behaves like mutable reference.
        */
        long[] sum = new long[1];

        int start = 0;
        int idx = 0;

        // Expand sliding window
        for (int end = 0; end < n; end++) {

            int num = nums[end];

            /*
             STEP 1:
             Add current number to frequency structure.
             updateFreq handles:
                - remove old pair
                - update frequency
                - reinsert correctly
            */
            updateFreq(minHeap, maxHeap, freqMap, sum, num, 1);

            /*
             If window size exceeds k,
             remove outgoing element.
            */
            while (end - start + 1 > k) {

                int startNum = nums[start];

                updateFreq(minHeap, maxHeap, freqMap, sum, startNum, -1);

                /*
                 After removal,
                 we may have fewer than X elements
                 in minHeap.

                 So we promote best candidate
                 from maxHeap to minHeap.
                */
                if (!maxHeap.isEmpty()) {

                    Integer[] removedPair = maxHeap.pollFirst();

                    addPairToMinHeap(minHeap, removedPair, sum);
                }

                start++;
            }

            /*
             Ensure minHeap size does not exceed X.
             If it does, move smallest element
             from minHeap to maxHeap.
            */
            while (minHeap.size() > x) {

                Integer[] removedPair = minHeap.pollFirst();

                sum[0] -= removedPair[0] * (long) removedPair[1];

                maxHeap.add(removedPair);
            }

            /*
             If window size == k,
             record sum of top X elements.
            */
            if ((end - start + 1) == k)
                xSum[idx++] = sum[0];
        }

        return xSum;
    }


    /*
     updateFreq:
     Updates frequency of a number.

     freq = +1 for add
     freq = -1 for remove
    */
    private void updateFreq(TreeSet<Integer[]> minHeap,
                            TreeSet<Integer[]> maxHeap,
                            Map<Integer, Integer[]> freqMap,
                            long[] sum,
                            int num,
                            int freq) {

        Integer[] pair = freqMap.getOrDefault(num, new Integer[]{num, 0});

        /*
         Remove old version of pair
         before modifying frequency.
        */
        removePair(minHeap, maxHeap, pair, sum);

        pair[1] += freq;

        freqMap.put(num, pair);

        /*
         Reinsert updated pair.
         Initially insert into minHeap.
         Later balancing logic will fix position.
        */
        addPairToMinHeap(minHeap, pair, sum);
    }


    /*
     Remove pair from whichever heap it belongs to.
    */
    private void removePair(TreeSet<Integer[]> minHeap,
                            TreeSet<Integer[]> maxHeap,
                            Integer[] pair,
                            long[] sum) {

        if (minHeap.contains(pair)) {

            minHeap.remove(pair);

            // If it was contributing to sum,
            // subtract its contribution.
            sum[0] -= pair[0] * (long) pair[1];
        }
        else {
            maxHeap.remove(pair);
        }
    }


    /*
     Add pair into minHeap and update sum.
     Later balancing step may move it.
    */
    private void addPairToMinHeap(TreeSet<Integer[]> minHeap,
                                  Integer[] pair,
                                  long[] sum) {

        minHeap.add(pair);

        sum[0] += pair[0] * (long) pair[1];
    }
}
