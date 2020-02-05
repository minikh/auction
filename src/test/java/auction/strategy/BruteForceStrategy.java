package auction.strategy;

import java.util.LinkedList;

import static auction.Props.ONE_POSITION;

public class BruteForceStrategy extends Strategy {

    private final LinkedList<Integer> sequence = new LinkedList<>();
    private final int step;

    public BruteForceStrategy(int step) {
        this.step = step;
    }

    @Override
    public void init(int quantity, int cash) {
        super.init(quantity, cash);

        var bid = cash / (quantity / ONE_POSITION);
        while (cash > 0) {
            sequence.add(bid);
            cash -= bid;
            bid += step;
        }
    }

    @Override
    public int placeBid() {
        var firstVal = 0;
        if (sequence.size() > 0) {
            firstVal = sequence.removeFirst();
        }
        return changeBalance(firstVal);
    }

    @Override
    public void bids(int own, int other) {

    }
}
