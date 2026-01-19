public class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pair<?, ?> other = (Pair<?, ?>) obj;
        return (key == null ? other.key == null : key.equals(other.key)) &&
               (value == null ? other.value == null : value.equals(other.value));
    }

    public int hashCode() {
        int result = (key != null) ? key.hashCode() : 0;
        result = 31 * result + ((value != null) ? value.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Pair{key='" + key + "', value=" + value + "}";
    }
}
