import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // map to store the length of the word from beginWord
        HashMap<String, Integer> wordsLength = new HashMap<>();
        // initialize the length of all words to infinity
        wordList.stream().forEach(word -> wordsLength.put(word, Integer.MAX_VALUE));
        // add the beginWord to the map and set its length to 1 since it is the starting word
        wordsLength.put(beginWord, 1);
        // create a queue to store the words to be processed
        Queue<String> queue = new LinkedList<>();
        // add the beginWord to the queue to start the processing
        queue.add(beginWord);
        while(!queue.isEmpty()) {
            // get the word from the queue
            String word = queue.poll();
            // we are using StringBuilder to change the characters of the word
            StringBuilder sb = new StringBuilder(word);
            // iterate through all the characters of the word and one by one change them to all the alphabets 'a' to 'z'
            for(int i=0; i<sb.length(); i++) {
                for(int j=0; j<26; j++) {
                    char prevChar = sb.charAt(i);
                    char newChar = (char) ('a'+j);
                    sb.setCharAt(i, newChar);
                    String newWord = sb.toString();
                    // check if the new word created after changing single character in original word is present in the wordList
                    if(wordsLength.containsKey(newWord)) {
                        int prevLength = wordsLength.get(newWord);
                        int newLength = wordsLength.get(word) + 1;
                        // if the length of the new word is less than the length of the original word + 1, then update the length of the new word and add it to the queue
                        if(newLength<prevLength) {
                            wordsLength.put(newWord, newLength);
                            queue.add(newWord);
                        }
                    }
                    sb.setCharAt(i, prevChar);
                }
            }
        }
        // if the length of the endWord is not infinity (that means there is a way to transform from begin word to end word), then return the length of the endWord, else return 0
        return (wordsLength.getOrDefault(endWord, Integer.MAX_VALUE)!=Integer.MAX_VALUE)? wordsLength.get(endWord):0;
    }
}