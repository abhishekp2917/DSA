import java.util.ArrayList;
import java.util.List;

class Solution {

    // Pattern: Backtracking / DFS (Partitioning String by Cut Positions)
    //
    // Core intuition:
    // We want to split the string into substrings such that
    // every substring is a palindrome.
    //
    // The problem is about deciding where to CUT the string,
    // not about choosing characters or rearranging them.

    public List<List<String>> partition(String s) {

        List<List<String>> partitions = new ArrayList<>();

        // Start recursion from index 0
        recurrsion(s, 0, partitions, new ArrayList<>());

        return partitions;
    }

    private void recurrsion(
        String s,
        int start,
        List<List<String>> partitions,
        List<String> partition
    ) {

        // Base case:
        // If we have consumed the entire string,
        // the current partition is a valid answer.
        if (start == s.length()) {
            partitions.add(new ArrayList<>(partition));
            return;
        }

        // Try all possible substring lengths starting at `start`
        for (int len = 1; start + len <= s.length(); len++) {

            int end = start + len - 1;

            // Only proceed if the substring s[start..end] is a palindrome
            if (isPalindrome(s, start, end)) {

                // Choose the palindrome substring
                partition.add(s.substring(start, end + 1));

                // Recurse on the remaining suffix
                recurrsion(s, end + 1, partitions, partition);

                // Backtrack: remove the last chosen substring
                partition.remove(partition.size() - 1);
            }
        }
    }

    // Checks whether s[start..end] is a palindrome
    private boolean isPalindrome(String s, int start, int end) {

        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }
}
