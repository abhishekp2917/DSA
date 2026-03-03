import java.util.PriorityQueue;

class Solution {

    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = tasks.length;

        int[] result = new int[n];

        // Heap 1: Available servers
        //
        // Ordered by:
        // 1. Smaller weight first
        // 2. Smaller index if tie
        //
        // Why?
        // Problem requires:
        // Always assign task to server with smallest weight.
        // If tie, smaller index.
        PriorityQueue<Server> availableServers =
            new PriorityQueue<>((a, b) -> {
                if (a.weight == b.weight)
                    return a.index - b.index;
                return a.weight - b.weight;
            });

        // Heap 2: Unavailable servers (currently processing)
        //
        // Ordered by:
        // 1. Earliest finishing time
        // 2. Smaller weight
        // 3. Smaller index
        //
        // Why?
        // So that we can quickly free servers
        // in chronological order of completion.
        PriorityQueue<Server> unavailableServers =
            new PriorityQueue<>((a, b) -> {
                if (a.time == b.time) {
                    if (a.weight == b.weight)
                        return a.index - b.index;
                    return a.weight - b.weight;
                }
                return a.time - b.time;
            });

        // Initially, all servers are available.
        for (int i = 0; i < servers.length; i++) {
            availableServers.add(new Server(i, servers[i]));
        }

        // Iterate over tasks.
        //
        // Task i arrives at time = i.
        for (int i = 0; i < n; i++) {

            // Step 1:
            // Free all servers that have finished
            // by current time i.
            while (!unavailableServers.isEmpty()
                    && unavailableServers.peek().time <= i) {

                availableServers.add(
                    unavailableServers.poll()
                );
            }

            Server server;

            // Step 2:
            // If no server available,
            // we must wait for earliest finishing server.
            if (availableServers.isEmpty()) {

                // Get server that finishes earliest.
                server = unavailableServers.poll();

                result[i] = server.index;

                // IMPORTANT:
                // Since we waited until server.time,
                // its finishing time becomes:
                // server.time + tasks[i]
                server.time += tasks[i];
            }
            else {

                // Assign smallest weight server.
                server = availableServers.poll();

                result[i] = server.index;

                // It will finish at:
                // current time + task duration
                server.time = i + tasks[i];
            }

            // Mark server as unavailable.
            unavailableServers.add(server);
        }

        return result;
    }
}

class Server {

    int index;
    int weight;
    int time;   // next available time

    public Server(int index, int weight) {
        this.index = index;
        this.weight = weight;
        this.time = 0;
    }
}
