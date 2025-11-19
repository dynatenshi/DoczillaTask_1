package liquidsorter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Bottle {
    private final List<Integer> liquids;
    private final int capacity;

    public Bottle(int capacity, int[] liquidArray) {
        this.capacity = capacity;
        this.liquids = new ArrayList<>();

        this.liquids.addAll(
                Arrays.stream(liquidArray)
                        .filter(liquid -> liquid > 0)
                        .boxed()
                        .toList()
        );
    }

    /**
     *  For creating copies.
     * @param capacity - capacity of bottle
     * @param liquids - list of liquids
     */
    private Bottle(int capacity, List<Integer> liquids) {
        this.capacity = capacity;
        this.liquids = new ArrayList<>(liquids);
    }

    public List<Integer> getLiquids() {
        return new ArrayList<>(liquids);
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return liquids.isEmpty();
    }

    public boolean isFull() {
        return liquids.size() == capacity;
    }

    public int size() {
        return liquids.size();
    }

    public int getTopLiquid() {
        return isEmpty() ? 0 : liquids.get(liquids.size()-1);
    }

    public int getTopLiquidGroupSize() {
        if (isEmpty()) return 0;

        int topLiquid = getTopLiquid();
        int count = 1;

        for (int i = liquids.size()-2; i >= 0; i--) {
            if (topLiquid != liquids.get(i)) break;

            count++;
        }

        return count;
    }

    public boolean canPourInto(Bottle other, int amount) {
        return !isEmpty() && (other.canAccept(getTopLiquid(), amount));
    }

    public boolean canAccept(int liquid, int amount) {
        return isEmpty() || (getTopLiquid() == liquid && amount <= getSizeLeft());
    }

    /**
     * Tries to pour liquid into other bottle. Throws exception if unable to.
     * @param other other bottle
     * @param amount amount of liquid
     */
    public void pourInto(Bottle other, int amount) throws IllegalArgumentException {
        if (!canPourInto(other, amount)) {
            throw new IllegalArgumentException("Can't pour into bottle!");
        }

        for (int i = 0; i < amount; i++) {
            int liquid = liquids.remove(liquids.size()-1);
            other.liquids.add(liquid);
        }
    }

    public boolean isComplete() {
        return isEmpty() || (getTopLiquidGroupSize() == capacity);
    }

    public int getSizeLeft() {
        return capacity - liquids.size();
    }

    /**
     *
     * @return {@code Bottle} copy of this bottle.
     */
    public Bottle copy() {
        return new Bottle(capacity, liquids);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Bottle bottle)) return false;
        return Objects.equals(liquids, bottle.liquids);
    }

    @Override
    public String toString() {
        return liquids.toString();
    }

    @Override
    public int hashCode() {
        return liquids.hashCode();
    }
}
