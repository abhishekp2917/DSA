import java.util.PriorityQueue;

public class Solution {

    public ListNode mergeKLists(ListNode[] arr) {

        int n = arr.length;

        // Edge case:
        // If there are no lists, return null.
        if (n == 0) return null;

        // We use a MIN HEAP.
        //
        // Why MIN heap?
        // Because at every step,
        // we want the smallest current node among k lists.
        //
        // Comparator compares node values.
        PriorityQueue<ListNode> minHeap =
            new PriorityQueue<>(
                (node1, node2) -> Integer.compare(node1.val, node2.val)
            );

        // Dummy head to simplify linking logic.
        //
        // Why dummy?
        // Avoids special handling for first node.
        ListNode mergedHead = new ListNode(-1);
        ListNode curr = mergedHead;

        // Step 1:
        // Insert head of each list into heap.
        //
        // Why only head?
        // Because each list is already sorted.
        // The head is the smallest element of that list.
        //
        // These heads are candidates for global smallest.
        for (int i = 0; i < n; i++) {
            if (arr[i] != null) {
                minHeap.add(arr[i]);
            }
        }

        // Step 2:
        // Keep extracting smallest node
        // and attach it to merged list.
        while (!minHeap.isEmpty()) {

            // Get smallest node among current candidates
            ListNode tempNode = minHeap.poll();

            // Store next pointer before modifying
            ListNode next = tempNode.next;

            // Attach this node to result
            curr.next = tempNode;
            curr = curr.next;

            // Important:
            // Disconnect from original list.
            //
            // Why?
            // Prevent accidental linking
            // that could cause cycle or incorrect structure.
            curr.next = null;

            // If extracted node had a next,
            // push that next node into heap.
            //
            // Why?
            // Because in that list,
            // the next element is now the smallest candidate.
            if (next != null) {
                minHeap.add(next);
            }
        }

        // mergedHead.next skips dummy node
        return mergedHead.next;
    }
}

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}