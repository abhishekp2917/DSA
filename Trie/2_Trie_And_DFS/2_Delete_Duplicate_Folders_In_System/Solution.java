import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    // Maps an identical subtree structure to a unique integer ID.
    // Instead of comparing entire subtrees repeatedly, we can compare their IDs.
    private final Map<String, Integer> serializationToId = new HashMap<>();

    // Stores how many times each subtree structure occurs in the Trie.
    // A subtree appearing more than once represents duplicate folders.
    private final Map<Integer, Integer> subtreeIdFrequency = new HashMap<>();

    // Generates a unique ID for every distinct subtree serialization.
    private int idCounter = 1;

    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {

        // First build the folder hierarchy as a Trie.
        Trie root = buildTrie(paths);

        // Compute a structural ID for every folder based on its complete subtree.
        assignSubtreeIds(root);

        List<List<String>> result = new ArrayList<>();

        // Traverse the Trie again and collect folders whose subtree is unique.
        collectUniqueFolders(root, new ArrayDeque<>(), result);

        return result;
    }

    private Trie buildTrie(List<List<String>> paths) {
        Trie root = new Trie();

        for (List<String> path : paths) {
            Trie node = root;

            // Each path represents a root-to-folder path.
            // Reuse existing Trie nodes when folders share the same parent.
            for (String folder : path) {
                node.map.putIfAbsent(folder, new Trie());
                node = node.map.get(folder);
            }
        }

        return root;
    }

    private int assignSubtreeIds(Trie node) {

        // A leaf has no children, so every leaf has the same empty subtree.
        // ID 0 represents this empty subtree.
        if (node.map.isEmpty()) {
            node.id = 0;
            return 0;
        }

        // Child order must be deterministic because a HashMap does not
        // guarantee iteration order. Sorting ensures identical folder
        // structures produce exactly the same serialization.
        List<String> folderNames = new ArrayList<>(node.map.keySet());
        Collections.sort(folderNames);

        StringBuilder serialization = new StringBuilder();

        // Build the subtree representation using each child's name
        // and the unique ID representing that child's entire subtree.
        for (String folderName : folderNames) {
            Trie child = node.map.get(folderName);
            int childId = assignSubtreeIds(child);

            // The combination of folder name + child subtree ID uniquely
            // identifies that child within the current subtree.
            serialization
                .append(folderName)
                .append("#")
                .append(childId)
                .append(",");
        }

        String serializedSubtree = serialization.toString();

        // Identical serializations receive the same ID, allowing us to
        // detect structurally identical subtrees using integer comparison.
        int subtreeId = serializationToId.computeIfAbsent(
            serializedSubtree,
            key -> idCounter++
        );

        node.id = subtreeId;

        // Count how many folders have this exact subtree structure.
        subtreeIdFrequency.put(
            subtreeId,
            subtreeIdFrequency.getOrDefault(subtreeId, 0) + 1
        );

        return subtreeId;
    }

    private void collectUniqueFolders(Trie node, Deque<String> path, List<List<String>> result) {

        // If this subtree occurs more than once, the current folder and
        // everything below it must be removed as a duplicate folder.
        if (subtreeIdFrequency.getOrDefault(node.id, 0) > 1) {
            return;
        }

        // The root represents no actual folder, so only add non-empty paths.
        if (!path.isEmpty()) {
            result.add(new ArrayList<>(path));
        }

        // DFS through the remaining unique subtree.
        for (String folderName : node.map.keySet()) {
            path.addLast(folderName);

            collectUniqueFolders(node.map.get(folderName), path, result);

            // Backtrack so the same deque can represent the next sibling path.
            path.removeLast();
        }
    }
}

class Trie {

    // Maps child folder name -> child Trie node.
    // This represents the folder hierarchy.
    Map<String, Trie> map = new HashMap<>();

    // Unique ID representing the complete subtree rooted at this folder.
    int id;
}