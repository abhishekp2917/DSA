class Solution {

    public boolean canBeValid(String s, String locked) {

        int n = s.length();

        // --------------------------------------------------
        // Length must be even
        // --------------------------------------------------
        // Valid parentheses always have even length
        if (n % 2 != 0) return false;

        int flipCount = 0; // positions we can change (locked == '0')
        int openCount = 0; // balance of parentheses


        // --------------------------------------------------
        // PASS 1: LEFT → RIGHT
        // --------------------------------------------------
        //
        // Goal:
        // Avoid too many ')'
        //
        // Idea:
        // Use unlocked positions as '(' if needed
        for (int i = 0; i < n; i++) {

            if (locked.charAt(i) == '0') {
                // Can flip → treat as flexible
                flipCount++;
            }

            else if (s.charAt(i) == '(') {
                openCount++;
            }

            else {
                openCount--;
            }

            // --------------------------------------------------
            // If even after using flips we can't balance
            // → invalid
            // --------------------------------------------------
            if (flipCount + openCount < 0)
                return false;
        }


        // Reset counters
        flipCount = 0;
        openCount = 0;


        // --------------------------------------------------
        // PASS 2: RIGHT → LEFT
        // --------------------------------------------------
        //
        // Goal:
        // Avoid too many '('
        //
        // Idea:
        // Use unlocked positions as ')' if needed
        for (int i = n - 1; i >= 0; i--) {

            if (locked.charAt(i) == '0') {
                flipCount++;
            }

            else if (s.charAt(i) == ')') {
                openCount++;
            }

            else {
                openCount--;
            }

            if (flipCount + openCount < 0)
                return false;
        }

        return true;
    }
}