class Solution {
    public static int countSubs(String s) {
        int n = s.length();
        int count = 0;
        PrefixTree prefixTree = new PrefixTree();
        for(int start=0; start<n; start++) {
            PrefixTree root = prefixTree;
            for(int i=start; i<n; i++) {
                int idx = s.charAt(i)-'a';
                if(root.map[idx]==null) {
                    root.map[idx] = new PrefixTree();
                    count++;
                }
                root = root.map[idx];
            }
        }
        return count;
    }
}

class PrefixTree {
    PrefixTree[] map;
    PrefixTree() {
        map = new PrefixTree[26];
    }
}