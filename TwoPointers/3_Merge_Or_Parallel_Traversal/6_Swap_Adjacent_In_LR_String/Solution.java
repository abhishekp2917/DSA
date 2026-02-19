class Solution {

    public boolean canTransform(String s, String t) {

        // We need to check if we can transform string s into t
        // using allowed moves:
        //
        // "XL" → "LX"  (L can move LEFT)
        // "RX" → "XR"  (R can move RIGHT)
        //
        // Important:
        // L can only move left.
        // R can only move right.
        // X is just an empty placeholder.

        int i = 0, j = 0;

        // Traverse both strings
        while (i < s.length() && j < t.length()) {

            // Skip all 'X' characters in s
            while (i < s.length() && s.charAt(i) == 'X') i++;

            // Skip all 'X' characters in t
            while (j < t.length() && t.charAt(j) == 'X') j++;

            // If both pointers are still valid
            if (i < s.length() && j < t.length()) {

                // Characters must match (either L or R)
                if (s.charAt(i) != t.charAt(j)) return false;

                // If character is 'R':
                // R can only move right.
                // So its position in t (j) must be >= position in s (i).
                if (t.charAt(j) == 'R' && j < i) return false;

                // If character is 'L':
                // L can only move left.
                // So its position in t (j) must be <= position in s (i).
                if (t.charAt(j) == 'L' && j > i) return false;

                // Move both pointers forward
                i++;
                j++;
            }
        }

        // After main loop,
        // remaining characters (if any) must all be 'X'
        while (i < s.length() && s.charAt(i) == 'X') i++;
        while (j < t.length() && t.charAt(j) == 'X') j++;

        // Both strings must be fully consumed
        return i == s.length() && j == t.length();
    }
}
