import java.util.Arrays;

class Solution {
    public int numFriendRequests(int[] ages) {

        int n = ages.length;

        int count = 0;

        // Frequency map since ages are between 1 and 120.
        int[] ageFreqMap = new int[121];

        int left = 0, right = 0;

        // Sorting is required because:
        // 1) It allows sliding window.
        // 2) Valid age range becomes monotonic.
        Arrays.sort(ages);

        // Build frequency map.
        for(int age : ages) ageFreqMap[age]++;

        while(right<n) {

            // Move right to last occurrence of same age.
            // This groups identical ages together.
            while(right<n-1 && ages[right]==ages[right+1]) {
                right++;
            }

            // Move left until condition becomes valid.
            //
            // Friend request condition:
            // B > 0.5*A + 7
            //
            // Here ages[right] is the receiver (A),
            // and ages[left] is sender (B).
            //
            // We remove invalid smaller ages.
            while(left<right && ages[left]<=(ages[right]*0.5+7)) left++;

            // Now all indices between left and right-1
            // are valid senders for age ages[right].
            //
            // Multiply by frequency of this age
            // because there may be multiple people of same age.
            count += (right-left)*ageFreqMap[ages[right]];

            right++;
        }

        return count;
    }
}
