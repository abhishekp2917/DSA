import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    // Pattern: Divide & Conquer + Recursion with Memoization
    //
    // Idea:
    // Split the expression at every operator and recursively compute
    // all possible results for left and right sub-expressions.

    public List<Integer> diffWaysToCompute(String exp) {

        List<Integer> nums = new ArrayList<>();
        List<Character> symbols = new ArrayList<>();

        // Separate numbers and operators from the expression
        seperateNumAndSymbol(exp, nums, symbols);

        // Recursively compute all results for the full range
        return recursion(nums, symbols, 0, nums.size() - 1, new HashMap<>());
    }

    private List<Integer> recursion(
        List<Integer> nums,
        List<Character> symbols,
        int start,
        int end,
        Map<String, List<Integer>> memo
    ) {

        // Base case: single number
        if (start == end) return Arrays.asList(nums.get(start));

        String key = start + "_" + end;

        // Return cached result if already computed
        if (memo.containsKey(key)) return memo.get(key);

        List<Integer> results = new ArrayList<>();

        // Try splitting at every operator position
        for (int i = start; i < end; i++) {

            Character symbol = symbols.get(i);

            // Compute left and right sub-expressions
            List<Integer> result1 = recursion(nums, symbols, start, i, memo);
            List<Integer> result2 = recursion(nums, symbols, i + 1, end, memo);

            // Combine results from left and right
            for (Integer num1 : result1) {
                for (Integer num2 : result2) {
                    if (symbol == '+') results.add(num1 + num2);
                    else if (symbol == '-') results.add(num1 - num2);
                    else results.add(num1 * num2);
                }
            }
        }

        // Store result for memoization
        memo.put(key, results);

        return results;
    }

    // Parses the expression into numbers and operators
    private void seperateNumAndSymbol(String exp, List<Integer> nums, List<Character> symbols) {

        int num = 0;

        for (int i = 0; i < exp.length(); i++) {

            if (Character.isDigit(exp.charAt(i))) {
                num = num * 10 + exp.charAt(i) - '0';
            }
            else {
                nums.add(num);
                symbols.add(exp.charAt(i));
                num = 0;
            }
        }

        nums.add(num);
    }
}
