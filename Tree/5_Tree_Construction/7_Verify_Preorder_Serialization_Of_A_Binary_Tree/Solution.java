class Solution {

    private int index;

    public boolean isValidSerialization(String preorder) {
        String[] preorderArr = preorder.split(",");
        return traverse(preorderArr) && index==preorderArr.length;
    }

    private boolean traverse(String[] preorderArr) {
        if(index>=preorderArr.length) return false;
        if(preorderArr[index++].equals("#")) return true;
        return traverse(preorderArr) && traverse(preorderArr);
    }
}