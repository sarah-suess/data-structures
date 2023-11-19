import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Sarah Suess
 * @version 1.0
 * @userid ssuess3
 * @GTID 903687080
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if ((index < 0) || (index > size)) {
            throw new IndexOutOfBoundsException("Index is less than zero or greater than the list size.");
        } else if (data == null) {
            throw new IllegalArgumentException("The data can't be null.");
        } else {
            if (index == 0) {
                if (head == null) {
                    CircularSinglyLinkedListNode<T> newData = new CircularSinglyLinkedListNode<T>(data);
                    head = newData;
                    head.setNext(head);
                } else {
                    CircularSinglyLinkedListNode<T> newFrontNode = new CircularSinglyLinkedListNode<T>(head.getData());
                    head.setData(data);
                    newFrontNode.setNext(head.getNext());
                    head.setNext(newFrontNode);
                }
            } else {
                CircularSinglyLinkedListNode<T> newData = new CircularSinglyLinkedListNode<T>(data);
                CircularSinglyLinkedListNode<T> holdNode = head;
                for (int i = 0; i < (index - 1); i++) {
                    holdNode = holdNode.getNext();
                }
                newData.setNext(holdNode.getNext());
                holdNode.setNext(newData);
            }
            size++;
        }

    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        // edge case when size = 1
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        } else {
            addAtIndex(0, data);
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        } else if (head == null) {
            CircularSinglyLinkedListNode<T> newData = new CircularSinglyLinkedListNode<T>(data);
            head = newData;
            head.setNext(head);
            size++;
        } else if (size == 1) {
            CircularSinglyLinkedListNode<T> newData = new CircularSinglyLinkedListNode<T>(data);
            head.setNext(newData);
            newData.setNext(head);
            size++;
        } else {
            CircularSinglyLinkedListNode<T> newFrontNode = new CircularSinglyLinkedListNode<T>(head.getData());
            head.setData(data);
            newFrontNode.setNext(head.getNext());
            head.setNext(newFrontNode);
            head = newFrontNode;
            size++;
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if ((index < 0 || index >= size)) {
            throw new IndexOutOfBoundsException("Index can not be less than zero or more than items in list");
        }
        if (size == 1 && index == 0) {
            return removeFromFront();
        } else if (index == 0){
            return removeFromFront();
        }else {
            CircularSinglyLinkedListNode<T> holdNode = head;
            for (int i = 0; i < (index - 1); i++) {
                holdNode = holdNode.getNext();
            }
            T oldData = holdNode.getNext().getData();
            holdNode.setNext(holdNode.getNext().getNext());
            size--;
            return oldData;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        // maybe edge case when size = 1
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty");
        } else if (size == 1) {
            T oldData = head.getData();
            size = 0;
            head = null;
            return oldData;
        } else {
            T oldData = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return oldData;
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty");
        } else {
            return removeAtIndex(size - 1);
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index can't be less than zero or more than num of elements in list");
        } else {
            if (index == 0) {
                return head.getData();
            } else {
                CircularSinglyLinkedListNode<T> holdNode = head;
                for (int i = 0; i < index; i++) {
                    holdNode = holdNode.getNext();
                }
                return holdNode.getData();
            }
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        } else if (head != null) {
            CircularSinglyLinkedListNode<T> holdNode = head;
            CircularSinglyLinkedListNode<T> prevNode = null;
            CircularSinglyLinkedListNode<T> lastNode = null;
            if (head.getData().equals(data)) {
                lastNode = head;
            }
            while (holdNode.getNext() != head) {
                if (holdNode.getNext().getData().equals(data)) {
                    prevNode = holdNode;
                    lastNode = holdNode.getNext();
                }
                holdNode = holdNode.getNext();
            }

            if (lastNode != null) {
                if (lastNode == head) {
                    if (size == 1) {
                        T oldData = head.getData();
                        head = null;
                        size--;
                        return oldData;
                    } else {
                        T oldData = head.getData();
                        head = head.getNext();
                        if (prevNode != null) {
                            prevNode.setNext(head);
                        }
                        lastNode = null;
                        size--;
                        return oldData;
                    }
                } else {
                    prevNode.setNext(lastNode.getNext());
                    T oldData = lastNode.getData();
                    lastNode = null;
                    size--;
                    return oldData;
                }
            } else {
                throw new NoSuchElementException("Data could not be found.");
            }  
        } else {
            throw new NoSuchElementException("Data could not be found.");  
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] dataArray;
        dataArray = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> holdNode = head;
        for (int i = 0; i < dataArray.length; i++) {
            dataArray[i] = holdNode.getData();
            holdNode = holdNode.getNext();
        }
        return dataArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
