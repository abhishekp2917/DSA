import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution1 {
    public int longestStrChain(String[] words) {
        int n = words.length;
        int longestChainLen = 0;
        Map<String, Integer> wordIdxMap = new HashMap<>();
        Arrays.sort(words, (w1, w2) -> w2.length()-w1.length());
        for(int i=0; i<n; i++) wordIdxMap.put(words[i], i);
        Integer[] memo = new Integer[n];
        Arrays.fill(memo, -1);
        for(int i=0; i<n; i++) {
            longestChainLen = Math.max(
                longestChainLen,
                recursion(words, wordIdxMap, i, memo)
            );
        }
        return longestChainLen;
    }

    private int recursion(String[] words, Map<String, Integer> wordIdxMap, int idx, Integer[] memo) {
        if(memo[idx]!=null) return memo[idx];
        String word = words[idx];
        int chainLen = 1;
        for(int i=0; i<word.length(); i++) {
            String newWord = removeCharAtK(word, i);
            if(wordIdxMap.containsKey(newWord)) {
                int newWordIdx = wordIdxMap.get(newWord);
                chainLen = Math.max(
                    chainLen, 
                    1 + recursion(words, wordIdxMap, newWordIdx, memo)
                );
            } 
        }
        memo[idx] = chainLen;
        return chainLen;
    }

    private String removeCharAtK(String word, int k) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<word.length(); i++) {
            if(i==k) continue;
            sb.append(word.charAt(i));
        }
        return sb.toString();
    }
}

class Solution2 {
    public int longestStrChain(String[] words) {
        int n = words.length;
        int longestChainLen = 0;
        Map<String, Integer> wordIdxMap = new HashMap<>();
        Arrays.sort(words, (w1, w2) -> w1.length()-w2.length());
        for(int i=0; i<n; i++) wordIdxMap.put(words[i], i);
        int[] dp = new int[n];
        for(int idx=0; idx<n; idx++) {
            String word = words[idx];
            int chainLen = 1;
            for(int i=0; i<word.length(); i++) {
                String newWord = removeCharAtK(word, i);
                if(wordIdxMap.containsKey(newWord)) {
                    int newWordIdx = wordIdxMap.get(newWord);
                    chainLen = Math.max(chainLen, 1 + dp[newWordIdx]);
                }
            }
            dp[idx] = chainLen;
            longestChainLen = Math.max( longestChainLen, chainLen);
        }
        return longestChainLen;
    }

    private String removeCharAtK(String word, int k) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<word.length(); i++) {
            if(i==k) continue;
            sb.append(word.charAt(i));
        }
        return sb.toString();
    }
}


