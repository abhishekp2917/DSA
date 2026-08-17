import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();

        // Maps every email to the account index where it was first seen.
        // If the same email appears in another account, those two accounts
        // belong to the same connected component and must be merged.
        HashMap<String, Integer> mailIdToAccountIdMap = new HashMap<>();

        // Maps the final DSU root account to all emails belonging to that
        // connected component.
        HashMap<Integer, List<String>> mergedAccountMap = new HashMap<>();

        List<List<String>> ans = new ArrayList<>();
        int[] parents = buildParents(n);

        // Build connected components of accounts using shared emails.
        for(int accountId=0; accountId<n; accountId++) {
            for(int mailIdx=1; mailIdx<accounts.get(accountId).size(); mailIdx++) {
                String mailId = accounts.get(accountId).get(mailIdx);

                if(mailIdToAccountIdMap.containsKey(mailId)) {
                    // This email already belongs to another account,
                    // so both accounts are connected and must be merged.
                    int prevAccountId = mailIdToAccountIdMap.get(mailId);
                    merge(parents, prevAccountId, accountId);
                }
                else {
                    // Remember the first account where this email appeared.
                    mailIdToAccountIdMap.put(mailId, accountId);
                }
            }
        }

        // Group every email under the root of its connected component.
        // We use the root account as the representative of the merged account.
        for(String mailId : mailIdToAccountIdMap.keySet()) {
            int accountId = mailIdToAccountIdMap.get(mailId);
            int rootAccountId = getRootNode(parents, accountId);

            List<String> mergedAccount =
                mergedAccountMap.getOrDefault(rootAccountId, new ArrayList<>());

            mergedAccount.add(mailId);
            mergedAccountMap.put(rootAccountId, mergedAccount);
        }

        // Sort emails as required by the problem and prepend the account name.
        for(Integer rootAccountId : mergedAccountMap.keySet()) {
            List<String> mergedAccount = mergedAccountMap.get(rootAccountId);

            Collections.sort(mergedAccount);

            // The root account represents the entire merged component,
            // so its name can be used as the merged account name.
            String name = accounts.get(rootAccountId).get(0);

            mergedAccount.add(0, name);
            ans.add(mergedAccount);
        }

        return ans;
    }

    private int[] buildParents(int n) {
        int[] parent = new int[n];

        // Initially every account is its own connected component.
        for(int i=0; i<n; i++) {
            parent[i] = i;
        }

        return parent;
    }

    private void merge(int[] parents, int u, int v) {
        // Always connect the roots rather than the original nodes.
        // This maintains the DSU tree structure correctly.
        int uRootNode = getRootNode(parents, u);
        int vRootNode = getRootNode(parents, v);

        parents[vRootNode] = uRootNode;
    }

    private int getRootNode(int[] parents, int u) {

        // A node whose parent is itself is the representative of
        // its connected component.
        if(parents[u]==u) return u;

        int parentNode = parents[u];

        // Recursively find the actual component representative.
        int rootNode = getRootNode(parents, parentNode);

        // Path compression:
        // directly connect u to the root so future lookups are faster.
        parents[u] = rootNode;

        return rootNode;
    }
}