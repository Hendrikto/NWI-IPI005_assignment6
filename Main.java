package assignment6;

/**
 * Contains the main method.
 *
 * @author Pieter Koopman, Sjaak Smetsers
 * @author Hendrik Werner // s4549775
 * @author Jasper Haasdijk // s4449754
 */
public class Main {
    /**
     * Entry point
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] game = {
            1, 2, 4, 8,
            16, 5, 6, 3,
            9, 10, 7, 11,
            13, 14, 15, 12
        };
        SlidingGame s = new SlidingGame(game);
        Solver solver = new Solver(s);
        System.out.println(s);
        System.out.println(solver.solve());
    }
}
