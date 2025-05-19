import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Solution {

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();  // Total number of accounts

        // mailIdMap maps each unique email to an account index.
        // This helps us detect if the same email is already seen and which account it belongs to.
        // we choose account index and not account name because two diff account can have same name.
        HashMap<String, Integer> mailIdMap = new HashMap<>();

        // mergedAccountMap groups emails by the root representative account.
        // Key: root index of a group, Value: list of emails belonging to that group.
        HashMap<Integer, List<String>> mergedAccountMap = new HashMap<>();

        // Final answer list to store merged accounts
        List<List<String>> ans = new ArrayList<>();

        // DSU parent array initialization:
        // Each account initially points to itself, meaning every account is its own group.
        int[] parents = getParent(n);

        // Traverse all accounts to process each email
        for (int i = 0; i < n; i++) {
            List<String> account = accounts.get(i);

            // Skip the name at index 0, start from index 1 because index 0 has account name and not email 
            for (int j = 1; j < account.size(); j++) {
                String mailId = account.get(j);

                // If this email was seen before, we need to union the current account with the previous one
                if (mailIdMap.containsKey(mailId)) {
                    int accountU = mailIdMap.get(mailId);  // previously seen account index
                    int accountV = i;                      // current account index
                    merge(parents, accountU, accountV);    // merge the two accounts
                } else {
                    // First time seeing this email, map it to the current account
                    mailIdMap.put(mailId, i);
                }
            }
        }

        // After all unions are done, group emails by their root representative account
        for (String mailId : mailIdMap.keySet()) {
            int parentNode = mailIdMap.get(mailId);         // get the account this email initially belonged to
            int rootNode = getRootNode(parents, parentNode); // find the root of the account group using DSU

            // Add email to the list corresponding to the root account
            List<String> mergedAccount = mergedAccountMap.getOrDefault(rootNode, new ArrayList<>());
            mergedAccount.add(mailId);
            mergedAccountMap.put(rootNode, mergedAccount);
        }

        // Final step: format and add each merged group to the result
        for (Integer parentNode : mergedAccountMap.keySet()) {
            List<String> mergedAccount = mergedAccountMap.get(parentNode);

            // Sort the email list for lexicographical order
            Collections.sort(mergedAccount, (acc1, acc2) -> acc2.compareTo(acc1));

            // Retrieve the account name (original name from input using parentNode)
            String name = accounts.get(parentNode).get(0);

            // Add name at the beginning of the email list
            mergedAccount.add(name);
            Collections.reverse(mergedAccount); // reverse to put name first followed by sorted emails

            // Add to final result
            ans.add(mergedAccount);
        }

        return ans;
    }

    // Initializes parent array where each index points to itself
    private int[] getParent(int n) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        return parent;
    }

    // Merges two sets in DSU by pointing v's root to u's root
    private void merge(int[] parents, int u, int v) {
        int uRootNode = getRootNode(parents, u);
        int vRootNode = getRootNode(parents, v);
        parents[vRootNode] = uRootNode;
    }

    // Finds the root of a node using path compression for optimization
    private int getRootNode(int[] parents, int u) {
        if (parents[u] == u) return u;
        int parentNode = parents[u];
        int rootNode = getRootNode(parents, parentNode);
        parents[u] = rootNode; // Path compression to flatten tree
        return rootNode;
    }
}
