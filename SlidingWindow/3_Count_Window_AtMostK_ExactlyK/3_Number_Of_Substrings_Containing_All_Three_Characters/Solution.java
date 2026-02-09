import java.util.HashMap;
import java.util.Map;

class Solution {
    public int numberOfSubstrings(String s) {

        // We need to count substrings that contain ALL THREE characters:
        // 'a', 'b', and 'c'.

        // Instead of directly counting such substrings,
        // we use a complementary counting approach.

        long n = s.length();

        // Total number of substrings in a string of length n
        // Formula:
        //     n * (n + 1) / 2
        long totalSubstring = n * (n + 1) / 2;

        // Count substrings that contain AT MOST 2 distinct characters
        //
        // Reason:
        // Any substring that does NOT contain all 3 characters
        // must have at most 2 distinct characters.
        long substringCountWithAtmostTwoChar = countAtmostK(s, 2);

        // Substrings containing ALL 3 characters =
        // total substrings - substrings with at most 2 distinct characters
        return (int) (totalSubstring - substringCountWithAtmostTwoChar);
    }

    private int countAtmostK(String s, int k) {

        // This function counts substrings
        // with AT MOST k distinct characters.

        int count = 0;

        int start = 0;

        // Frequency map to track characters in current window
        Map<Character, Integer> freqMap = new HashMap<>();

        for (int end = 0; end < s.length(); end++) {

            // Add current character to the window
            char ch = s.charAt(end);
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);

            // If window has more than k distinct characters,
            // shrink from the left until it becomes valid
            while (freqMap.size() > k) {

                ch = s.charAt(start);

                freqMap.put(ch, freqMap.get(ch) - 1);

                // Remove character completely if frequency becomes zero
                if (freqMap.get(ch) == 0) {
                    freqMap.remove(ch);
                }

                start++;
            }

            // Now window [start ... end] is valid
            // (contains at most k distinct characters)
            //
            // All substrings ending at 'end'
            // and starting from any index between start and end
            // are valid.
            //
            // Number of such substrings = (end - start + 1)
            count += (end - start + 1);
        }

        return count;
    }
}
