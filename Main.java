package assignment6;

/**
 * Contains the main method.
 *
 * @author Pieter Koopman, Sjaak Smetsers
 * @author Hendrik Werner // s4549775
 * @author Jasper Haasdijk // s4449754
 */
public class Main {
    public static void main(String[] args) {
        int[] game = {1, 2, 3, 4, 5, 6, 9, 7, 8};

        SlidingGame s = new SlidingGame(game);
        System.out.println(s);
        Solver solver = new Solver(s);
        System.out.println(solver.solve());
    }
}
