class Solution {

    public int minInsertions(String s) {

        int minInsert = 0;

        // openBraces = how many ')' we still NEED
        // (each '(' needs TWO ')')
        int openBraces = 0;


        // --------------------------------------------------
        // STEP 1: Traverse string
        // --------------------------------------------------
        for (char ch : s.toCharArray()) {

            // --------------------------------------------------
            // CASE 1: '(' → needs TWO ')'
            // --------------------------------------------------
            if (ch == '(') {

                // --------------------------------------------------
                // If we have negative requirement
                // → too many ')'
                // --------------------------------------------------
                if (openBraces < 0) {

                    // Fix extra ')'
                    minInsert += Math.abs(openBraces / 2);

                    // If odd imbalance → need extra '(' + ')'
                    if (openBraces % 2 != 0)
                        minInsert += 2;

                    openBraces = 0;
                }

                // --------------------------------------------------
                // If odd number of needed ')'
                // → we must fix it first
                // --------------------------------------------------
                else if (openBraces % 2 != 0) {

                    // Insert one ')'
                    openBraces--;
                    minInsert++;
                }

                // Now this '(' needs 2 ')'
                openBraces += 2;
            }

            // --------------------------------------------------
            // CASE 2: ')'
            // --------------------------------------------------
            else {
                openBraces--;
            }
        }


        // --------------------------------------------------
        // STEP 2: Fix remaining imbalance
        // --------------------------------------------------
        if (openBraces < 0) {

            // Too many ')'
            minInsert += Math.abs(openBraces / 2);

            if (openBraces % 2 != 0)
                minInsert += 2;
        }

        else {

            // Remaining needed ')'
            minInsert += openBraces;
        }

        return minInsert;
    }
}