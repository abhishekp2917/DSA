import java.util.PriorityQueue;

class Solution {

    public int getNumberOfBacklogOrders(int[][] orders) {

        // BUY heap:
        // Max heap by price.
        //
        // Why max heap?
        // Because highest buy price gets priority.
        // Buyers willing to pay more should match first.
        PriorityQueue<int[]> buy =
            new PriorityQueue<>(
                (order1, order2) -> order2[0] - order1[0]
            );

        // SELL heap:
        // Min heap by price.
        //
        // Why min heap?
        // Because lowest sell price gets priority.
        // Sellers asking less should match first.
        PriorityQueue<int[]> sell =
            new PriorityQueue<>(
                (order1, order2) -> order1[0] - order2[0]
            );

        long backlogOrders = 0;

        // Process orders one by one in given order.
        //
        // Why sequentially?
        // Because stock exchange processes
        // incoming orders in time order.
        for (int[] order : orders) {

            int price = order[0];
            int quantity = order[1];
            boolean isBuyOrder = order[2] == 0;

            // Case 1: Buy order
            if (isBuyOrder) {

                // Try matching with lowest sell price.
                //
                // Matching condition:
                // sellPrice <= buyPrice
                //
                // And quantity must remain.
                while (!sell.isEmpty()
                        && sell.peek()[0] <= price
                        && quantity > 0) {

                    int[] sellOrder = sell.peek();

                    int sellOrderQuantity = sellOrder[1];

                    // Match as much as possible.
                    int orderPlaced =
                        Math.min(quantity, sellOrderQuantity);

                    quantity -= orderPlaced;
                    sellOrder[1] -= orderPlaced;

                    // If sell order exhausted, remove it.
                    if (sellOrder[1] == 0)
                        sell.poll();
                }
            }
            // Case 2: Sell order
            else {

                // Try matching with highest buy price.
                //
                // Matching condition:
                // buyPrice >= sellPrice
                while (!buy.isEmpty()
                        && buy.peek()[0] >= price
                        && quantity > 0) {

                    int[] buyOrder = buy.peek();

                    int buyOrderQuantity = buyOrder[1];

                    int orderPlaced =
                        Math.min(quantity, buyOrderQuantity);

                    quantity -= orderPlaced;
                    buyOrder[1] -= orderPlaced;

                    if (buyOrder[1] == 0)
                        buy.poll();
                }
            }

            // If some quantity still remains,
            // add it to appropriate backlog heap.
            if (quantity > 0) {
                if (isBuyOrder)
                    buy.add(new int[]{ price, quantity });
                else
                    sell.add(new int[]{ price, quantity });
            }
        }

        // Sum remaining quantities in both heaps.
        //
        // These represent unmatched backlog orders.
        while (!buy.isEmpty()) {
            backlogOrders += buy.poll()[1];
        }

        while (!sell.isEmpty()) {
            backlogOrders += sell.poll()[1];
        }

        // Return modulo as required.
        return (int) (backlogOrders % 1000000007);
    }
}
