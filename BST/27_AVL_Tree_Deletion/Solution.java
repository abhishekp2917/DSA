class Node
{
    int data, height;
    Node left, right;
    Node(int d) {
        data = d; 
        height = 1;
        left = right = null;
    }
}

class Sol
{
    public static Node deleteNode(Node root, int key)
    {
       if(root==null) return null;
       // move left if current node value is greater than the key
       if(root.data>key) root.left = deleteNode(root.left, key);
       // move right if current node value is smaller than the key 
       else if(root.data<key) root.right = deleteNode(root.right, key);
       else {
            // if current node is the node to be removed, check if it has both the child or not
            // if node has both the child then replace current node value with the largest node of its left child and then delete that largest node
            if(root.left!=null && root.right!=null) {
                Node temp = getLargestNode(root.left);
                root.data = temp.data;
                root.left = deleteNode(root.left, temp.data);
            }
            // if node has only one child, then just return that child
            else if(root.left==null) return root.right;
            else if(root.right==null) return root.left;
       }

        // while backtracking reset the height of each node as new node has added which 
        // will change the height of its ancestors
        setHeight(root);
        // modfiy tree to make it balance since a node has been removed 
        root = modifyTree(root);
        return root;
    }

    private static Node modifyTree(Node root) {
        // find balance of current root node
        int balance = getBalance(root);

        // left right rotation
        if(balance>1 && getBalance(root.left)<0) {
            root.left = leftRotate(root.left);
            root = rightRotate(root);
        }
        // right right rotation
        else if(balance>1 && getBalance(root.left)>=0) {
            root = rightRotate(root);
        }
        // right left rotation
        else if(balance<-1 && getBalance(root.right)>0) {
            root.right = rightRotate(root.right);
            root = leftRotate(root);
        }
        // left left rotation
        else if(balance<-1 && getBalance(root.right)<=0) {
            root = leftRotate(root);
        }
        return root;
    }

    // utility method to get largest node in a given tree
    private static Node getLargestNode(Node root) {
        Node temp = root;
        while(temp.right!=null) temp = temp.right;
        return temp;     
    }

    // rotating tree root in left direction
    private static Node leftRotate(Node root) {
        Node right = root.right;
        Node rightLeft = root.right.left;
        right.left = root;
        root.right = rightLeft;
        // resetting the height of root node and its right child because on left rotation
        // only this 2 node children has changed. As children changes, height of the parent 
        // node could change as newly added children can have height diff from previous child
        setHeight(root);
        setHeight(right);
        // returning right node as it will be the new root node
        return right;
    }

    // rotating tree root in right direction
    private static Node rightRotate(Node root) {
        Node left = root.left;
        Node leftRight = root.left.right;
        left.right = root;
        root.left = leftRight;
        // resetting the height of root node and its right child because on left rotation
        // only this 2 node children has changed.
        setHeight(root);
        setHeight(left);
        // returning left node as it will be the new root node
        return left;
    }

    // utility method to set the height of a node based on its left and right child height
    private static void setHeight(Node root) {
        int leftHeight = 0, rightHeight = 0;
        if(root.left!=null) leftHeight = root.left.height;
        if(root.right!=null) rightHeight = root.right.height;
        root.height = Math.max(leftHeight, rightHeight) + 1;
    }

    // utility method to get balance of a node
    private static int getBalance(Node root) {
        int leftHeight = 0, rightHeight = 0;
        if(root.left!=null) leftHeight = root.left.height;
        if(root.right!=null) rightHeight = root.right.height;
        return leftHeight - rightHeight;
    }
}