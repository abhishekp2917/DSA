class Node{
    int data;
    Node left;
    Node right;
    Node(int data){
        this.data = data;
        left=null;
        right=null;
    }
}

class solver{
    static int sum_at_distK(Node root, int target, int k){
        int[] sum = new int[1];
        traverse(root, target, k, sum);
        return sum[0];
    }

    static int traverse(Node root, int target, int k, int[] sum) {
        if(root==null) return 0;
        // if we found the target node, then 
        // add the node value to sum and call calculateSum() method which will calculate all the nodes 
        // value which are at k distance from current node
        if(root.data==target) {
            sum[0] += root.data;
            calculateSum(root.left, k, sum);
            calculateSum(root.right, k, sum);
            // return k so that while backtracking, we would know if we have to add the node value to sum
            return k;
        }
        int left = traverse(root.left, target, k, sum);
        // if we get positive value from left subtree, that means the target node is in left subtree and current node
        // is within k distnace from target
        if(left>0) {
            // add the current node value to sum and call calculateSum() to traverse current node right subtree 
            // we are traversing only right because we got result from left subtree which shows it's already been traversed
            sum[0] += root.data;
            calculateSum(root.right, left-1, sum);
            // subtract 1 from what we recieved from left subtree to track the distance
            return left-1;
        } 
         // if we get positive value from right subtree, that means the target node is in right subtree and current node
        // is within k distnace from target
        int right = traverse(root.right, target, k, sum);
        if(right>0) {
            // add the current node value to sum and call calculateSum() to traverse current node left subtree 
            // we are traversing only left because we got result from right subtree which shows it's already been traversed
            sum[0] += root.data;
            calculateSum(root.left, right-1, sum);
            // subtract 1 from what we recieved from right subtree to track the distance
            return right-1;
        } 
        return 0;
    }

    // function that calculates sum of all the nodes which are within k distance from root
    static void calculateSum(Node root, int k, int[] sum) {
        if(root==null || k<=0) return;
        sum[0] += root.data;
        calculateSum(root.left, k-1, sum);
        calculateSum(root.right, k-1, sum);
    }
}