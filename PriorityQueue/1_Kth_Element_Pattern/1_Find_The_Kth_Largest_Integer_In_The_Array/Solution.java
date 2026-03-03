import java.util.PriorityQueue;

class Solution {
    public String kthLargestNumber(String[] nums, int k) {

        // Important observation:
        // Numbers are given as STRINGS because they can be very large
        // (larger than what long or BigInteger may safely handle in constraints).
        //
        // Therefore:
        // We must compare them WITHOUT converting to integers.
        
        // We use a MIN HEAP of size k.
        //
        // Just like the integer version of kth largest,
        // we want to maintain only the TOP k largest numbers.
        //
        // The smallest among those top k will be the kth largest overall.
        //
        // BUT:
        // Since numbers are strings, we need a CUSTOM COMPARATOR.

        PriorityQueue<String> minHeap = new PriorityQueue<>((num1, num2) -> {

            // First comparison rule:
            // If lengths differ, the longer number is larger.
            //
            // Why?
            // Because numeric value depends first on digit count.
            //
            // Example:
            // "1234" (length 4) > "999" (length 3)
            //
            // So smaller length should come first in minHeap.
            if (num1.length() != num2.length()) {
                return num1.length() - num2.length();
            }

            // If lengths are same,
            // then lexicographical comparison works correctly.
            //
            // Why?
            // Because equal length numeric strings compare
            // lexicographically exactly the same as numerically.
            //
            // Example:
            // "345" > "123"
            //
            // compareTo does lexicographical comparison.
            return num1.compareTo(num2);
        });

        // Traverse all numbers
        for (String num : nums) {

            // Add current number as a candidate
            minHeap.add(num);

            // If heap size exceeds k,
            // remove the smallest number.
            //
            // Why?
            // Because we only want to keep the k largest numbers.
            //
            // The smallest among them cannot be part of top k.
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // At this point:
        // Heap contains exactly k largest numbers.
        //
        // Since it's a MIN HEAP:
        // Root = smallest among top k
        // = kth largest overall.
        return minHeap.poll();
    }
}
