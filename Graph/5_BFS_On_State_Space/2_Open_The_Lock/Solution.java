import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

class Solution {

    public int openLock(String[] deadends, String target) {
        Set<String> deadendsSet = new HashSet<>(Arrays.asList(deadends));

        if (deadendsSet.contains("0000")) {
            return -1;
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.offer("0000");
        visited.add("0000");

        int turns = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                String state = queue.poll();

                if (state.equals(target)) {
                    return turns;
                }

                for (int i=0; i<4; i++) {
                    String nextState = turn(state, i, 1);

                    if (!deadendsSet.contains(nextState) &&
                        visited.add(nextState)) {
                        queue.offer(nextState);
                    }

                    nextState = turn(state, i, -1);

                    if (!deadendsSet.contains(nextState) &&
                        visited.add(nextState)) {
                        queue.offer(nextState);
                    }
                }
            }
            turns++;
        }

        return -1;
    }

    private String turn(String state, int index, int direction) {
        char[] chars = state.toCharArray();
        int digit = chars[index] - '0';
        digit = (digit + direction + 10) % 10;
        chars[index] = (char) ('0' + digit);
        return new String(chars);
    }
}