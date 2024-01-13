class Solution {
    public static int distributeTicket(int n,int k)
    {
		// start and end will be use to track the first and last element of the queue at the ticket counter
        int start = 1, end = n;
		// boolean to track whether to remove elements from the front or from the back
        boolean front = true;
        
		// keep removing k elements from the queue until queue size is less or equal to k
        while(end-start+1>k) {
			// if it is front turn, then increment the start value by k showing that k from front has been removed
            if(front) start += k;
			// if not from front, then remove k from back 
            else end -= k;
			// switch the turn after each operation
            front = !front;
        }
		// once k or less than k elements remained, then based on the current turn, return the last to be removed
		// if turn is to remove from front, then last removed will be last one else last removed will be from start 
        return (front)? end:start;
    }
}