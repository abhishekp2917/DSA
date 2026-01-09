import java.util.ArrayList;
import java.util.List;

class Solution {

    // Pattern: Backtracking / DFS (Partitioning with Pruning)
    //
    // Core intuition:
    // We want to split the string into a sequence of integers such that
    // each number (from the third onward) is the sum of the previous two.
    //
    // The digits order is fixed; the only decision is where to cut the string
    // to form the next number in the Fibonacci sequence.

    public List<Integer> splitIntoFibonacci(String str) {

        List<Integer> fibSeq = new ArrayList<>();

        // Start recursion with no previous numbers
        recursion(str, 0, 0, fibSeq);

        return fibSeq;
    }

    private boolean recursion(
        String str,
        long secLast,
        long last,
        List<Integer> fibSeq
    ) {

        // Base case:
        // If the entire string has been consumed, the sequence is valid
        // only if it contains at least 3 numbers.
        if (str == "") {
            if (fibSeq.size() >= 3) return true;
            return false;
        }

        // Try all possible prefixes of the remaining string
        for (int len = 1; len <= str.length(); len++) {

            long num = Long.parseLong(str.substring(0, len));

            // Leading zero check:
            // Numbers like "01" are invalid, except for "0" itself.
            if (len == 2 && str.charAt(0) == '0') return false;

            // Pruning:
            // Stop if the number exceeds integer limit or Fibonacci constraint
            if (num > Integer.MAX_VALUE ||
                (fibSeq.size() >= 2 && num > last + secLast)) break;

            // If we already have two numbers, enforce Fibonacci rule
            if (fibSeq.size() >= 2 && num != last + secLast) continue;

            // Choose the current number
            fibSeq.add((int) num);

            // Recurse on the remaining string
            if (recursion(
                    str.substring(len, str.length()),
                    last,
                    num,
                    fibSeq
            )) return true;

            // Backtrack: remove the last added number
            fibSeq.remove(fibSeq.size() - 1);
        }

        // No valid Fibonacci split found
        return false;
    }
}
