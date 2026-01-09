class Solution {

    // Pattern: Recursion + Divide-and-Transform (Self-Similar String)
    //
    // Idea:
    // The string is built recursively where the second half is a transformed
    // version of the first half (each character shifted by +1).

    public char kthCharacter(int k) {
        // Start with a sufficiently large length (power of 2)
        return getChar(k, 512);
    }

    private char getChar(int k, int n) {

        // Base case: the very first character is 'a'
        if (k == 1) return 'a';

        // If k lies in the first half, recurse directly
        if (k <= n / 2) {
            return getChar(k, n / 2);
        }
        else {
            // If k lies in the second half, map it to the first half
            char ch = getChar(k - (n / 2), n / 2);

            // Apply the transformation: shift character by +1 (with wrap-around)
            char nextCh = (char) ('a' + (ch + 1 - 'a') % 26);

            return nextCh;
        }
    }
}
