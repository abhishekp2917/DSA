class Solution {
    public int countKConstraintSubstrings(String s, int k) {

        // We need to count substrings where:
        //   NOT (number of 1s > k AND number of 0s > k)
        //
        // Meaning:
        //   At least one of them must be <= k.
        //
        // Instead of checking all substrings (O(n^2)),
        // we use Sliding Window.

        int n = s.length();

        int substrCount = 0;

        int start = 0, end = 0;

        int onesCount = 0;
        int zeroesCount = 0;

        while (end < n) {

            // Expand window by including s[end]
            if (s.charAt(end) == '1') {
                onesCount++;
            } else {
                zeroesCount++;
            }

            // If BOTH counts exceed k,
            // then window becomes INVALID.
            //
            // Condition:
            //   onesCount > k AND zeroesCount > k
            //
            // Reason:
            // Only when both exceed k is substring invalid.
            while (onesCount > k && zeroesCount > k) {

                // Shrink from left
                if (s.charAt(start) == '1') {
                    onesCount--;
                } else {
                    zeroesCount--;
                }

                start++;
            }

            // At this point,
            // window [start ... end] is VALID.
            //
            // All substrings ending at 'end'
            // and starting from any index in [start ... end]
            // are valid.
            //
            // Count of such substrings = (end - start + 1)
            substrCount += (end - start + 1);

            end++;
        }

        return substrCount;
    }
}
