import java.util.ArrayList;
import java.util.List;

class Solution {

    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {

        int n = firstList.length;
        int m = secondList.length;

        // List to store all valid intersections.
        List<int[]> intersections = new ArrayList<>();

        // Two pointers for both interval lists.
        int ptr1 = 0, ptr2 = 0;

        // Traverse both lists simultaneously.
        while (ptr1 < n && ptr2 < m) {

            // Current intervals
            int start1 = firstList[ptr1][0];
            int end1   = firstList[ptr1][1];

            int start2 = secondList[ptr2][0];
            int end2   = secondList[ptr2][1];

            // Intersection exists if:
            // max(start1, start2) <= min(end1, end2)
            //
            // Because overlap happens only when
            // the later start is before or equal to earlier end.
            if (Math.max(start1, start2) <= Math.min(end1, end2)) {

                // Add the overlapping interval.
                intersections.add(new int[] {
                        Math.max(start1, start2),
                        Math.min(end1, end2)
                });
            }

            // Move the pointer of the interval
            // that finishes earlier.
            //
            // Reason:
            // The interval with smaller end cannot
            // overlap with any future interval
            // from the other list.
            if (end1 < end2) {
                ptr1++;
            } else {
                ptr2++;
            }
        }

        // Convert List<int[]> to int[][]
        int[][] ans = new int[intersections.size()][2];
        for (int i = 0; i < intersections.size(); i++) {
            ans[i] = intersections.get(i);
        }

        return ans;
    }
}
