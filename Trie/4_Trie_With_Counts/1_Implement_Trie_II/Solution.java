public class Solution {

    PrefixTree prefixTree;

    public Solution() {
        prefixTree = new PrefixTree();
    }

    public void insert(String word) {
        PrefixTree root = prefixTree;
        for(char ch : word.toCharArray()) {
            int idx = ch-'a';
            if(root.map[idx]==null) root.map[idx] = new PrefixTree();
            root = root.map[idx];
            root.prefixCount++;
        }
        root.endCount++;
    }

    public int countWordsEqualTo(String word) {
        PrefixTree root = prefixTree;
        for(char ch : word.toCharArray()) {
            int idx = ch-'a';
            if(root.map[idx] == null) return 0;
            root = root.map[idx];
        }
        return root.endCount;
    }

    public int countWordsStartingWith(String word) {
        PrefixTree root = prefixTree;
        for(char ch : word.toCharArray()) {
            int idx = ch-'a';
            if(root.map[idx] == null) return 0;
            root = root.map[idx];
        }
        return root.prefixCount;
    }

    public void erase(String word) {
        int n = word.length();
        PrefixTree[] path = new PrefixTree[n];
        PrefixTree root = prefixTree;
        for(int i=0; i<n; i++) {
            int idx = word.charAt(i)-'a';
            if(root.map[idx] == null) return;
            root = root.map[idx];
            path[i] = root;
        }
        if(root.endCount==0) return;
        root.endCount--;
        for(PrefixTree node : path) node.prefixCount--;
    }

}

class PrefixTree {
    PrefixTree[] map;
    int endCount;
    int prefixCount;
    PrefixTree() {
        map = new PrefixTree[26];
    }
}
 