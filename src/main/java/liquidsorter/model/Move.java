package liquidsorter.model;

public record Move(int fromBottle, int toBottle, int amount) {

    @Override
    public String toString() {
        return String.format("(%d -> %d, %d)", fromBottle, toBottle, amount);
    }
}
