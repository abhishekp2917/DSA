import java.util.HashMap;
import java.util.TreeSet;

class NumberContainers {

    // indexMap:
    // Maps index → number stored at that index.
    //
    // Why needed?
    // Because when change(index, number) is called,
    // we must know the OLD number stored at that index
    // to remove it from numberMap.
    private HashMap<Integer, Integer> indexMap;

    // numberMap:
    // Maps number → sorted set of indices where this number exists.
    //
    // Why TreeSet?
    // Because find(number) must return
    // the SMALLEST index where this number appears.
    //
    // TreeSet keeps indices sorted.
    private HashMap<Integer, TreeSet<Integer>> numberMap;

    public NumberContainers() {

        indexMap = new HashMap<>();
        numberMap = new HashMap<>();
    }
    
    public void change(int index, int number) {

        // If index already had some number,
        // we must remove that index
        // from its old number's TreeSet.
        //
        // Why?
        // Because index now changes ownership.
        if (indexMap.containsKey(index)) {

            int currNumber = indexMap.get(index);

            numberMap.get(currNumber).remove(index);
        }

        // Update index → number mapping.
        indexMap.put(index, number);

        // Get TreeSet for this number.
        //
        // If not present, create new TreeSet.
        TreeSet<Integer> set =
            numberMap.getOrDefault(number,
                                   new TreeSet<>());

        // Add this index into number's set.
        set.add(index);

        // Put back into map (important if newly created).
        numberMap.put(number, set);
    }
    
    public int find(int number) {

        // If number exists AND has at least one index,
        // return smallest index.
        if (numberMap.containsKey(number)
                && !numberMap.get(number).isEmpty()) {

            return numberMap.get(number).first();
        }
        else
            return -1;
    }
}
