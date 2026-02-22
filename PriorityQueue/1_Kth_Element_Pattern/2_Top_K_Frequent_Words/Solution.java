import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {

    public List<String> topKFrequent(String[] words, int k) {

        // Final result list
        List<String> ans = new ArrayList<>();

        // Step 1: Count frequencies of each word.
        //
        // Why?
        // Because ranking depends on frequency.
        //
        // We first compress the array into:
        // word -> frequency
        //
        // This reduces repeated processing.
        Map<String, Integer> wordFreq = new HashMap<>();

        for (String word : words) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }

        // Step 2: Use a MIN HEAP of size k.
        //
        // Goal:
        // Keep only the top k frequent words.
        //
        // The heap will store words (not frequencies),
        // and comparator will use wordFreq map.

        PriorityQueue<String> minHeap = new PriorityQueue<>((word1, word2) -> {

            // Compare by frequency first.
            //
            // We want LOW frequency words to come first in heap.
            // Because if heap size exceeds k,
            // we remove the LOWEST priority word.
            int freqCompare = Integer.compare(
                wordFreq.get(word1),
                wordFreq.get(word2)
            );

            if (freqCompare == 0) {

                // If frequencies are equal:
                // Problem requires lexicographically smaller word first.
                //
                // But since this is a MIN HEAP,
                // and we remove the smallest element,
                // we must push lexicographically LARGER word first.
                //
                // Why?
                // Because among same frequency words,
                // lexicographically larger should be removed first.
                //
                // So we reverse comparison here.
                return word2.compareTo(word1);
            }

            return freqCompare;
        });

        // Step 3: Iterate over unique words only.
        //
        // Why not iterate original array?
        // Because duplicates are already counted.
        // We only need to push each unique word once.
        for (String word : wordFreq.keySet()) {

            minHeap.add(word);

            // Maintain heap size k.
            //
            // If size exceeds k,
            // remove lowest priority word.
            //
            // Lowest priority means:
            // - Lowest frequency
            // - If tie → lexicographically largest
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // Step 4: Extract elements from heap.
        //
        // Important:
        // Heap gives elements from lowest priority to highest.
        // But we want highest frequency first.
        while (!minHeap.isEmpty()) {
            ans.add(minHeap.poll());
        }

        // Reverse because:
        // We removed from lowest → highest,
        // but answer requires highest → lowest.
        Collections.reverse(ans);

        return ans;
    }
}
