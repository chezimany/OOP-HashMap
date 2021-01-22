package ex4;

import java.util.Collection;

public class CollectionFacadeSet extends java.lang.Object implements SimpleSet {

    protected java.util.Collection<java.lang.String> collection;

    /*----=  Constructor  =-----*/

    CollectionFacadeSet(java.util.Collection<java.lang.String> collection) {
        this.collection = collection;
    }


    /*----=  Methods  =-----*/


    public Collection<String> getCollection() {
        return collection;
    }

    @Override
    public boolean add(String newValue) {
        if (this.contains(newValue)) {
            return false;
        }
        return this.collection.add(newValue);
    }

    @Override
    public boolean contains(String searchVal) {
        return this.collection.contains(searchVal);
    }

    @Override
    public boolean delete(String toDelete) {
        if (this.collection instanceof SimpleHashSet) {
            return ((SimpleHashSet) this.collection).delete(toDelete);
        } else {
            return this.collection.remove(toDelete);
        }
    }

    @Override
    public int size() {
        return this.collection.size();
    }
}
