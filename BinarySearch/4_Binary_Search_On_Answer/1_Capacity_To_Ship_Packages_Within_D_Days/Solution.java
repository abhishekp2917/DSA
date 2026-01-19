class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int[] temp = totalWeights(weights);
        int i = temp[0], j = temp[1];
        int estCapacity = -1;
        while(i<=j) {
            int capacity = (i+j)/2;
            int estDays = calcDays(weights, capacity);
            if(estDays>days) i = capacity+1;
            else if(estDays<=days) {
                estCapacity = capacity;
                j = capacity-1;
            }
        }
        return estCapacity;
    }

    public int[] totalWeights(int[] weights) {
        int total = 0;
        int max = 0;
        for(int weight : weights) {
            max = Math.max(max, weight);
            total += weight;
        }
        return new int[] {max, total};
    }

    public int calcDays(int[] weights, int capacity) {
        int days = 0;
        int totalWeights = 0;
        for(int i=0; i<weights.length; i++) {
            if((totalWeights + weights[i])>capacity) {
                days++;
                totalWeights = 0;
            }
            totalWeights += weights[i];
        }
        return ++days;
    }
}