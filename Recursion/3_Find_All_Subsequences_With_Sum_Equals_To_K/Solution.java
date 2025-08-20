import java.util.ArrayList;
import java.util.List;

class Solution {
    
    public static List<List<Integer>> subsequencesSumK(int[] arr, int K) {
        // Result list to store all subsequences with sum K
        List<List<Integer>> res = new ArrayList<>();
        
        // Temporary list to store the current subsequence being formed
        List<Integer> currSubSeq = new ArrayList<>();
        
        // Start recursive exploration from index 0 with sum = 0
        findSubsequence(arr, K, 0, res, currSubSeq, 0);
        
        return res;
    }

    private static void findSubsequence(int[] arr, int K, int ptr,
                                       List<List<Integer>> res,
                                       List<Integer> currSubSeq,
                                       int currSum) {
        
        // ---- BASE CASE ----
        // If we've checked all elements
        if(ptr >= arr.length) {
            // If the sum of the subsequence matches K, save it
            if(currSum == K) {
                List<Integer> subSeq = new ArrayList<>(currSubSeq);
                res.add(subSeq);
            }
            return; // End this branch
        }

        // ---- CHOICE 1: Include current element ----
        currSubSeq.add(arr[ptr]);  // Add element to subsequence
        findSubsequence(arr, K, ptr + 1, res, currSubSeq, currSum + arr[ptr]);

        // ---- BACKTRACK ----
        currSubSeq.remove(currSubSeq.size() - 1); // Undo inclusion

        // ---- CHOICE 2: Exclude current element ----
        findSubsequence(arr, K, ptr + 1, res, currSubSeq, currSum);
    }
}
