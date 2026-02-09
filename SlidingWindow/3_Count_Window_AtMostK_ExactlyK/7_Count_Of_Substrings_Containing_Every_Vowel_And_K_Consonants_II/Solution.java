import java.util.HashMap;
import java.util.Map;

class Solution {

    public long countOfSubstrings(String word, int k) {

        // We need substrings that:
        // 1) Contain all 5 vowels (a, e, i, o, u)
        // 2) Have exactly k consonants
        //
        // Instead of directly counting "exactly k",
        // we use the standard trick:
        //
        // exactly(k) = atLeast(k) - atLeast(k + 1)
        //
        // Reason:
        // Substrings with at least k consonants
        // minus
        // Substrings with at least (k + 1) consonants
        // leaves substrings with exactly k consonants.

        return atLeastK(word, k) - atLeastK(word, k + 1);
    }

    private long atLeastK(String word, int k) {

        // This function counts substrings that:
        // - Contain all 5 vowels
        // - Have at least k consonants

        long numValidSubstrings = 0;

        int start = 0;
        int end = 0;

        // Track frequency of vowels inside window
        Map<Character, Integer> vowelCount = new HashMap<>();

        // Track consonant count
        int consonantCount = 0;

        // Sliding window
        while (end < word.length()) {

            char newLetter = word.charAt(end);

            // Expand window by including newLetter
            if (isVowel(newLetter)) {

                // Update vowel frequency
                vowelCount.put(
                        newLetter,
                        vowelCount.getOrDefault(newLetter, 0) + 1
                );
            } else {

                // Increase consonant count
                consonantCount++;
            }

            // If window is valid:
            // - contains all 5 vowels
            // - consonantCount >= k
            //
            // Then all substrings extending further right
            // will also be valid.
            while (vowelCount.size() == 5 && consonantCount >= k) {

                // All substrings starting at 'start'
                // and ending at any index from 'end' to end of string
                // will remain valid.
                //
                // So we add:
                //   word.length() - end
                numValidSubstrings += word.length() - end;

                // Now shrink from left to find next valid window
                char startLetter = word.charAt(start);

                if (isVowel(startLetter)) {

                    vowelCount.put(
                            startLetter,
                            vowelCount.get(startLetter) - 1
                    );

                    // If frequency becomes zero,
                    // remove vowel from map
                    if (vowelCount.get(startLetter) == 0) {
                        vowelCount.remove(startLetter);
                    }
                } else {

                    // Reduce consonant count
                    consonantCount--;
                }

                start++;
            }

            end++;
        }

        return numValidSubstrings;
    }

    private boolean isVowel(char c) {

        // Helper function to identify vowels
        return c == 'a' || c == 'e' || c == 'i'
                || c == 'o' || c == 'u';
    }
}
