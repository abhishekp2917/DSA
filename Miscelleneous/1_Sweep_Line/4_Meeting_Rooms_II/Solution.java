import java.util.*;

class Solution {

    public int minMeetingRooms(int[] start, int[] end) {

        int n = start.length;

        // This will store the maximum number of
        // meetings active at the same time.
        int minRooms = 0;

        // TreeMap: time → list of events at that time
        //
        // Each event = {meetingId, type}
        //
        // type:
        // +1 → meeting starts
        // -1 → meeting ends
        //
        // Why TreeMap?
        // Because we need to process events
        // in sorted time order (sweep line).
        TreeMap<Integer, List<int[]>> eventsMap =
            new TreeMap<>();


        // Set to track currently active meetings
        //
        // Why Set?
        // To maintain which meetings are ongoing.
        //
        // Size of this set = number of rooms needed
        // at that moment.
        Set<Integer> activeMeetings =
            new HashSet<>();


        // --------------------------------------------------
        // STEP 1: Convert meetings into events
        // --------------------------------------------------
        //
        // Each meeting produces two events:
        //
        // start → +1 (meeting begins)
        // end   → -1 (meeting ends)
        //
        for (int i = 0; i < n; i++) {

            int startTime = start[i];
            int endTime = end[i];

            int meetingId = i;

            // Add start event
            List<int[]> events1 =
                eventsMap.getOrDefault(
                    startTime, new ArrayList<>());

            events1.add(new int[]{meetingId, 1});

            eventsMap.put(startTime, events1);


            // Add end event
            List<int[]> events2 =
                eventsMap.getOrDefault(
                    endTime, new ArrayList<>());

            events2.add(new int[]{meetingId, -1});

            eventsMap.put(endTime, events2);
        }


        // --------------------------------------------------
        // STEP 2: Sweep through time
        // --------------------------------------------------
        //
        // At each time point:
        //   process all events
        //   update active meetings
        //   track maximum overlap
        //
        for (List<int[]> events : eventsMap.values()) {

            for (int[] event : events) {

                int meetingId = event[0];
                int eventType = event[1];

                // Meeting starts → add to active set
                if (eventType == 1) {
                    activeMeetings.add(meetingId);
                }

                // Meeting ends → remove from active set
                else {
                    activeMeetings.remove(meetingId);
                }
            }

            // Number of active meetings at this time
            // equals number of rooms needed
            minRooms =
                Math.max(minRooms,
                         activeMeetings.size());
        }

        return minRooms;
    }
}