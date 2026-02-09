import java.util.TreeMap;

class Solution {

    public double[] medianSlidingWindow(int[] nums, int k) {

        int n = nums.length;

        // Result array size = total windows possible
        double[] median = new double[n - k + 1];

        /*
         maxHeap  -> stores smaller half of elements
                     (we use lastKey() to simulate max-heap)
         
         minHeap  -> stores larger half of elements
                     (we use firstKey() to simulate min-heap)

         TreeMap is used instead of PriorityQueue because:
         - We need O(log n) removal of arbitrary element
         - Sliding window requires removing outgoing element
        */
        TreeMap<Integer, Integer> maxHeap = new TreeMap<>();
        TreeMap<Integer, Integer> minHeap = new TreeMap<>();

        /*
         maxHeap should contain ceil(k/2) elements
         minHeap should contain floor(k/2) elements

         This guarantees:
         - If k is odd → maxHeap holds 1 extra element (the median)
         - If k is even → both halves are balanced
        */
        int maxHeapSize = (int) Math.ceil(k / 2.0);
        int minHeapSize = (int) Math.floor(k / 2.0);

        int idx = 0;     // index to store medians
        int start = 0;   // sliding window left pointer

        // Expand window using end pointer
        for (int end = 0; end < n; end++) {

            int num = nums[end];

            /*
             STEP 1:
             Always insert into maxHeap first.
             
             Why?
             Because maxHeap stores smaller half,
             and we maintain size constraint manually.
            */
            addElement(maxHeap, num);
            maxHeapSize--;

            /*
             If maxHeap has exceeded required size,
             move the largest element from maxHeap
             into minHeap.

             Why largest?
             Because maxHeap stores smaller half.
             Its largest element may actually belong
             to upper half.
            */
            if (maxHeapSize < 0) {

                int largest = maxHeap.lastKey();

                removeElement(maxHeap, largest);
                addElement(minHeap, largest);

                // Fix size counters
                maxHeapSize++;
                minHeapSize--;
            }

            /*
             If both heap size requirements are satisfied,
             it means window size == k.
            */
            if (maxHeapSize == 0 && minHeapSize == 0) {

                // Window is fully formed
                int startNum = nums[start];

                // Compute median
                median[idx] = getMedian(maxHeap, minHeap, k);

                /*
                 STEP 2:
                 Remove outgoing element (sliding window left)
                */

                /*
                 Case 1:
                 If element exists in maxHeap
                */
                if (maxHeap.containsKey(startNum)) {

                    removeElement(maxHeap, startNum);
                    maxHeapSize++;

                    /*
                     After removal, maxHeap might be short.
                     So rebalance by moving smallest element
                     from minHeap to maxHeap.
                    */
                    if (minHeap.size() > 0) {

                        int smallest = minHeap.firstKey();

                        removeElement(minHeap, smallest);
                        addElement(maxHeap, smallest);

                        maxHeapSize--;
                        minHeapSize++;
                    }
                }

                /*
                 Case 2:
                 Element belongs to minHeap
                */
                else {

                    removeElement(minHeap, startNum);
                    minHeapSize++;
                }

                idx++;
                start++;   // shrink window
            }
        }

        return median;
    }

    /*
     Insert element into heap.
     We maintain frequency count because duplicates can exist.
    */
    private void addElement(TreeMap<Integer, Integer> heap, int element) {
        heap.put(element, heap.getOrDefault(element, 0) + 1);
    }

    /*
     Remove element safely.
     If frequency becomes zero, remove key.
    */
    private void removeElement(TreeMap<Integer, Integer> heap, int element) {
        heap.put(element, heap.getOrDefault(element, 0) - 1);
        if (heap.get(element) == 0) heap.remove(element);
    }

    /*
     Compute median:
     
     If k is even:
        median = average of maxHeap's largest
                 and minHeap's smallest

     If k is odd:
        median = maxHeap's largest
                 (because it holds extra element)
    */
    private double getMedian(TreeMap<Integer, Integer> maxHeap,
                             TreeMap<Integer, Integer> minHeap,
                             int k) {

        if (k % 2 == 0) {

            int m1 = maxHeap.lastKey();
            int m2 = minHeap.firstKey();

            return m1 / 2.0 + m2 / 2.0;
        }
        else {
            return maxHeap.lastKey();
        }
    }
}
