import java.util.List;

class Solution {
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> machines, List<Integer> stock, List<Integer> cost) {
        int ans = 0;
        int left=0, right=1000000000;
        while(left<=right) {
            int mid = (left+right)/2;
            boolean isPoss = false;
            for(int i=0; i<k; i++) {
                isPoss = isPoss || isPoss(machines.get(i), cost, stock, budget, mid);
            } 
            if(isPoss) {
                ans = mid;
                left = mid+1;
            }  
            else right = mid-1;
        }
        return ans;
    }

    private boolean isPoss(List<Integer> machine, List<Integer> cost, List<Integer> stocks, int budget, int quantity) {
        long totalCost = 0;
        for(int i=0; i<machine.size(); i++) {
            long currReq = (long)machine.get(i)*quantity-(long)stocks.get(i);
            if(currReq>0) {
                totalCost += currReq*cost.get(i); 
            }
        }
        return totalCost<=budget;
    }
}