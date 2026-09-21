import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}


class Solution {
    
    public Node cloneGraph(Node node) {
        if(node==null) return null;
        Map<Node, Node> visited = new HashMap<>();
        return buildGraph(node, visited);
    }

    private Node buildGraph(Node originalNode, Map<Node, Node> visited) {
        Node cloneNode = new Node(originalNode.val);
        visited.put(originalNode, cloneNode);
        List<Node> cloneNeighbours = new ArrayList<>();
        for(Node neighbour : originalNode.neighbors) {
            if(visited.containsKey(neighbour)) {
                cloneNeighbours.add(visited.get(neighbour));
            }
            else {
                cloneNeighbours.add(buildGraph(neighbour, visited));
            }
        }
        cloneNode.neighbors = cloneNeighbours;
        return cloneNode;
    }
}