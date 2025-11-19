package liquidsorter;

import liquidsorter.model.Move;
import liquidsorter.model.State;
import liquidsorter.util.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        for (State state : getStateList()) {
            System.out.println("================================");
            state.print("Initial state:");
            List<Move> solution = Solver.solve(state);
            if (solution == null) {
                System.out.println("No solution found in "+Solver.getMaxDepth()+" states ;-(");
                System.out.println("================================\n");
                return;
            }

            System.out.println("\nSolved in " + solution.size() + " moves");
            printMoves(solution);
            Solver.recreateState(state, solution).print("\nFinal state:");
            System.out.println("================================\n");
        }

        System.out.println("Press enter to close...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
        System.exit(0);
    }

    public static List<State> getStateList() {
        List<State> states = new ArrayList<>();

        int[][] baby = {
                {1, 1, 2, 2},
                {2, 2, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int[][] medium = {
                {1, 2, 3, 1},
                {2, 3, 1, 2},
                {3, 1, 2, 3},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int[][] bringEmOn = {
                {1, 2, 3, 4},
                {2, 3, 4, 1},
                {3, 4, 1, 2},
                {4, 1, 2, 3},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int[][] extreme = {
                {1, 2, 3, 4, 5, 1},
                {2, 3, 4, 5, 1, 2},
                {3, 4, 5, 1, 2, 3},
                {4, 5, 1, 2, 3, 4},
                {5, 1, 2, 3, 4, 5},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };
        int[][] doczilla = {
                {4, 4, 10, 2},
                {8, 12, 8, 1},
                {9, 5, 7, 10},
                {5, 2, 3, 5},
                {7, 8, 11, 6},
                {2, 1, 12, 12},
                {11, 8, 7, 4},
                {1, 3, 11, 10},
                {9, 9, 7, 10},
                {11, 6, 2, 6},
                {3, 9, 6, 4},
                {1, 12, 3, 5},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        states.add(new State(baby));
        states.add(new State(medium));
        states.add(new State(bringEmOn));
        states.add(new State(extreme));
        states.add(new State(doczilla));

        return states;
    }

    public static void printMoves(List<Move> moveList) {
        for (int i = 0; i < moveList.size(); i++) {
            System.out.println(i + " " + moveList.get(i));
        }
    }
}
