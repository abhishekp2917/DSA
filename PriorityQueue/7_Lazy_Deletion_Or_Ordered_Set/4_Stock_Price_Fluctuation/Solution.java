import java.util.HashMap;
import java.util.TreeMap;

class StockPrice {

    // Maps timestamp → price
    //
    // Why?
    // Because:
    // 1. We need to update an existing timestamp.
    // 2. We need to retrieve current price quickly.
    HashMap<Integer, Integer> timestampPriceMap;

    // TreeMap of price → frequency
    //
    // Why TreeMap?
    // Because:
    // - It keeps prices sorted.
    // - We can get:
    //      minimum price  → firstKey()
    //      maximum price  → lastKey()
    //
    // Why frequency?
    // Because multiple timestamps may have same price.
    TreeMap<Integer, Integer> priceFreqMap;

    // Tracks latest timestamp seen so far.
    int currentTimestamp = 0;

    public StockPrice() {
        timestampPriceMap = new HashMap<>();
        priceFreqMap = new TreeMap<>();
    }
    
    public void update(int timestamp, int price) {

        // Update current timestamp if needed.
        //
        // Why?
        // Because timestamps may arrive out of order.
        // current() must return price at highest timestamp.
        currentTimestamp =
            Math.max(currentTimestamp, timestamp);

        // If this timestamp already existed,
        // we must remove its previous price from frequency map.
        //
        // Why?
        // Because price is being corrected.
        if (timestampPriceMap.containsKey(timestamp)) {

            int prevPrice =
                timestampPriceMap.get(timestamp);

            // Decrease frequency of old price.
            priceFreqMap.put(
                prevPrice,
                priceFreqMap.get(prevPrice) - 1
            );

            // If frequency becomes zero,
            // remove price from TreeMap.
            //
            // Why?
            // Because TreeMap must reflect
            // only active prices.
            if (priceFreqMap.get(prevPrice) == 0)
                priceFreqMap.remove(prevPrice);
        }

        // Update timestamp → price mapping.
        timestampPriceMap.put(timestamp, price);

        // Increase frequency of new price.
        priceFreqMap.put(
            price,
            priceFreqMap.getOrDefault(price, 0) + 1
        );
    }
    
    public int current() {

        // Return price at latest timestamp.
        return timestampPriceMap.get(currentTimestamp);
    }
    
    public int maximum() {

        // Largest key in sorted TreeMap.
        return priceFreqMap.lastKey();
    }
    
    public int minimum() {

        // Smallest key in sorted TreeMap.
        return priceFreqMap.firstKey();
    }
}
