import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {
    public int shoppingOffers(List<Integer> price, List<List<Integer>> offers, List<Integer> needs) {
        return recurrsion(price, offers, needs, new HashMap<>());
    }

    private int recurrsion(List<Integer> price, List<List<Integer>> offers, List<Integer> needs, Map<String, Integer> memo) {
        String key = needs.stream().map(String::valueOf).collect(Collectors.joining("_"));
        if(memo.containsKey(key)) return memo.get(key);
        int minPrice = 0;
        for (int i=0; i<needs.size(); i++) {
            minPrice += price.get(i)*needs.get(i);
        }
        for(List<Integer> offer : offers) {
            if(!isOfferAllowedToTake(offer, needs)) continue;
            List<Integer> newNeeds = new ArrayList<>();
            for(int i=0; i<needs.size(); i++) newNeeds.add(needs.get(i)-offer.get(i));
            minPrice = Math.min(minPrice, offer.get(offer.size()-1) + recurrsion(price, offers, newNeeds, memo));
        }
        memo.put(key, minPrice);
        return minPrice;
    }

    private boolean isOfferAllowedToTake(List<Integer> offer, List<Integer> needs) {
        boolean isAllowed = true;
        for(int i=0; i<needs.size(); i++) {
            if(needs.get(i)<offer.get(i)) {
                isAllowed = false;
                break;
            }
        }
        return isAllowed;
    }
}