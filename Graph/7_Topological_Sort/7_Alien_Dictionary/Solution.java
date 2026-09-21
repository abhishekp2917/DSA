import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Solution {
    
    public String findOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();

        for (String word : words) {
            for (char ch : word.toCharArray()) {
                graph.putIfAbsent(ch, new HashSet<>());
                indegree.putIfAbsent(ch, 0);
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String first = words[i];
            String second = words[i + 1];

            int minLength = Math.min(first.length(), second.length());
            int j = 0;

            while (j < minLength && first.charAt(j) == second.charAt(j)) {
                j++;
            }

            // Invalid ordering: ["abc", "ab"]
            if (j == minLength && first.length() > second.length()) {
                return "";
            }

            // First differing character gives the ordering
            if (j < minLength) {
                char from = first.charAt(j);
                char to = second.charAt(j);

                if (graph.get(from).add(to)) {
                    indegree.put(to, indegree.get(to) + 1);
                }
            }
        }

        Queue<Character> queue = new ArrayDeque<>();

        for (char ch : indegree.keySet()) {
            if (indegree.get(ch) == 0) {
                queue.offer(ch);
            }
        }

        StringBuilder order = new StringBuilder();

        while (!queue.isEmpty()) {
            char curr = queue.poll();
            order.append(curr);

            for (char next : graph.get(curr)) {
                indegree.put(next, indegree.get(next) - 1);

                if (indegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }

        // Cycle exists
        if (order.length() != indegree.size()) {
            return "";
        }

        return order.toString();
    }
}
