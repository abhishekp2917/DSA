import java.util.TreeMap;

class Solution {

    // START and END are chosen so that when two events
    // have the same time, END (-1) is processed before START (1).
    private static final int START = 1;
    private static final int END = -1;

    public int minMeetingRooms(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;

        // TreeMap keeps all events sorted by:
        // 1. event time
        // 2. event type
        //
        // The value stores the frequency because multiple meetings
        // can generate exactly the same event.
        TreeMap<int[], Integer> events = new TreeMap<>((event1, event2) -> {
            int comparison = Integer.compare(event1[0], event2[0]);
            if (comparison != 0) {
                return comparison;
            }

            return Integer.compare(event1[1], event2[1]);
        });

        for (int i = 0; i < n; i++) {
            int[] startEvent = {startTimes[i], START};
            int[] endEvent = {endTimes[i], END};

            // Add the meeting's START event.
            events.put(
                startEvent,
                events.getOrDefault(startEvent, 0) + 1
            );

            // Add the meeting's END event.
            events.put(
                endEvent,
                events.getOrDefault(endEvent, 0) + 1
            );
        }

        int activeRooms = 0;
        int maxRooms = 0;

        // Process events chronologically using a sweep line.
        for (int[] event : events.keySet()) {

            // START means one or more meetings begin,
            // so additional rooms are required.
            if (event[1] == START) {
                activeRooms += events.get(event);
            }

            // END means one or more meetings finish,
            // so those rooms become available.
            else {
                activeRooms -= events.get(event);
            }

            // The maximum number of simultaneously active meetings
            // determines the minimum number of rooms required.
            maxRooms = Math.max(maxRooms, activeRooms);
        }

        return maxRooms;
    }
}