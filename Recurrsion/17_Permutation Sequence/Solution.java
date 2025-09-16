import java.util.ArrayList;
import java.util.List;

class Solution {
    public String getPermutation(int n, int k) {
        // Initialize list of numbers [1, 2, 3, ..., n]
        List<Integer> numbers = new ArrayList<>();
        int totalPermutations = 1;

        for (int i = 1; i <= n; i++) {
            totalPermutations *= i;   // factorial calculation
            numbers.add(i);           // store available numbers
        }

        return buildPermutation(n, totalPermutations, k, numbers);
    }

    private String buildPermutation(int n, int totalPermutations, int k, List<Integer> numbers) {
        // Base case: only one number left
        if (n == 1) {
            return String.valueOf(numbers.get(0));
        }

        // Size of each group = (n-1)! 
        int groupSize = totalPermutations / n;

        // Find index of the number to pick
        int index = (k - 1) / groupSize; // 0-based index
        int newK = k - index * groupSize; // remaining k for next recursion

        // Pick number at current index and recurse for the rest
        int currentNum = numbers.remove(index);
        return currentNum + buildPermutation(n - 1, groupSize, newK, numbers);
    }
}
