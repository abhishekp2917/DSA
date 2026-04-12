class Solution {

    public boolean checkValidString(String s) {

        int n = s.length();

        int openBraces = 0; // balance of '('
        int star = 0;       // count of '*'


        // --------------------------------------------------
        // PASS 1: LEFT → RIGHT
        // --------------------------------------------------
        //
        // Goal:
        // Ensure we NEVER have too many ')'
        //
        // Idea:
        // Treat '*' as '(' when needed
        for (char ch : s.toCharArray()) {

            if (ch == '(') openBraces++;

            else if (ch == ')') openBraces--;

            else star++; // '*' can help later

            // --------------------------------------------------
            // If even after using '*' we can't balance
            // → invalid
            // --------------------------------------------------
            if (openBraces + star < 0)
                return false;
        }


        // Reset counters
        openBraces = 0;
        star = 0;


        // --------------------------------------------------
        // PASS 2: RIGHT → LEFT
        // --------------------------------------------------
        //
        // Goal:
        // Ensure we NEVER have too many '('
        //
        // Idea:
        // Treat '*' as ')' when needed
        for (int i = n - 1; i >= 0; i--) {

            char ch = s.charAt(i);

            if (ch == ')') openBraces++;

            else if (ch == '(') openBraces--;

            else star++;

            // --------------------------------------------------
            // If even after using '*' we can't balance
            // → invalid
            // --------------------------------------------------
            if (openBraces + star < 0)
                return false;
        }

        return true;
    }
}