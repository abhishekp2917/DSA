import java.util.HashSet;

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
    HashSet<Integer> set = new HashSet<>();
    
    public int isPairPresent(Node root, int target)
    {
        if(root==null) return 0;
        // traverse tree in inorder fashion i.e. left -> root -> right
        // one can traverse in any fashion to find the answer
        int left = isPairPresent(root.left, target);

        // check if current node pair present (node pair will be (target - node value)) in set 
        // if pair is present int the set, then return 1
        if(set.contains(target-root.data)) return 1;

        // for every node traversed, add that node data in set so that there could be possiblity of finding its pair in future traversal 
        set.add(root.data);

        // traverse right and store it's result
        int right = isPairPresent(root.right, target);

        // return max of left and right returned value so that if any one of them have 1 (pair present) then that value will
        // traverse backward up 
        return Math.max(left, right);
    }
}

