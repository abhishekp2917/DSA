import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class Node
{
    int data;
    List<Node> children;

    Node(int item)
    {
        data = item;
        children = new ArrayList<>();
    }
}

class Solution{
    
    // global variable to store the answer
    int count = 0;
    
    public int duplicateSubtreeNaryTree(Node root){
        // decalre map to store all the possible subtree mapped to its count
        HashMap<String, Integer> map = new HashMap<>();
        traverse(root, map);
        return count;
    }
    
    private String traverse(Node root,  HashMap<String, Integer> map) {
        // if root is null, return empty braces denoting null 
        if(root==null) return "()";
        // if root is a leaf node
        if(root.children.size()==0) {
            // create root node in string and check if it's present in hashmap or not
            String node = String.format("(%d)", root.data);
            // if node present in map and it's count is equal to 1, then increment the count var value
            // else put this node value in hashmap having count equal to 1
            // atlast return the node
            if(map.containsKey(node)) {
                if(map.get(node)==1) count++;
                map.put(node, map.get(node)+1);
            }
            else map.put(node, 1);
            return node;
        }
        // declaring string var to store all the children subtree after traversing each of them
        String children = "";
        for(Node node : root.children) {
            children += traverse(node, map);
        }
        // create current tree in string format and check if it's present in hashmap  
        String node = String.format("(%d%s)", root.data, children);
        // if curr tree is present in map and has count equal to 1, increment the count var value
        // else put the curr tree in hashmap and return the tree
        if(map.containsKey(node)) {
            if(map.get(node)==1) count++;
            map.put(node, map.get(node)+1);
        }
        else map.put(node, 1);
        return node;
    }
    
}