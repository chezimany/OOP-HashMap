package ex4;

public abstract class SimpleHashSet extends java.lang.Object implements SimpleSet {

    /**
     * Describes the higher load factor of a newly created hash set
     */
    protected static float DEFAULT_HIGHER_CAPACITY = 0.75f;

    /**
     * Describes the lower load factor of a newly created hash set
     */
    protected static float DEFAULT_LOWER_CAPACITY = 0.25f;

    /**
     * Describes the capacity of a newly created hash set
     */
    protected static int INITIAL_CAPACITY = 16;

    /**
     * the higher bound of the specific hash set
     */
    protected float higherCapacity;

    /**
     * the lower bound of the specific hash set
     */
    protected float lowerCapacity;

    /**
     * a magic number for increasing the table size
     */
    protected final static int DOUBLE = 2;

    /**
     *  a magic number for decreasing the table size
     */
    protected final static int DECREASE = 1;

    private String[] stringArray;


    /*----=  Constructors  =-----*/

    /**
     * Constructs a new hash set with the default capacities given in
     * DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY
     */
    public SimpleHashSet(){
        this.higherCapacity = DEFAULT_HIGHER_CAPACITY;
        this.lowerCapacity = DEFAULT_LOWER_CAPACITY;
        this.stringArray = new String[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     * @param upperLoadFactor - the upper load factor before rehashing
     * @param lowerLoadFactor - the lower load factor before rehashing
     */
    public SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.higherCapacity = upperLoadFactor;
        this.lowerCapacity = lowerLoadFactor;
        this.stringArray = new String[INITIAL_CAPACITY];
    }

    /*----=  Methods  =-----*/

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public abstract int capacity();

    /**
     * Clamps hashing indices to fit within the current table capacity
     * (see the exercise description for details)
     * @param index - the index before clamping
     * @return - an index properly clamped
     */
    protected abstract int clamp(int index);

    /**
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor(){
        return this.lowerCapacity;
    }

    /**
     * @return - The higher load factor of the table.
     */
    protected float getUpperLoadFactor(){
        return this.higherCapacity;
    }

    /**
     * change the sets size
     * @param change - if equals 2 the tables size would double, otherwise it would be cut in half
     */
    protected abstract void changeTableSize(int change);

}
