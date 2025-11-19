package liquidsorter.util;

import liquidsorter.model.Move;
import liquidsorter.model.State;

import java.util.*;

public class Solver {
    private static final int MAX_DEPTH = 100;

    /**
     *DFS solving algorithm. Not optimal, but easy and works for hard cases.
     * @param initialState state for which we want solution
     * @return {@code List<Move>} that contains solution or {@code null} if solution was not found
     */
    public static List<Move> solve(State initialState) {
        Set<State> visited = new HashSet<>();
        return dfsRecursive(initialState, new ArrayList<>(), visited, 0);
    }

    private static List<Move> dfsRecursive(State currentState, List<Move> currentPath,
                                  Set<State> visited, int depth) {
        if (depth > MAX_DEPTH) {
            return null;
        }

        if (currentState.isSolved()) {
            return new ArrayList<>(currentPath);
        }

        visited.add(currentState);

        for (Move move : currentState.generateValidMoves()) {
            State nextState = currentState.applyMove(move);

            if (!visited.contains(nextState)) {
                currentPath.add(move);
                List<Move> result = dfsRecursive(nextState, currentPath, visited, depth + 1);
                currentPath.removeLast();

                if (result != null) {
                    return result;
                }
            }
        }

        visited.remove(currentState);
        return null;
    }

    /**
     * Recreates state based on move list
     * @param initialState initial state
     * @param moves list of moves we'll apply
     * @return {@code State} with all moves applied
     */
    public static State recreateState(State initialState, List<Move> moves) {
        State state = initialState.copy();

        for (Move move: moves) {
            state = state.applyMove(move);
        }

        return state;
    }

    public static int getMaxDepth() {
        return MAX_DEPTH;
    }
}
