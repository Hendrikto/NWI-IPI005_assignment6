package assignment6;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Pieter Koopman, Sjaak Smetsers
 * @author Hendrik Werner // s4549775
 * @author Jasper Haasdijk // s4449754
 * @version 1.3
 * @date 07-03-2016 A template implementation of a sliding game also
 * implementing the Graph interface
 */
public class SlidingGame implements Configuration {
    public static final int N = 4, SIZE = N * N, HOLE = SIZE;
    /**
     * The board is represented by a 2-dimensional array; the position of the
     * hole is kept in 2 variables holeX and holeY
     */
    private final int[][] board;
    private int holeX, holeY;
    private int heuristic;

    /**
     * A constructor that initializes the board with the specified array
     *
     * @param start: a one dimensional array containing the initial board. The
     * elements of start are stored row-wise.
     */
    public SlidingGame(int[] start) {
        this.board = new int[N][N];

        assert start.length == N * N : "Length of specified board incorrect";

        for (int p = 0; p < start.length; p++) {
            if (start[p] < 1 || start[p] > SIZE) {
                throw new Error(String.format("Invalid number %d at position %d.", start[p], p));
            }
            this.board[p % N][p / N] = start[p];
            if (start[p] == HOLE) {
                this.holeX = p % N;
                this.holeY = p / N;
            }
        }
        this.heuristic = this.calculateHeuristic();
    }

    /**
     * Copy constructor.
     *
     * @param s the SlidingGame to copy
     */
    public SlidingGame(SlidingGame s) {
        this.board = new int[N][N];
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                this.board[x][y] = s.board[x][y];
            }
        }
        this.holeX = s.holeX;
        this.holeY = s.holeY;
        this.heuristic = s.heuristic;
    }

    /**
     * Converts a board into a printable representation. The hole is displayed
     * as a space
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                int puzzel = this.board[col][row];
                buf.append(String.format("%3s", puzzel == HOLE ? "" : puzzel));
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    /**
     * a standard implementation of equals checking whether 2 boards are filled
     * in exactly the same way
     *
     * @param o the board to compare to
     * @return a boolean indicating equality
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        } else {
            SlidingGame other_puzzle = (SlidingGame) o;
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (this.board[col][row] != other_puzzle.board[col][row]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * Generate a hash for this configuration.
     *
     * @return a hash for this configuration
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                hash += this.board[x][y] * Math.pow(31, x + y * N);
            }
        }
        return hash;
    }

    /**
     * Check whether the current configuration is a solution.
     *
     * @return whether the current configuration is a solution
     */
    @Override
    public boolean isSolution() {
        int i = 1;
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (this.board[x][y] != i++) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get a collection of successors for this configuration.
     *
     * @return a collection of successors for this configuration
     */
    @Override
    public Collection<Configuration> successors() {
        Collection<Configuration> successors = new LinkedList<>();
        SlidingGame candidate;
        for (Direction d: Direction.values()) {
            if (withinBounds(this.holeX + d.GetDX(), this.holeY + d.GetDY())) {
                candidate = new SlidingGame(this);
                candidate.move(d);
                successors.add(candidate);
            }
        }
        return successors;
    }

    /**
     * Compare this configuration to another.
     *
     * @param g the configuration to compare to
     * @return [heuristic of this] - [heuristic of g]
     */
    @Override
    public int compareTo(Configuration g) {
        return this.heuristic - g.getHeuristic();
    }

    /**
     * Accessor for the heuristic of this configuration.
     *
     * @return the heuristic of this configuration
     */
    @Override
    public int getHeuristic() {
        return this.heuristic;
    }

    /**
     * Check whether given x and y-coordinates are within the boundaries of the
     * board.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return whether the coordinates fall within the boundaries of the board
     */
    private static boolean withinBounds(int x, int y) {
        return (
            x >= 0 &&
            x < N &&
            y >= 0 &&
            y < N
        );
    }

    /**
     * Move the empty tile in the given direction.
     *
     * @param d the direction to move
     */
    private void move(Direction d) {
        swap(
            this.holeX,
            this.holeY,
            this.holeX + d.GetDX(),
            this.holeY + d.GetDY()
        );
        this.holeX += d.GetDX();
        this.holeY += d.GetDY();
        this.heuristic = this.calculateHeuristic();
    }

    /**
     * Swap two tiles.
     *
     * @param x1 x-coordinate of the first tile
     * @param y1 y-coordinate of the first tile
     * @param x2 x-coordinate of the second tile
     * @param y2 y-coordinate of the second tile
     */
    private void swap(int x1, int y1, int x2, int y2) {
        int temp = this.board[x1][y1];
        this.board[x1][y1] = this.board[x2][y2];
        this.board[x2][y2] = temp;
    }

    /**
     * Calculate a heuristic for this configuration.
     *
     * @return a heuristic for this configuration
     */
    private int calculateHeuristic() {
        int heuristic = 0;
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                heuristic += Math.abs(x - (this.board[x][y] - 1) % N);
                heuristic += Math.abs(y - (this.board[x][y] - 1) / N);
            }
        }
        return heuristic;
    }
}
