import java.util.List;

class Solution {
    public int maxNumberOfAlloys(int n,
                                 int k,
                                 int budget,
                                 List<List<Integer>> machines,
                                 List<Integer> stock,
                                 List<Integer> cost) {

        // We have k different machines.
        // Each machine has a recipe to build 1 alloy.
        // We want to build the MAXIMUM number of alloys
        // using AT MOST 'budget' money.

        // We are NOT choosing exact machines and quantities directly.
        // We are searching the ANSWER: maximum number of alloys.

        // Binary search on the ANSWER (number of alloys)

        // Minimum possible alloys = 0
        int left = 0;

        // Maximum possible alloys = very large (given by constraints)
        // Reason:
        // We don't know exact upper bound, but this safely covers all cases
        int right = 1_000_000_000;

        // This will store the maximum feasible number of alloys
        int ans = 0;

        // Binary search on quantity
        while (left <= right) {

            // Try producing 'mid' alloys
            int mid = (left + right) / 2;

            boolean isPoss = false;

            // We try EACH machine:
            // If ANY machine can produce 'mid' alloys within budget,
            // then 'mid' is feasible
            for (int i = 0; i < k; i++) {

                // Check feasibility for machine i
                isPoss = isPoss || isPoss(machines.get(i),
                                           cost,
                                           stock,
                                           budget,
                                           mid);
            }

            // If at least one machine can make 'mid' alloys,
            // then this quantity is FEASIBLE
            if (isPoss) {

                // Store as possible answer
                ans = mid;

                // Try to produce even more alloys
                // Reason:
                // We want the MAXIMUM possible quantity
                left = mid + 1;
            }

            // If no machine can produce 'mid' alloys within budget,
            // then this quantity is too large
            else {

                // Try smaller quantity
                right = mid - 1;
            }
        }

        // ans is the maximum number of alloys we can produce
        return ans;
    }

    private boolean isPoss(List<Integer> machine,
                           List<Integer> cost,
                           List<Integer> stocks,
                           int budget,
                           int quantity) {

        // This function checks:
        // Can THIS machine produce exactly 'quantity' alloys
        // within the given budget?

        long totalCost = 0;

        for (int i = 0; i < machine.size(); i++) {

            // machine.get(i) = units of metal i needed per alloy
            // quantity = number of alloys to make
            //
            // Total required units of metal i =
            //     machine[i] * quantity

            long required = (long) machine.get(i) * quantity;

            // We already have some stock available
            long available = stocks.get(i);

            // Additional units we need to BUY
            long currReq = required - available;

            // If required more than available,
            // we need to buy the extra amount
            if (currReq > 0) {

                // Cost = extra units * per-unit cost
                totalCost += currReq * cost.get(i);
            }
        }

        // Check if total buying cost fits within budget
        return totalCost <= budget;
    }
}
