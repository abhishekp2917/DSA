import java.util.HashMap;

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
        // declare hashmap, whose key will be node of the given tree and value will be its cloned node
        HashMap<Tree, Tree> map = new HashMap<>();
        // build clone tree whithout random ref as null
        Tree cloneTree = buildTree(tree, map);
        // once clone tree is build, assign the random ref
        assignRandomRef(tree, cloneTree, map);
        return cloneTree;
    }

    // function to clone tree
    public Tree buildTree(Tree root, HashMap<Tree, Tree> map) {
        if(root==null) return null;
        Tree temp = new Tree(root.data);
        // in hashmap, store the node of the main tree mapped to its cloned counterpart
        map.put(root, temp);
        temp.left = buildTree(root.left, map);
        temp.right = buildTree(root.right, map);
        return temp;
    }

    // function to assign the random ref
    // traverse the main tree and if the random ref is not null, then for that node cloned counterpart,
    // assign it's random ref. to get the cloned node random ref node, use hashmap 
    public void assignRandomRef(Tree root1, Tree root2, HashMap<Tree, Tree> map) {
        if(root1==null) return;
        if(root1.random!=null) root2.random = map.get(root1.random);
        assignRandomRef(root1.left, root2.left, map);
        assignRandomRef(root1.right, root2.right, map);
    }
}