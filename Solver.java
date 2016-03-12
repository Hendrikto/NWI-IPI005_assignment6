package assignment6;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A class that implements a breadth-first search algorithm for finding the
 * Configurations for which the isSolution predicate holds
 *
 * @author Pieter Koopman, Sjaak Smetsers
 * @author Hendrik Werner // s4549775
 * @author Jasper Haasdijk // s4449754
 * @version 1.4
 * @date 07-03-2016
 */
public class Solver {
    // A queue for maintaining graphs that are not visited yet.
    private final Queue<Node<Configuration>> toExamine;

    public Solver(Configuration g) {
        this.toExamine = new LinkedList<>();
        this.toExamine.add(new Node<>(null, g));
    }

    /**
     * A skeleton implementation of the solver
     *
     * @return a string representation of the solution
     */
    public String solve() {
        while (!toExamine.isEmpty()) {
            Node<Configuration> next = toExamine.remove();
            if (next.getItem().isSolution()) {
                System.out.println(next);
                return "Success!";
            } else {
                for (Configuration succ : next.getItem().successors()) {
                    toExamine.add(new Node<>(next, succ));
                }
            }
        }
        return "Failure!";
    }
}
