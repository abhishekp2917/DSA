class Solution {
    
    public void subsequence(String str, int ptr, StringBuilder subSeq) {
        if(ptr>=str.length()) {
            System.out.println(subSeq.toString());
            return; 
        }
        subSeq.append(str.charAt(ptr));
        subsequence(str, ptr+1, subSeq);
        subSeq.deleteCharAt(subSeq.length()-1);
        subsequence(str, ptr+1, subSeq);
    }
}