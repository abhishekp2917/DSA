import java.util.Deque;
import java.util.LinkedList;

class Solution {

    public int findMaxValueOfEquation(int[][] points, int k) {

        int n = points.length;

        /*
         We want to maximize:

             yi + yj + |xi - xj|

         Since points are sorted by x
         and j > i,
             |xi - xj| = xj - xi

         So equation becomes:

             yi + yj + (xj - xi)

         Rearranging:

             = (yi - xi) + (yj + xj)

         For each j:
             maximize (yi - xi)
             where xj - xi <= k
        */

        int maxValue = Integer.MIN_VALUE;

        /*
         Deque will store indices i.

         It maintains decreasing order
         of (yi - xi).

         Front = best candidate i
         giving maximum (yi - xi)
        */
        Deque<Integer> deque = new LinkedList<>();

        for (int end = 0; end < n; end++) {

            int xj = points[end][0];
            int yj = points[end][1];

            /*
             STEP 1:
             Remove indices that violate constraint:

                 xj - xi > k

             Because they are too far away.
            */
            while (!deque.isEmpty()) {

                int xi = points[deque.peekFirst()][0];

                if (xj - xi > k)
                    deque.pollFirst();
                else
                    break;
            }

            /*
             STEP 2:
             If deque not empty,
             compute candidate answer.

             max = (yi - xi) + (yj + xj)

             Since front stores best (yi - xi),
             we just use it.
            */
            if (!deque.isEmpty()) {

                int i = deque.peekFirst();

                int xi = points[i][0];
                int yi = points[i][1];

                maxValue = Math.max(
                    maxValue,
                    (yi + yj) + (xj - xi)
                );
            }

            /*
             STEP 3:
             Maintain decreasing order of (yi - xi).

             Remove all elements from back
             whose (yi - xi) <= current (yj - xj)

             Because they will never be useful.
            */
            while (!deque.isEmpty()) {

                int l = deque.peekLast();

                int xl = points[l][0];
                int yl = points[l][1];

                /*
                 Compare:

                     (yl - xl) vs (yj - xj)

                 Instead of computing directly,
                 your code computes:

                     (xj - xl) + (yl - yj)

                 which simplifies to:
                     (yl - xl) - (yj - xj)
                */

                int xExtraValueByLastPoint = xj - xl;
                int yExtraValueByLastPoint = yl - yj;
                int totalExtraValue =
                        xExtraValueByLastPoint +
                        yExtraValueByLastPoint;

                /*
                 If totalExtraValue < 0,
                 it means:

                     (yl - xl) < (yj - xj)

                 So last point is worse candidate.
                */
                if (totalExtraValue < 0)
                    deque.pollLast();
                else
                    break;
            }

            /*
             Add current index as candidate.
            */
            deque.add(end);
        }

        return maxValue;
    }
}
