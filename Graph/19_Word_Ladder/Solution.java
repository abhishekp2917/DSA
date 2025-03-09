import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // initialize the length to 1 since we are starting from beginWord
        int length = 1;
        // create a set of words for faster lookup and also for tracking visited words
        Set<String> words = new HashSet<>(wordList);
        // create a queue for BFS traversal
        Queue<String> queue = new LinkedList<>();
        // since we are starting from beginWord, add it to the queue
        queue.add(beginWord);
        while(!queue.isEmpty()) {
            // increment the length since we are moving to the next level
            length++;
            // get the size of the queue at this level so that we can process only the words at this level
            int queueSize = queue.size();
            while(queueSize-->0) {
                // get the word from the queue of this level
                String word = queue.poll();
                // we are using StringBuilder to modify the word since Strings are immutable
                // we are iterating over each character of the word and replacing it with all 26 alphabets to create new words
                // each new word will have one character different from the original word
                StringBuilder sb = new StringBuilder(word);
                for(int i=0; i<sb.length(); i++) {
                    for(int j=0; j<26; j++) {
                        char prevChar = sb.charAt(i);
                        char newChar = (char) ('a'+j);
                        sb.setCharAt(i, newChar);
                        String newWord = sb.toString();
                        // check if the newWord is present in the word set. If it is present, that means this word is not visited yet
                        // if not present, that means this word is already visited or not present in the wordList
                        if(words.contains(newWord)) {
                            // remove the newWord from the set to mark it as visited
                            words.remove(newWord);
                            // if the newWord is the endWord, return the length
                            if(newWord.equals(endWord)) {
                                return length;
                            }
                            // add the newWord to the queue for further processing
                            queue.add(newWord);
                        }
                        sb.setCharAt(i, prevChar);
                    }
                }
            }
        }
        // if we reach here, that means we have traversed all the words and we couldn't reach the endWord so return 0
        return 0;
    }
}