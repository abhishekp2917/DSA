class Solution {

    public boolean isSubsequence(String s, String t) {

        int n = s.length();
        int m = t.length();

        // i → pointer for string s (the subsequence we want to match)
        // j → pointer for string t (the larger string)
        int i = 0, j = 0;

        // Traverse both strings
        while (i < n && j < m) {

            // If characters don't match,
            // move forward in t to find a match.
            if (t.charAt(j) != s.charAt(i)) {
                j++;
            }
            else {
                // If characters match,
                // move both pointers forward.
                // We successfully matched one character of s.
                i++;
                j++;
            }
        }

        // If we were able to match all characters of s,
        // then s is a subsequence of t.
        return i == n;
    }
}
