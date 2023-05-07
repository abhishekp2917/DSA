import java.util.ArrayList;

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

class Tree
{
    //Function to return list containing elements of left view of binary tree.
    ArrayList<Integer> leftView(Node root)
    {
        // declare arraylist to store left view of tree
        ArrayList<Integer> leftViewList = new ArrayList<Integer>();

        findLeftView(root, 0, 0, leftViewList);
        return leftViewList;
    }

    //Function to return list containing elements of right view of binary tree.
    ArrayList<Integer> rightView(Node root)
    {
        // declare arraylist to store right view of tree
        ArrayList<Integer> rightViewList = new ArrayList<Integer>();

        findRightView(root, 0, 0, rightViewList);
        return rightViewList;
    }
    

    // function to find the left view
    // here parameter x and y will track horizontal and vertical position of the node in a tree resp.
    void findLeftView(Node root, int x, int y, ArrayList<Integer> leftViewList) {
        
        // if root is null then there is no further node to check on, so just return from there
        if(root==null) return;
        
        // since we are moving left side first, the very first node that we will encounter in each level will be part of left view.
        // so if list size is less than the horizontal position (Y) of the node, that means that node is leftest node of that level so insert that node in the list
        if(leftViewList.size()<=y) {
            leftViewList.add(root.data);
        }
        
        // move left side of the tree first
        findLeftView(root.left, x-1, y+1, leftViewList);

        // then move right side
        findLeftView(root.right, x+1, y+1, leftViewList);
    }

    // function to find the right view
    // here parameter x and y will track horizontal and vertical position of the node in a tree resp.
    void findRightView(Node root, int x, int y, ArrayList<Integer> rightViewList) {
        
        // if root is null then there is no further node to check on, so just return from there
        if(root==null) return;
        
        // since we are moving right side first, the very first node that we will encounter in each level will be part of right view.
        // so if list size is less than the horizontal position (Y) of the node, that means that node is rightest node of that level so insert that node in the list
        if(rightViewList.size()<=y) {
            rightViewList.add(root.data);
        }

        // move right side of the tree first
        findRightView(root.right, x+1, y+1, rightViewList);
        
        // then move left side
        findRightView(root.left, x-1, y+1, rightViewList);
    }
}