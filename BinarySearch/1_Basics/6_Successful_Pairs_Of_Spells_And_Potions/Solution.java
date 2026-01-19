import java.util.Arrays;

class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {

        // We need to find, for each spell, how many potions can form
        // a successful pair such that:
        //      spell * potion >= success

        // Sorting potions allows us to use binary search to quickly find
        // the first potion that satisfies the condition for each spell
        Arrays.sort(potions);

        int[] ans = new int[spells.length];

        // For every spell, we independently compute how many potions work with it
        for (int i = 0; i < spells.length; i++) {

            // We find the index of the FIRST potion such that:
            //      spells[i] * potions[index] >= success
            // All potions from this index till the end will form valid pairs
            int idx = findUpperBound(potions, spells[i], success);

            // Total valid potions = total potions - first valid index
            ans[i] = potions.length - idx;
        }

        return ans;
    }

    // This function finds the FIRST index where:
    //      arr[mid] * num >= success
    // This is a classic "first true" binary search
    public int findUpperBound(int[] arr, int num, long success) {

        int start = 0, end = arr.length - 1;

        // Default value: if no potion satisfies the condition,
        // index remains arr.length (means 0 valid potions)
        int upperBound = arr.length;

        while (start <= end) {

            int mid = (start + end) / 2;

            // We cast to long because multiplication may overflow int
            long product = (long) arr[mid] * num;

            // If this potion with this spell is successful,
            // then this index is a valid candidate
            // But there might exist an earlier potion that also works
            if (product >= success) {

                // Store this index as possible first valid position
                upperBound = mid;

                // Try to find an even smaller index on the left
                end = mid - 1;
            }

            // If product is smaller than success,
            // then this potion and all potions to the left are too weak
            else {
                start = mid + 1;
            }
        }

        // Returns index of first potion that forms a successful pair
        return upperBound;
    }
}
