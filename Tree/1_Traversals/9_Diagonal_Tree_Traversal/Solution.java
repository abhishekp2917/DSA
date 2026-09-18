import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

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

class Solution {

    public ArrayList<Integer> diagonal(Node root) {       
        ArrayList<Integer> diagonal = new ArrayList<>();
        if (root==null) return diagonal;

        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-->0) {
                Node node = queue.poll();
                
                while (node!=null) {
                    diagonal.add(node.data);
                    if (node.left!=null) queue.offer(node.left);
                    node = node.right;
                }
            }
        }

        return diagonal;
    }
}