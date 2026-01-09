import java.util.HashSet;
import java.util.Set;

class Solution {

    // Pattern: Backtracking / DFS (Partitioning with Uniqueness Constraint)
    //
    // Core intuition:
    // We want to split the string into substrings such that:
    // 1) All substrings are unique
    // 2) The total number of substrings is maximized
    //
    // The order of characters is fixed.
    // The decision is where to cut the string.

    public int maxUniqueSplit(String s) {

        // A set is used to track substrings already used
        return recurrsion(s, 0, new HashSet<>());
    }

    private int recurrsion(
        String s,
        int start,
        Set<String> set
    ) {

        // Base case:
        // If we have consumed the entire string,
        // the number of unique substrings formed so far is the result.
        if (start == s.length()) {
            return set.size();
        }

        int maxLen = 0;

        // Try all possible substrings starting at index `start`
        for (int end = start + 1; end <= s.length(); end++) {

            String substr = s.substring(start, end);

            // Only proceed if this substring has not been used before
            if (!set.contains(substr)) {

                // Choose the substring
                set.add(substr);

                // Recurse on the remaining suffix
                maxLen = Math.max(
                    maxLen,
                    recurrsion(s, end, set)
                );

                // Backtrack: remove the chosen substring
                set.remove(substr);
            }
        }

        return maxLen;
    }
}
