import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {

    // Pattern: DFS + Memoization (Combination with State Reduction)
    //
    // Core intuition:
    // We want to minimize the total cost to satisfy given needs.
    // At each step, we can either:
    // 1) Buy remaining items individually, or
    // 2) Apply any valid special offer and reduce the needs.
    //
    // The same "needs state" can be reached via different paths,
    // so memoization is used to avoid recomputation.

    public int shoppingOffers(
        List<Integer> price,
        List<List<Integer>> offers,
        List<Integer> needs
    ) {
        return recurrsion(price, offers, needs, new HashMap<>());
    }

    private int recurrsion(
        List<Integer> price,
        List<List<Integer>> offers,
        List<Integer> needs,
        Map<String, Integer> memo
    ) {

        // Convert current needs into a string key for memoization
        String key = needs.stream()
                          .map(String::valueOf)
                          .collect(Collectors.joining("_"));

        if (memo.containsKey(key)) return memo.get(key);

        // Base option:
        // Buy all remaining items individually without using any offer
        int minPrice = 0;
        for (int i = 0; i < needs.size(); i++) {
            minPrice += price.get(i) * needs.get(i);
        }

        // Try applying each special offer
        for (List<Integer> offer : offers) {

            // Skip offers that cannot be applied to current needs
            if (!isOfferAllowedToTake(offer, needs)) continue;

            // Apply the offer: reduce the needs accordingly
            List<Integer> newNeeds = new ArrayList<>();
            for (int i = 0; i < needs.size(); i++) {
                newNeeds.add(needs.get(i) - offer.get(i));
            }

            // Cost = offer price + cost of remaining needs
            minPrice = Math.min(
                minPrice,
                offer.get(offer.size() - 1)
                + recurrsion(price, offers, newNeeds, memo)
            );
        }

        // Store the minimum cost for this needs state
        memo.put(key, minPrice);

        return minPrice;
    }

    // Checks whether an offer can be applied to the current needs
    private boolean isOfferAllowedToTake(
        List<Integer> offer,
        List<Integer> needs
    ) {

        for (int i = 0; i < needs.size(); i++) {
            if (needs.get(i) < offer.get(i)) {
                return false;
            }
        }
        return true;
    }
}
