package ex4;

import java.security.KeyStore;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


public class Bucket {


    private LinkedList<String> bucket;

    /*----=  Constructor  =-----*/

    public Bucket() {
        this.bucket = new LinkedList<String>();
    }

    /*----=  Methods  =-----*/

    /**
     * Appends the specified element to the end of this list.
     *
     * @param string - element to be appended to this list
     * @return true if managed to add the new string
     */
    public boolean add(String string) {
            return this.bucket.add(string);
    }

    /**
     * remove the last value from the list
     *
     * @param value - the value to be removed
     * @return true if removed, false otherwise
     */
    public boolean remove(String value) {
        return this.bucket.remove(value);
    }

    /**
     * @return the number of elements in this list
     */
    public int size() {
        return this.bucket.size();
    }

    /**
     * Returns true if this list contains the specified element
     *
     * @param string - string whose presence in this list is to be tested
     * @return - true if this list contains the specified element
     */
    public boolean contains(String string) {
        return this.bucket.contains(string);

    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index - index of the element to return
     * @return - the element at the specified position in this list
     */
    public String get(int index) {
        return this.bucket.get(index);
    }

    /**
     * Returns the head (first element) of this list.
     * @return the head of this list
     */
    public String getFirst(){
        return this.bucket.getFirst();
    }

    public boolean isEmpty(){
        return this.bucket.isEmpty();
    }


}
