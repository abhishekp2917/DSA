import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> partitions = new ArrayList<>();
        recurrsion(s, 0, partitions, new ArrayList<>());
        return partitions;
    }

    private void recurrsion(String s, int start, List<List<String>> partitions, List<String> partition) {
        if(start==s.length()) {
            partitions.add(new ArrayList<>(partition));
            return;
        }
        for(int len=1; start+len<=s.length(); len++) {
            int end = start+len-1;
            if(isPalindrome(s, start, end)) {
                partition.add(s.substring(start, end+1));
                recurrsion(s, end+1, partitions, partition);
                partition.remove(partition.size()-1);
            }
        }
    }

    private boolean isPalindrome(String s, int start, int end) {
        while(start<end) {
            if(s.charAt(start++)!=s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }
}