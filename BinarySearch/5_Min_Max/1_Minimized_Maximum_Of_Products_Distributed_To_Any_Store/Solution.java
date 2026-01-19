class Solution {

    public int minimizedMaximum(int n, int[] quantities) {

        // We want to distribute all products into n stores such that:
        // - Each store gets at most 'X' products
        // - X is minimized
        //
        // We are NOT choosing exact distribution —
        // we are searching the ANSWER: minimum possible maximum quantity per store.

        // Lower bound:
        // At least 1 product per store is possible minimum
        int minQuantity = 1;

        // Upper bound:
        // The worst case is when one store takes all of a product type,
        // so maximum possible value = max quantity in the array
        int maxQuantity = 0;
        for (int quantity : quantities) {
            maxQuantity = Math.max(quantity, maxQuantity);
        }

        // This will store the smallest feasible maximum quantity per store
        int minQuantityPerStore = 0;

        // Binary search on the ANSWER (maximum allowed quantity per store)
        while (minQuantity <= maxQuantity) {

            // Try a candidate maximum quantity per store
            int quantityPerStore = (minQuantity + maxQuantity) / 2;

            // Compute how many stores are REQUIRED
            // if no store can take more than quantityPerStore
            if (numOfStoresCovered(quantities, quantityPerStore) <= n) {

                // If we can distribute using at most n stores,
                // then this quantityPerStore is FEASIBLE
                minQuantityPerStore = quantityPerStore;

                // Try to minimize it further
                // Reason: we want the smallest possible maximum load
                maxQuantity = quantityPerStore - 1;
            }

            // If more than n stores are required,
            // then quantityPerStore is too small
            // We must allow larger per-store capacity
            else {
                minQuantity = quantityPerStore + 1;
            }
        }

        // minQuantityPerStore is the minimum possible maximum per-store quantity
        return minQuantityPerStore;
    }

    private int numOfStoresCovered(int[] quantities, int quantityPerStore) {

        // This function calculates:
        // How many stores are REQUIRED if
        // each store can take at most 'quantityPerStore' items.

        int count = 0;

        for (int i = 0; i < quantities.length; i++) {

            // For each product type:
            // We split its quantity into chunks of size at most quantityPerStore

            // Number of stores needed =
            //      ceil(quantities[i] / quantityPerStore)

            // Integer division gives floor, so we adjust using remainder
            count += quantities[i] / quantityPerStore;

            // If some remainder exists, we need one more store
            if (quantities[i] % quantityPerStore != 0) {
                count++;
            }
        }

        // Return total number of stores required
        return count;
    }
}
