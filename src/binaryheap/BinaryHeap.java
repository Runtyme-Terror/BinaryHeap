package binaryheap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Queue;

/**
 * This class implements a binary heap data structure by extending the
 * ArrayList class.
 * It also implements the java.util.Queue interface so that it can be
 * used with the framework problem solver.
 * @author tcolburn
 * @param <E> the type of element stored on this binary heap
 */
public class BinaryHeap<E> extends ArrayList<E> implements Queue<E> {

    /**
     * Creates an empty binary heap with a given capacity and comparator.
     * @param capacity The initial size of the underlying ArrayList object.
     * @param comp A comparator object for comparing keys of binary heap elements.
     */
    public BinaryHeap(int capacity, Comparator<E> comp) {
        super(capacity+1);
        init();
        this.comp = comp;
    }

    /**
     * Getter for the comparator for this binary heap.
     * @return the comparator for this binary heap
     */
    public Comparator<E> getComp() {
        return comp;
    }

    /**
     * Initializes the underlying ArrayList object for use as a binary heap.
     * A null object is added to location 0, which is not used by the heap.
     */
    private void init() {
        add(0, null);
    }

    /**
     * Clears this binary heap by clearing and initializing the underlying
     * ArrayList object.
     */
    @Override
    public void clear() {
        super.clear();
        init();
    }

    /**
     * Returns the current size of this binary heap.  Since the first location 
     * (index 0) of the underlying ArrayList object is not used, the size of the 
     * binary heap is one less than the size of the ArrayList object.
     * @return The binary heap's current size. 
     */
    @Override
    public int size() {
        return super.size()-1;
    }

    /**
     * Returns true if this binary heap is empty, that is, its size is zero.
     * @return Whether this binary heap is empty.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Adds a new element to this binary heap.  At the end of the add,
     * the heap has one more element and the heap property is maintained.
     * @param element The element to add
     * @return true.  In accordance with the Collection interface, returns
     * true since duplicate elements are allowed.
     */
    @Override
    public boolean add(E element) {
        super.add(element);        
        int index = lastIndexOf(element);
        int parentIndex = index / 2;
        while ((parentIndex >= 1) &&
                (comp.compare(element, get(parentIndex)) < 0)) {
            set(index, get(parentIndex));
            set((parentIndex), element);
            index = lastIndexOf(element);
            parentIndex = index / 2;
        }
	
        return true;
    }

    /**
     * Removes an element from the root of this binary heap.  After removal,
     * the heap has one less element and the heap property is restored.
     * This method does not override any method in the ArrayList class 
     * (note absence of an index parameter).
     * However, it implements a method in the Queue interface.
     * @return The removed element
     */
    @Override
    public E remove() {
        E oldElement = get(1);
        E currElement = super.remove(size());       
        int parent = 1;
        int leftChild = 2;
        int rightChild = 3;
        boolean done = false;
        E leftElement;
        E rightElement;
        
        
        if (size() >= rightChild) {
                    leftElement = get(leftChild);
                    rightElement = get(rightChild);
                } else if (size() >= leftChild) {
                    leftElement = get(leftChild);
                    rightElement = null;
                    done = true;
                    
                    if (comp.compare(currElement, leftElement) > 0) {
                        set(parent, leftElement);
                        set(leftChild, currElement);
                        
                    }
                } else {
                    leftElement = null;
                    rightElement = null;
                    done = true;
                }
        
        if (isEmpty()) {
            return oldElement;
        }
        set(parent, currElement);
        while (!done && ((comp.compare(currElement, leftElement) > 0) || (comp.compare(currElement, rightElement) > 0))) {
            
        if (leftElement != null && rightElement != null) {
            if (size() <= leftChild) {
                    done = true;
                    continue;
                }
            if (comp.compare(get(leftChild), get(rightChild)) <= 0) {
                set(parent, leftElement);
                set(leftChild, currElement);
                parent = leftChild;
                leftChild = (parent * 2);
                rightChild = (parent * 2) + 1;
                if (size() <= leftChild) {
                    done = true;
                    continue;
                }
                leftElement = get(leftChild);
                rightElement = get(rightChild);
            } else {
                set(parent, rightElement);
                set(rightChild, currElement);
                parent = rightChild;
                leftChild = (parent * 2);
                rightChild = (parent * 2) + 1; 
                if (size() <= leftChild) {
                    done = true;
                    continue;
                }
                leftElement = get(leftChild);
                rightElement = get(rightChild);
            }
           
        } else if (rightElement == null) {
                set(parent, leftElement);
                set(leftChild, currElement);
                parent = leftChild;
                leftChild = (parent * 2);
                rightChild = (parent * 2) + 1;
                if (size() <= leftChild) {
                    done = true;
                    continue;
                }
                leftElement = get(leftChild);
                rightElement = get(rightChild);
        }
   
        }
        	
        return oldElement;    
    }

    /**
     * A Comparator object used to compare binary heap elements during its
     * add and remove operations.
     */
    private final Comparator<E> comp;

    /*
    The following are required to complete the implementation of the Queue<E> 
    interface. They are not used in the test.
    */
    
    @Override
    public boolean offer(E e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public E poll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public E element() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public E peek() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}