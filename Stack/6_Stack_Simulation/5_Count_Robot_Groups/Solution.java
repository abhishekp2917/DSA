class Solution {
    
    public int countGroups(int[] position, int[] speed, int distance) {
        int n = position.length;
        int rightGrpSpeed = Integer.MAX_VALUE;
        int rightGrpPos = Integer.MAX_VALUE;
        int grpCount = 0;
        for(int i=n-1; i>=0; i--) {
            int currPos = position[i];
            int currSpeed = speed[i];
            if(currSpeed<=rightGrpSpeed && rightGrpPos-currPos>distance) {
                rightGrpSpeed = currSpeed;
                grpCount++;
            }
            rightGrpPos = currPos;
        }
        return grpCount;
    }
}

  



