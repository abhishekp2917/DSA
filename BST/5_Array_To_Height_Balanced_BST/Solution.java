class Node
{
    int data;
    Node left, right;

    Node(int item)
    {
        data = item;
        left = right = null;
    }
}

class Solution
{
    // declaring index which will keep track of index of answer array
    int index = 0;
    public int[] sortedArrayToBST(int[] nums)
    {
        // create an array to store preorder traversal of created BST
        int[] ans = new int[nums.length];
        traverse(nums, nums.length, 0, ans);
        return ans;
    }
    
    // this function will take 'nums' as an original array, N being the size of array in a particular recursive call
    // 'start' is the starting index of that particular array and 'ans' is our array to store preorder result
    void traverse(int[] nums, int N, int start, int[] ans) {
        // if size of array is not positive or index is out of answer array range, then just return 
        if(N<=0 || index>=nums.length) return;

        // find the mid index of current array based on 'N' and 'start'
        // if N is even then mid will be the floor value of division as we need lexicographically smallest preorder value  
        int mid = (2*start + N-1)/2;

        // insert that mid index value inside ans and increment the 'index' by 1
        ans[index] = nums[mid];
        index++;

        // traverse left by keeping left part of the mid index being the new array
        traverse(nums, mid-start, start, ans);

        // then traverse right by keeping right part of the mid index being the new array
        traverse(nums, start+N-1-mid, mid+1, ans);
    }
    
}