import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<Integer> diffWaysToCompute(String exp) {
        List<Integer> nums = new ArrayList<>();
        List<Character> symbols = new ArrayList<>();
        seperateNumAndSymbol(exp, nums, symbols);
        return recursion(nums, symbols, 0, nums.size()-1, new HashMap<>());
    }

    private List<Integer> recursion(List<Integer> nums, List<Character> symbols, int start, int end, Map<String, List<Integer>> memo) {
        if(start==end) return Arrays.asList(nums.get(start));
        String key = start+"_"+end;
        if(memo.containsKey(key)) return memo.get(key);
        List<Integer> results = new ArrayList<>();
        for(int i=start; i<end; i++) {
            Character symbol = symbols.get(i);
            List<Integer> result1 = recursion(nums, symbols, start, i, memo);
            List<Integer> result2 = recursion(nums, symbols, i+1, end, memo);
            for(Integer num1 : result1) {
                for(Integer num2 : result2) {
                    if(symbol=='+') results.add(num1+num2);
                    else if(symbol=='-') results.add(num1-num2);
                    else results.add(num1*num2);                       
                }
            }
        }
        memo.put(key, results);
        return results;
    }

    private void seperateNumAndSymbol(String exp, List<Integer> nums, List<Character> symbols) {
        int num = 0;
        for(int i=0; i<exp.length(); i++) {
            if(Character.isDigit(exp.charAt(i))) {
                num = num*10 + exp.charAt(i)-'0';
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

