import java.util.HashMap;
import java.util.Map;

class Tree{
	int data;
	Tree left,right,random;
	Tree(int d){
		data=d;
		left=null;
		right=null;
		random=null;
	}
}

class Solution{
    public Tree cloneTree(Tree tree){
        Map<Tree, Tree> nodeMap = new HashMap<>();
        Tree treeClone = buildTree(tree, nodeMap);
        assignRandomRef(tree, treeClone, nodeMap);
        return treeClone;
    }

    public Tree buildTree(Tree root, Map<Tree, Tree> nodeMap) {
        if(root==null) return null;
        Tree nodeClone = new Tree(root.data);
        nodeMap.put(root, nodeClone);
        nodeClone.left = buildTree(root.left, nodeMap);
        nodeClone.right = buildTree(root.right, nodeMap);
        return nodeClone;
    }

    public void assignRandomRef(Tree root1, Tree root2, Map<Tree, Tree> nodeMap) {
        if(root1==null) return;
        if(root1.random!=null) root2.random = nodeMap.get(root1.random);
        assignRandomRef(root1.left, root2.left, nodeMap);
        assignRandomRef(root1.right, root2.right, nodeMap);
    }
}