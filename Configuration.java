package assignment6;

import java.util.Collection;

/**
 * An interface for representing nodes in a state space.
 *
 * @author Sjaak Smetsers
 * @author Hendrik Werner // s4549775
 * @author Jasper Haasdijk // s4449754
 * @version 1.3
 * @date 07-03-2016
 */
public interface Configuration extends Comparable<Configuration> {
    /**
     * To obtain the successors for a specific configuration
     *
     * @return a collection of configurations containing the successors
     */
    public Collection<Configuration> successors();

    /**
     * For marking final / solution configurations.
     *
     * @return whether the configuration is the solution
     */
    public boolean isSolution();

    /**
     * Get a heuristic for this configuration.
     *
     * @return s heuristic for this configuration
     */
    public int getHeuristic();
}
