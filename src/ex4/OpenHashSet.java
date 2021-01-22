package ex4;

public class OpenHashSet extends SimpleHashSet {

    /***
     * an array of a wrapper object of linked list.
     */
    private Bucket[] setArray;


    /*----=  Constructors  =-----*/

    /**
     * A default constructor. Constructs a new, empty table with default initial
     * capacity (16), upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        this.setArray = new Bucket[INITIAL_CAPACITY];
        this.higherCapacity = DEFAULT_HIGHER_CAPACITY;
        this.lowerCapacity = DEFAULT_LOWER_CAPACITY;
    }

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     *
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.setArray = new Bucket[INITIAL_CAPACITY];
        this.higherCapacity = upperLoadFactor;
        this.lowerCapacity = lowerLoadFactor;
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of
     * initial capacity (16), upper load factor (0.75), and lower load factor (0.25).
     *
     * @param data - Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data) {
        this.setArray = new Bucket[INITIAL_CAPACITY];
        this.higherCapacity = DEFAULT_HIGHER_CAPACITY;
        this.lowerCapacity = DEFAULT_LOWER_CAPACITY;
        for (String string : data) {
            this.add(string);
        }
    }


    /*----=  Methods  =-----*/


    @Override
    public int capacity() {
        return this.setArray.length;
    }

    @Override
    protected int clamp(int index) {
        return index & (this.capacity() - 1);
    }

    @Override
    protected void changeTableSize(int change) {
        int capacity = this.capacity();
        int newSize = 0;
        if (change == DOUBLE) {
            newSize = capacity * DOUBLE;
        } else {
            newSize = capacity / DOUBLE;
        }
        Bucket[] newSetArray = new Bucket[newSize];
        for (Bucket bucket : this.setArray) {
            if (bucket != null && !bucket.isEmpty()){
                int hash = bucket.getFirst().hashCode();
                int index = hash & (newSize - 1);
                newSetArray[index] = bucket;
            }
        }
        this.setArray = newSetArray;
    }

    @Override
    public boolean add(String newValue) {
        int index = this.clamp(newValue.hashCode());
        if (this.contains(newValue)) {
            return false;
        }
        float loadFactor = (float) (this.size() + 1) / (float) this.capacity();
        if (loadFactor > this.getUpperLoadFactor()) {
            this.changeTableSize(DOUBLE);

            return this.add(newValue);
        }
        if (this.setArray[index] == null) {
            this.setArray[index] = new Bucket();
        }
        return this.setArray[index].add(newValue);

    }

    @Override
    public boolean delete(String toDelete) {
        int index = this.clamp(toDelete.hashCode());
        if (!this.contains(toDelete)) {
            return false;
        }
        float loadFactor =(float) (this.size() - 1) / (float) this.capacity();
        int DividedCapacity = this.capacity()/2;
        if (loadFactor <= this.getLowerLoadFactor() && this.capacity() > 1 && DividedCapacity >= this.size()) {
            this.changeTableSize(DECREASE);
//            int newIndex = this.clamp(toDelete.hashCode());
            return this.delete(toDelete);
        }
        return this.setArray[index].remove(toDelete);

    }

    @Override
    public boolean contains(String searchVal) {
        int index = this.clamp(searchVal.hashCode());
        if (this.setArray[index] == null || this.setArray[index].isEmpty()) {
            return false;
        }
        return this.setArray[index].contains(searchVal);
    }

    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < this.capacity(); i++) {
            if (this.setArray[i] != null && !this.setArray[i].isEmpty()) {
                size += this.setArray[i].size();
            }
        }
        return size;
    }
}
