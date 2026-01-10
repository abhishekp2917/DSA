class Solution {

    // Pattern: Backtracking / DFS (Permutation with Frequency Constraint)
    //
    // Core intuition:
    // We want to form all possible 3-digit numbers using the given digits,
    // such that:
    // 1) Each digit is used at most as many times as it appears in input
    // 2) The number is a valid 3-digit number (>= 100)
    // 3) The number is even (last digit must be even)

    public int totalNumbers(int[] digits) {

        // digitCount[i] stores how many times digit i is available
        int[] digitCount = new int[10];
        for (int digit : digits) digitCount[digit]++;

        // Start building numbers digit by digit, target length = 3
        return countNumbers(digitCount, 0, 3);
    }

    private int countNumbers(
        int[] digitCount,
        int num,
        int n
    ) {

        // Base case:
        // When we have built a 3-digit number
        if (n == 0) {

            // Check validity:
            // - num must be even
            // - num must be a 3-digit number (>= 100)
            if (num % 2 == 0 && num >= 100) return 1;
            else return 0;
        }

        int ans = 0;

        // Try placing each available digit at the current position
        for (int i = 0; i < 10; i++) {

            // Only use digit i if it is still available
            if (digitCount[i] > 0) {

                // Choose digit i
                digitCount[i]--;

                // Append digit i and recurse to next position
                ans += countNumbers(
                    digitCount,
                    num * 10 + i,
                    n - 1
                );

                // Backtrack: restore digit count
                digitCount[i]++;
            }
        }

        return ans;
    }
}
