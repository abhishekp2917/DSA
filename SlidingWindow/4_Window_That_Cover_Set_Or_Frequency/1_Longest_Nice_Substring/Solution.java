class Solution {
    public String longestNiceSubstring(String s) {

        // A substring is "nice" if:
        // For every character present,
        // both its lowercase and uppercase versions exist.
        //
        // Example:
        // 'a' must have 'A'
        // 'B' must have 'b'

        int n = s.length();

        String longestSubstring = "";

        // Try every possible starting index
        for (int start = 0; start < n; start++) {

            // Bitmask to track lowercase letters present
            int lowerCaseMask = 0;

            // Bitmask to track uppercase letters present
            int upperCaseMask = 0;

            // Extend substring from this start
            for (int end = start; end < n; end++) {

                char ch = s.charAt(end);

                // If lowercase letter,
                // set corresponding bit in lowerCaseMask
                if (ch >= 'a' && ch <= 'z') {

                    lowerCaseMask |= (1 << (ch - 'a'));

                }
                // If uppercase letter,
                // set corresponding bit in upperCaseMask
                else if (ch >= 'A' && ch <= 'Z') {

                    upperCaseMask |= (1 << (ch - 'A'));
                }

                // If both masks are equal,
                // it means:
                // Every lowercase letter present
                // also has its uppercase version present
                // and vice versa.
                if (lowerCaseMask == upperCaseMask) {

                    int currLen = end - start + 1;

                    // Update longest substring if needed
                    if (currLen > longestSubstring.length()) {

                        longestSubstring = s.substring(start, end + 1);
                    }
                }
            }
        }

        return longestSubstring;
    }
}
