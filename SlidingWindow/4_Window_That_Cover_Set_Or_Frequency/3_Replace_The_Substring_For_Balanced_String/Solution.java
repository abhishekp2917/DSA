class Solution {
    public int balancedString(String s) {

        // A string is balanced if each of 'Q','W','E','R'
        // appears exactly n/4 times.

        int n = s.length();
        int req = n / 4;

        // Step 1: Count total frequency of each character.
        //
        // Why?
        // We need to know how many extra characters exist
        // beyond the required frequency.
        int[] count = new int[26];
        for (char ch : s.toCharArray()) {
            count[ch - 'A']++;
        }

        // Step 2: Reduce counts to only EXTRA occurrences.
        //
        // Why?
        // We don't care about required characters.
        // We only care about how many are exceeding n/4.
        count['Q' - 'A'] = Math.max(0, count['Q' - 'A'] - req);
        count['W' - 'A'] = Math.max(0, count['W' - 'A'] - req);
        count['E' - 'A'] = Math.max(0, count['E' - 'A'] - req);
        count['R' - 'A'] = Math.max(0, count['R' - 'A'] - req);

        // Step 3: If no extra characters exist,
        // string is already balanced.
        if (count['Q' - 'A'] == 0 &&
            count['W' - 'A'] == 0 &&
            count['E' - 'A'] == 0 &&
            count['R' - 'A'] == 0) {

            return 0;
        }

        int minLen = n;
        int start = 0;

        // Step 4: Use Sliding Window.
        //
        // Idea:
        // We want the smallest substring that contains
        // all extra characters.
        for (int end = 0; end < n; end++) {

            char ch = s.charAt(end);

            // When character enters window,
            // decrease its extra count.
            //
            // Why?
            // Because this character is now being replaced.
            count[ch - 'A']--;

            // Step 5: Try shrinking window
            // while it still covers all extras.
            //
            // Condition:
            // All extra counts <= 0
            //
            // Why?
            // That means this window contains enough
            // of every extra character.
            while (start <= end &&
                   count['Q' - 'A'] <= 0 &&
                   count['W' - 'A'] <= 0 &&
                   count['E' - 'A'] <= 0 &&
                   count['R' - 'A'] <= 0) {

                // Update minimum length
                minLen = Math.min(minLen, end - start + 1);

                // Remove left character from window
                //
                // Why?
                // Try to make window smaller
                // while still covering all extras.
                char left = s.charAt(start);
                count[left - 'A']++;
                start++;
            }
        }

        return minLen;
    }
}
