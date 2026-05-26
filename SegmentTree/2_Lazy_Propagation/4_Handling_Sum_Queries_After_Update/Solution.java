import java.util.ArrayList;
import java.util.List;

class Solution {

    public long[] handleQuery(int[] nums1, int[] nums2, int[][] queries) {

        int n = nums1.length;

        List<Long> ans = new ArrayList<>();

        // Segment Tree stores:
        // count of 1s inside ranges of nums1.
        //
        // WHY only count of 1s?
        //
        // Query type 2 needs:
        // sum += p * countOfOnes
        //
        // So exact array values are unnecessary.
        Node root = buildSegmentTree(0, n-1, nums1);

        // Maintain running sum of nums2.
        //
        // Query type 2 modifies this sum directly.
        long sum = 0;

        for(int num : nums2) sum += num;

        for(int[] query : queries) {

            switch(query[0]) {

                // Type 1:
                // flip nums1[left...right]
                case 1: {

                    int left = query[1];

                    int right = query[2];

                    // Flip bits inside interval.
                    flip(0, n-1, left, right, root);

                    break;
                }

                // Type 2:
                // nums2[i] += p * nums1[i]
                //
                // Since nums1 contains only 0/1,
                // only positions with value 1 contribute.
                case 2: {

                    int p = query[1];

                    // root.onesCount:
                    // total number of 1s in nums1.
                    sum += (long)p * root.onesCount;

                    break;
                }

                // Type 3:
                // report current nums2 sum.
                case 3: {

                    ans.add(sum);

                    break;
                }

                default : break; 
            }
        }

        return ans.stream().mapToLong(Long::longValue).toArray();
    }

    private void flip(int low, int high, int left, int right, Node node) {

        // No overlap:
        // current segment unaffected.
        if(high<left || right<low) return;

        // Complete overlap:
        // every bit inside segment flips.
        //
        // New ones count becomes:
        //
        // segmentLength - oldOnesCount
        //
        // Example:
        // length = 5
        // ones = 2
        //
        // after flip:
        // ones = 3
        if(left<=low && high<=right) {

            // Toggle lazy flip state.
            //
            // Multiple flips cancel each other:
            //
            // false -> true
            // true -> false
            node.pendingFlip = !node.pendingFlip;

            node.onesCount =
                (high-low+1) - node.onesCount;

            return;
        }

        int mid = low + (high-low)/2;

        // Push pending lazy flips before recursion.
        //
        // WHY?
        // Child counts must become correct
        // before partial updates happen.
        push(node, low, mid, high);

        // Recurse into left half:
        // [low...mid]
        flip(low, mid, left, right, node.left);

        // Recurse into right half:
        // [mid+1...high]
        flip(mid+1, high, left, right, node.right);

        // Merge child results.
        //
        // Current segment ones count =
        // left ones + right ones.
        node.onesCount =
            node.left.onesCount + node.right.onesCount;
    }

    private void push(Node node, int low, int mid, int high) {

        // No pending lazy flip.
        if(node.pendingFlip) {

            // Propagate flip into left child.
            node.left.pendingFlip =
                !node.left.pendingFlip;

            // Propagate flip into right child.
            node.right.pendingFlip =
                !node.right.pendingFlip;

            // Update left child ones count after flip.
            node.left.onesCount =
                (mid-low+1) - node.left.onesCount;

            // Update right child ones count after flip.
            node.right.onesCount =
                (high-mid) - node.right.onesCount;

            // Current lazy flip fully propagated.
            node.pendingFlip = false;
        }
    }

    private Node buildSegmentTree(int low, int high, int[] nums1) {

        Node node = new Node();

        // Leaf node:
        // stores single bit value.
        if(low==high) {

            node.onesCount = nums1[low];

            return node;
        }
        
        int mid = low + (high-low)/2;

        // Build left half:
        // [low...mid]
        node.left = buildSegmentTree(low, mid, nums1);

        // Build right half:
        // [mid+1...high]
        node.right = buildSegmentTree(mid+1, high, nums1);

        // Merge child counts.
        node.onesCount =
            node.left.onesCount + node.right.onesCount;

        return node;
    }
}

class Node {

    // Number of 1s inside this segment.
    int onesCount;

    // Lazy propagation flip marker.
    //
    // true means:
    // descendants inside this segment
    // should eventually be flipped.
    boolean pendingFlip;

    // Child nodes.
    Node left, right;
}