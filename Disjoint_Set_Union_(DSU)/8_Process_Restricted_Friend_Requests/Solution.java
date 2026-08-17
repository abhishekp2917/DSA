import java.util.Arrays;

class Solution {
    public boolean[] friendRequests(int n, int[][] restrictions, int[][] requests) {
        int q = requests.length;
        boolean[] result = new boolean[q];
        int[] parents = new int[n];
        int[] sizes = new int[n];

        // Initially, every person belongs to their own friend group.
        // DSU will maintain the groups formed by accepted friend requests.
        for(int i=0; i<n; i++) parents[i] = i;
        Arrays.fill(sizes, 1);

        for(int i=0; i<q; i++) {
            int friendU = requests[i][0];
            int friendV = requests[i][1];

            // Find the current friend-group representatives of both people.
            int uRootFriend = findRootFriend(parents, friendU);
            int vRootFriend = findRootFriend(parents, friendV);

            // They are already in the same group, so accepting this request
            // does not change the group and cannot violate any new restriction.
            if(uRootFriend==vRootFriend) {
                result[i] = true;
                continue;
            }

            // Before merging the two groups, verify that doing so would not
            // make any restricted pair belong to the same friend group.
            if(!canBecomeFriends(parents, restrictions, uRootFriend, vRootFriend)) {
                continue;
            }

            // The merge is safe, so permanently combine the two groups.
            makeFriends(parents, sizes, uRootFriend, vRootFriend);
            result[i] = true;
        }

        return result;
    }


    private void makeFriends(int[] parents, int[] sizes,
                             int uRootFriend, int vRootFriend) {

        int uSize = sizes[uRootFriend];
        int vSize = sizes[vRootFriend];

        // Attach the smaller tree below the larger tree.
        // This keeps the DSU tree shallow and makes find operations faster.
        if(uSize<vSize) {
            parents[vRootFriend] = uRootFriend;
            sizes[uRootFriend] += vSize;
        }
        else {
            parents[uRootFriend] = vRootFriend;
            sizes[vRootFriend] += uSize;
        }
    }


    private boolean canBecomeFriends(int[] parents, int[][] restrictions,
                                     int uRootFriend, int vRootFriend) {

        // A restriction [a,b] is violated only if a and b become members
        // of the same component after this proposed merge.
        //
        // Before merging, the only new connections created are between
        // the U component and the V component. Therefore, we only need
        // to check whether a restriction has one endpoint in each component.
        for(int[] restriction : restrictions) {
            int aRootFriend = findRootFriend(parents, restriction[0]);
            int bRootFriend = findRootFriend(parents, restriction[1]);

            // The restriction would become violated if the two restricted
            // people currently belong to the two groups we are about to merge.
            if((aRootFriend==uRootFriend && bRootFriend==vRootFriend) ||
               (aRootFriend==vRootFriend && bRootFriend==uRootFriend)) {
                return false;
            }
        }

        return true;
    }


    private int findRootFriend(int[] parents, int friend) {

        // A node whose parent is itself is the representative of its group.
        if(parents[friend]==friend) return friend;

        // Path compression:
        // directly connect this person to the group representative so
        // future find operations become faster.
        return parents[friend] =
            findRootFriend(parents, parents[friend]);
    }
}