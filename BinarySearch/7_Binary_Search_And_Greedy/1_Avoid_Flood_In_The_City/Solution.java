import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public int[] avoidFlood(int[] rains) {

        // lastRainDay maps:
        //   lake -> last day it was filled with rain
        // This helps detect if a lake is getting rain again without being dried
        HashMap<Integer, Integer> lastRainDay = new HashMap<>();

        // dryDay stores indices of days when rains[i] == 0
        // TreeSet keeps them sorted so we can quickly find
        // the earliest possible dry day after a given rain day
        TreeSet<Integer> dryDay = new TreeSet<>();

        // ans[i] = -1 if it rains on day i
        // ans[i] = lake number to dry on dry day
        int[] ans = new int[rains.length];

        // Traverse day by day
        for (int i = 0; i < rains.length; i++) {

            // If today is a dry day
            if (rains[i] == 0) {

                // Store this index as an available drying opportunity
                dryDay.add(i);
            }

            // If today it rains on some lake
            else {

                // Mark rain day with -1 (as per problem statement)
                ans[i] = -1;

                // If this lake was already filled before and not dried yet,
                // then we MUST dry it sometime between lastRainDay and today
                if (lastRainDay.containsKey(rains[i])) {

                    int lastDay = lastRainDay.get(rains[i]);

                    // Find the earliest dry day that occurs AFTER lastDay
                    // Because drying must happen after previous fill
                    Integer ceil = dryDay.ceiling(lastDay);

                    // If such a dry day exists, we assign it to dry this lake
                    if (ceil != null) {

                        // On that dry day, we dry this lake
                        ans[ceil] = rains[i];

                        // Remove this dry day from available days
                        dryDay.remove(ceil);
                    }

                    // If no dry day exists to dry this lake in time,
                    // flooding is unavoidable → return empty array
                    else {
                        return new int[0];
                    }
                }

                // Update the last rain day for this lake to today
                lastRainDay.put(rains[i], i);
            }
        }

        // Any remaining dry days can dry ANY lake (problem allows arbitrary choice)
        // We assign them a dummy value (e.g., 1)
        for (Integer idx : dryDay) {
            ans[idx] = 1;
        }

        return ans;
    }
}
