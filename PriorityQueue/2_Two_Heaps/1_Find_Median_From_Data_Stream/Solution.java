import java.util.PriorityQueue;

class MedianFinder {

    // maxHeap stores the SMALLER half of numbers.
    // It is a MAX heap → largest element of smaller half is on top.
    //
    // Why MAX heap?
    // Because we want quick access to the largest element
    // from the smaller half (candidate median).
    PriorityQueue<Integer> maxHeap;

    // minHeap stores the LARGER half of numbers.
    // It is a MIN heap → smallest element of larger half is on top.
    //
    // Why MIN heap?
    // Because we want quick access to the smallest element
    // from the larger half (candidate median).
    PriorityQueue<Integer> minHeap;

    public MedianFinder() {

        // MAX heap → reverse comparator
        maxHeap = new PriorityQueue<>((a, b) -> b - a);

        // MIN heap → default behavior
        minHeap = new PriorityQueue<>();
    }
    
    public void addNum(int num) {

        // -------- Core Idea --------
        //
        // We maintain TWO invariants:
        //
        // 1) Ordering Invariant:
        //      Every element in maxHeap <= every element in minHeap
        //
        // 2) Size Invariant:
        //      maxHeap.size() >= minHeap.size()
        //      And difference is at most 1.
        //
        // Why these invariants?
        // Because:
        // - If total count is odd → median is top of maxHeap.
        // - If even → median is average of both heap tops.

        // Step 1:
        // Always insert into maxHeap first.
        //
        // Why?
        // To keep logic consistent.
        // We temporarily treat the new number as part of smaller half.
        maxHeap.add(num);
        
        // Step 2:
        // Move the largest element from maxHeap
        // into minHeap.
        //
        // Why?
        // This ensures ordering invariant:
        // All elements in maxHeap <= all elements in minHeap.
        //
        // Because:
        // maxHeap's top is the largest in smaller half.
        // If it belongs to larger half, we move it.
        minHeap.add(maxHeap.poll());
        
        // Step 3:
        // Fix size imbalance.
        //
        // If minHeap becomes larger,
        // move one element back to maxHeap.
        //
        // Why?
        // We enforce size invariant:
        // maxHeap should have equal OR one extra element.
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
    }
    
    public double findMedian() {

        // If total count is odd:
        // maxHeap will have one extra element.
        //
        // That extra element is the median.
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }

        // If total count is even:
        // Median = average of:
        //   largest of smaller half (maxHeap.peek())
        //   smallest of larger half (minHeap.peek())
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
