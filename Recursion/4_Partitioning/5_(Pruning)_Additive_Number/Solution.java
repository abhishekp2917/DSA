class Solution {

    // Pattern: Backtracking / DFS (Partitioning with Pruning)
    //
    // Core intuition:
    // We need to split the string into a sequence of numbers such that
    // starting from the third number, every number is the sum of the
    // previous two.
    //
    // The order of digits is fixed. The only choice we make is where
    // to split the string to form the next number.

    public boolean isAdditiveNumber(String num) {

        // thirdLast, secLast, last represent the last three numbers
        // in the sequence built so far.
        // Initially, all are unset (-1).
        return recursion(num, -1, -1, -1);
    }

    private boolean recursion(
        String num,
        long thirdLast,
        long secLast,
        long last
    ) {

        // Base case:
        // If the entire string has been consumed, we return true
        // only if we have formed at least three numbers.
        if (num.length() == 0) {
            return (thirdLast != -1 && secLast != -1 && last != -1);
        }

        long newNum = 0;

        // Try all possible prefixes of the remaining string
        for (int i = 0; i < num.length(); i++) {

            // Build the next number digit by digit
            newNum = newNum * 10 + num.charAt(i) - '0';

            // Leading zero check:
            // If converting back to string shortens the length,
            // it means there was a leading zero.
            if (String.valueOf(newNum).length() < (i + 1)) return false;

            // Pruning:
            // Stop if number becomes too large or exceeds the expected sum
            if (newNum > 10000000000000000L ||
                (secLast != -1 && last != -1 && newNum > secLast + last)) {
                break;
            }

            // Case 1: We are still forming the first two numbers
            if (secLast == -1) {

                if (recursion(
                        num.substring(i + 1, num.length()),
                        secLast,
                        last,
                        newNum
                )) return true;
            }

            // Case 2: Check additive condition
            else if (newNum == secLast + last) {

                if (recursion(
                        num.substring(i + 1, num.length()),
                        secLast,
                        last,
                        newNum
                )) return true;
            }
        }

        // No valid split found
        return false;
    }
}
