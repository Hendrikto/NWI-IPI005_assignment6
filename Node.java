package assignment6;

/**
 * For maintaining lists of T-elements enabling a structure suited for backwards
 * traversal
 *
 * @author Pieter Koopman, Sjaak Smetsers
 * @author Hendrik Werner // s4549775
 * @author Jasper Haasdijk // s4449754
 * @version 1.3
 * @date 07-03-2016
 */
public class Node<T extends Configuration> implements Comparable<Node<T>> {
    // the data field
    private final T item;
    // a reference to the predecessor
    private final Node<T> previous;

    /**
     * A constructor that initializes each node with the specified values
     *
     * @param from the node preceding the current node
     * @param item the initial data item
     */
    public Node(Node<T> from, T item) {
        this.previous = from;
        this.item = item;
    }

    /**
     * a getter for the item
     *
     * @return item
     */
    public T getItem() {
        return item;
    }

    /**
     * a getter for the predecessor
     *
     * @return previous
     */
    public Node<T> getPrevious() {
        return previous;
    }

    /**
     * determines the length of the current list
     *
     * @return the length as an integer
     */
    public int length() {
        int length = 1;
        Node<T> prev = previous;
        while (prev != null) {
            prev = prev.previous;
            length++;
        }
        return length;
    }

    /**
     * Compare this node to another.
     *
     * @param n the Node to compare to
     * @return the result of comparing the item of this node with the other's
     */
    @Override
    public int compareTo(Node<T> n) {
        return this.item.compareTo(n.item);
    }

    /**
     * Get a String containing a step number plus the respective configuration
     * from the begin node up to this Node.
     *
     * @return a String containing a step number plus the respective
     * configuration from the begin node up to this Node
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.previous != null) {
            sb.append(this.previous.toString());
        }
        return sb.append(String.format(
            "step %d:\n%s",
            this.length(),
            this.item.toString()
        )).toString();
    }
}
