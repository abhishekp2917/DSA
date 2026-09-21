class Solution {

    public int findJudge(int n, int[][] trust) {

        // Array to keep track of the number of people trusting each person in the town
        // trustCount[i] stores the number of people trusting person i
        int[] trustCount = new int[n+1];

        // Array to keep track of whether a person is trusting anyone or not
        boolean[] isTrusting = new boolean[n+1];

        // Iterate over the trust array and update the trustCount and isTrusting arrays
        for(int i=0; i<trust.length; i++) {

            // Get the two people in the trust array
            int person1 = trust[i][0];
            int person2 = trust[i][1];

            // Update the isTrusting of person1 to true since person1 is trusting person2
            isTrusting[person1] = true;

            // Increment the trustCount of person2 since person2 is being trusted by person1
            trustCount[person2]++;
        }

        // Iterate over the trustCount array and check if there is a person who is trusted by all other people but is not trusting anyone
        for(int i=1; i<=n; i++) {

            // If the person is not trusting anyone and is trusted by all other people, return the person
            // n-1 people should trust the person for the person to be the judge
            if(!isTrusting[i] && trustCount[i]==n-1) return i;
        }

        // If there is no such person, return -1
        return -1;
    }
}