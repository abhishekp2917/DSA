class Solution {

    public String shiftingLetters(String s, int[][] shifts) {

        int n = s.length();

        StringBuilder ans = new StringBuilder();

        // Difference array to store shift changes
        //
        // cummulativeShift[i] = net shift effect starting at index i
        int[] cummulativeShift = new int[n + 1];

        // --------------------------------------------------
        // STEP 1: Apply range updates (difference array)
        // --------------------------------------------------
        for (int[] shift : shifts) {

            int start = shift[0];
            int end = shift[1];

            // direction:
            // 0 → left shift → -1
            // 1 → right shift → +1
            int dir = (shift[2] == 0) ? -1 : 1;

            // Apply boundary updates
            cummulativeShift[start] += dir;

            // Stop effect after 'end'
            cummulativeShift[end + 1] -= dir;
        }


        // --------------------------------------------------
        // STEP 2: Convert difference array → prefix sum
        // --------------------------------------------------
        //
        // After this:
        // cummulativeShift[i] = total shifts applied at index i
        for (int i = 1; i < n; i++) {

            cummulativeShift[i] += cummulativeShift[i - 1];
        }


        // --------------------------------------------------
        // STEP 3: Apply shifts to characters
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            char ch = s.charAt(i);

            // Normalize shift to [0, 25]
            //
            // Why?
            // Because shifts can be negative or large
            int shift =
                ((cummulativeShift[i] % 26) + 26) % 26;

            // Apply circular shift
            //
            // 'a' → 0, ..., 'z' → 25
            char newCh = (char)(
                'a' +
                ((ch - 'a') + shift) % 26
            );

            ans.append(newCh);
        }

        return ans.toString();
    }
}