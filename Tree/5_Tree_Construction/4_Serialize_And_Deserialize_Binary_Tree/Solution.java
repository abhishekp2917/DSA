class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder serializedTree = new StringBuilder();
        preorderSerialization(root, serializedTree);
        return serializedTree.toString();
    }


    private void preorderSerialization(TreeNode root, StringBuilder serializedTree) {
        if(root==null) {
            serializedTree.append("N").append(",");
            return;
        }
        serializedTree.append(root.val).append(",");
        preorderSerialization(root.left, serializedTree);
        preorderSerialization(root.right, serializedTree);
    }


    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.isEmpty()) return null;
        String[] nodes = data.split(",");
        int[] index = new int[1];
        return preorderDeserialization(nodes, index);
    }

    private TreeNode preorderDeserialization(String[] nodes, int[] index) {
        String val = nodes[index[0]];
        index[0]++;
        if(val.equals("N")) return null;
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = preorderDeserialization(nodes, index);
        node.right = preorderDeserialization(nodes, index);
        return node;
    }
}