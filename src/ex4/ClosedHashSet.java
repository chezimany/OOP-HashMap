package ex4;

public class ClosedHashSet extends SimpleHashSet {

    /**
     * an array representing the set
     */
    private String[] setArray;

    /**
     * an array representing flags to know which cells content was deleted
     */
    private boolean[] deletedCells;


    /*----=  Constructors  =-----*/

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     *
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.higherCapacity = upperLoadFactor;
        this.lowerCapacity = lowerLoadFactor;
        this.setArray = new String[INITIAL_CAPACITY];
        this.deletedCells = new boolean[INITIAL_CAPACITY];
    }

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        this.higherCapacity = DEFAULT_HIGHER_CAPACITY;
        this.lowerCapacity = DEFAULT_LOWER_CAPACITY;
        this.setArray = new String[INITIAL_CAPACITY];
        this.deletedCells = new boolean[INITIAL_CAPACITY];
    }

    public ClosedHashSet(java.lang.String[] data) {
        this.higherCapacity = DEFAULT_HIGHER_CAPACITY;
        this.lowerCapacity = DEFAULT_LOWER_CAPACITY;
        this.setArray = new String[INITIAL_CAPACITY];
        this.deletedCells = new boolean[INITIAL_CAPACITY];
        for (String string : data) {
            this.add(string);
        }
    }

    /*----=  Methods  =-----*/


    @Override
    public int capacity() {
        return this.setArray.length;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     * (see the exercise description for details)
     *
     * @param index - the index before clamping
     * @return - an index properly clamped
     */
    protected int clamp(int index) {
        return (index) & (this.capacity() - 1);
    }

    /**
     * The quadratic probing function of this closed hash set
     *
     * @param hash    - a hash code of a certain string
     * @param attempt - an int representing the attempt number to insert an
     *                element to the table.
     * @return - an index after probing.
     */
    protected static int quadraticProb(int hash, int attempt) {
        return (int) (hash + (attempt + Math.pow(attempt, 2)) / 2);
    }

    protected float loadFactor() {
        return (float) this.size() / (float) this.capacity();
    }

    @Override
    public boolean add(String newValue) {
        if (this.contains(newValue)) {
            return false;
        }
        float loadFactor = ((float) this.size() + 1f) / (float) this.capacity();
        int code = newValue.hashCode();
        for (int i = 0; i < this.capacity(); i++) {
            int index = clamp(quadraticProb(code, i));
            if (this.setArray[index] == null) {
                if (loadFactor > this.higherCapacity) {
                    this.changeTableSize(DOUBLE);
                    return this.add(newValue);
                }
                this.setArray[index] = newValue;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String toDelete) {
        if (!this.contains(toDelete)) {
            return false;
        }
        int code = toDelete.hashCode();
        for (int i = 0; i < this.capacity(); i++) {
            int index = this.clamp(quadraticProb(code, i));
            if (this.setArray[index] != null && this.setArray[index].equals(toDelete)) {
                this.setArray[index] = null;
                this.deletedCells[index] = true;
                if (this.loadFactor() <= this.getLowerLoadFactor() && this.capacity() > DECREASE) {
                    this.changeTableSize(DECREASE);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < this.capacity(); i++) {
            if (this.setArray[i] != null) {
                size++;
            }
        }
        return size;
    }


    @Override
    protected void changeTableSize(int change) {
        int newSize = 0;
        if (change == DOUBLE) {
            newSize = this.capacity() * DOUBLE;
        } else {
            newSize = this.capacity() / DOUBLE;
        }
        String[] newSetArray = new String[newSize];
        boolean[] newDeleted = new boolean[newSize];
        for (int i = 0; i < this.capacity(); i++) {  // go through the sets cells
            if (this.setArray[i] != null) {  // the cell contains a string
                int code = this.setArray[i].hashCode();
                for (int j = 0; j < newSize; j++) {  // look for the first cell available with the code
                    // insert the string and break the loop
                    int probe = quadraticProb(code, j);
                    int index = (probe) & (newSize - 1);
                     if (newSetArray[index] == null){
                        newSetArray[index] = this.setArray[i];
                        break;
                    }


                }
            }
        }
        this.setArray = newSetArray;
        this.deletedCells = newDeleted;
    }

    @Override
    public boolean contains(String searchVal) {
        int attempt = 0;
        while (attempt < this.capacity()) {
            int hash = searchVal.hashCode();
            int code = quadraticProb(hash, attempt);
            int index = clamp(code);
            if (this.setArray[index] != null && this.setArray[index].equals(searchVal)) {
                return true;
            }
            if ((this.setArray[index] == null && this.deletedCells[index]) || (this.setArray[index] != null
                    && !this.setArray[index].equals(searchVal))) {
                attempt++;
            } else if (this.setArray[index] == null && !this.deletedCells[index]) {
                return false;
            }

        }
        return false;
    }
}
