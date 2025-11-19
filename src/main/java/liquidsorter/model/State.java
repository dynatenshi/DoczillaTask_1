package liquidsorter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Contains state of bottles
 */
public class State {
    private final List<Bottle> bottles;
    private final int capacity;

    public State(int[][] bottleArray) {
        this.capacity = bottleArray[0].length;
        bottles = new ArrayList<>();

        for (int[] bottle : bottleArray) {
            bottles.add(new Bottle(capacity, bottle));
        }
    }

    /**
     * Used for creating copies.
     * @param capacity bottles capacity
     * @param bottles list of bottles
     */
    private State(int capacity, List<Bottle> bottles) {
        this.capacity = capacity;
        this.bottles = new ArrayList<>();

        for (Bottle bottle : bottles) {
            this.bottles.add(bottle.copy());
        }
    }

    public void print(String prompt) {
        if (prompt == null) prompt = "";

        System.out.println(prompt);
        for (int i = 0; i < bottles.size(); i++) {
            System.out.printf("Bottle %d: %s%n", i, bottles.get(i));
        }
    }

    public boolean isSolved() {
        for (Bottle bottle : bottles) {
            if (!bottle.isComplete()) return false;
        }
        return true;
    }

    public List<Move> generateValidMoves() {
        List<Move> validMoves = new ArrayList<>();

        for (int from = 0; from < bottles.size(); from++) {
            Bottle fromBottle = bottles.get(from);

            if (fromBottle.isEmpty() || fromBottle.isComplete()) continue;

            for (int to = 0; to < bottles.size(); to++) {
                if (from == to) continue;

                Bottle toBottle = bottles.get(to);
                int topLiquidSize = fromBottle.getTopLiquidGroupSize();

                Move move = createMove(from, to);

                if (move != null) {
                    validMoves.add(move);

                    //we don't need to look further if found best move
                    if (move.amount() == topLiquidSize && toBottle.getSizeLeft() >= topLiquidSize) {
                        break;
                    }

                }
            }
        }
        return validMoves;
    }

    private Move createMove(int from, int to) {
        Bottle fromBottle = bottles.get(from);
        Bottle toBottle = bottles.get(to);

        if (!fromBottle.canPourInto(toBottle, 1)) return null;

        int topLiquidSize = fromBottle.getTopLiquidGroupSize();
        int maxAmount = Math.min(toBottle.getSizeLeft(), topLiquidSize);
        return new Move(from, to, maxAmount);
    }

    public State applyMove(Move move) {
        State newState = copy();
        Bottle fromBottle = newState.bottles.get(move.fromBottle());
        Bottle toBottle = newState.bottles.get(move.toBottle());

        fromBottle.pourInto(toBottle, move.amount());

        return newState;
    }

    public List<Bottle> getBottles() {
        return copy().getBottles();
    }

    /**
     *
     * @return {@code State} copy of this state
     */
    public State copy() {
        return new State(capacity, bottles);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof State state)) return false;
        return Objects.equals(bottles, state.bottles);
    }

    @Override
    public int hashCode() {
        return bottles.hashCode();
    }
}
