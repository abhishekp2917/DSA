import java.util.List;

class Solution {

    public int longestEqualSubarray(List<Integer> nums, int k) {

        int n = nums.size();

        int maxFreq = 0;

        // freqMap[num]:
        // stores the frequency
        // of 'num'
        // inside the current sliding window.
        int[] freqMap = new int[n+1];

        int i=0, j=0;

        // Expand the window
        // one element at a time.
        while(j<nums.size()) {

            int num = nums.get(j);

            // Include the new element
            // in the current window.
            freqMap[num]++;

            // Maintain the highest frequency
            // seen in the current window.
            //
            // This represents
            // the element
            // we would like to keep
            // after deleting other elements.
            maxFreq = Math.max(maxFreq, freqMap[num]);

            // Window size - maxFreq
            // gives the minimum number of elements
            // that must be removed
            // to make the entire window
            // consist of a single value.
            //
            // If more than k deletions
            // are required,
            // shrink the window.
            if((j-i+1)-maxFreq>k) {

                num = nums.get(i);

                // Remove the leftmost element
                // before moving
                // the left boundary.
                freqMap[num]--;

                i++;
            }

            j++;
        }

        // The answer is simply
        // the largest frequency
        // of any value
        // inside a valid window.
        //
        // After deleting
        // at most k other elements,
        // these occurrences
        // become contiguous.
        return maxFreq;
    }
}