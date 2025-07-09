import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the underlying data structure and methods that manipulate the data for the in-memory database.
 * The key-value store is implemented using a HashMap, so the set(), get(), unset() methods have O(1) average-case
 * time complexity. To effectively retrieve the number of key-value pairs equal to a given value, another HashMap
 * is used, so the numEqualTo() method also has average-case time complexity O(1).
 */
public class Database {
    public static final int DB_GOOD = 0;
    public static final int DB_NOT_FOUND = 1;
    public static final int DB_ERROR = 2;

    private final Map<String, String> keyToValue = new HashMap<>();
    private final Map<String, Integer> valueToCount = new HashMap<>();

    // Set a key-value pair in the database.
    public int dbSet(String key, String value) {
        decOldValue(key);
        keyToValue.put(key, value);
        valueToCount.put(value, valueToCount.getOrDefault(value, 0) + 1);
        return DB_GOOD;
    }

    // Erase a key-value pair with the given key.
    public int dbUnset(String key) {
        if (!keyToValue.containsKey(key)) {
            return DB_NOT_FOUND;
        }
        decOldValue(key);
        keyToValue.remove(key);
        return DB_GOOD;
    }

    // Get a value associated with a given key.
    public int dbGet(String key, StringBuilder value) {
        if (!keyToValue.containsKey(key)) {
            return DB_NOT_FOUND;
        }
        value.append(keyToValue.get(key));
        return DB_GOOD;
    }

    // Get the number of entries that have a specific value.
    public int dbNumEqualTo(String value, int[] count) {
        count[0] = valueToCount.getOrDefault(value, 0);
        return DB_GOOD;
    }

    // Decrease the value-count by one, if the count is 0, delete the value.
    private int decOldValue(String key) {
        String oldValue = keyToValue.get(key);
        if (oldValue != null) {
            int count = valueToCount.getOrDefault(oldValue, 0);
            if (count <= 1) {
                valueToCount.remove(oldValue);
            } else {
                valueToCount.put(oldValue, count - 1);
            }
        }
        return DB_GOOD;
    }
}
