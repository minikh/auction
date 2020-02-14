package auction.bots;

import auction.Bidder;

import java.util.LinkedList;

import static auction.Props.ONE_LOT;

/**
 * This is a test strategy for bidding
 */
public final class BruteForceStrategy implements Bidder {

    private final LinkedList<Integer> sequence = new LinkedList<>();
    private final int step;
    private int cash;

    public BruteForceStrategy(final int step) {
        this.step = step;
    }

    @Override
    public void init(final int quantity, final int cash) {
        this.cash = cash;
        var bid = cash / (quantity / ONE_LOT);
        while (this.cash > 0) {
            sequence.add(bid);
            this.cash -= bid;
            bid += step;
        }
    }

    @Override
    public int placeBid() {
        var bid = 0;
        if (sequence.size() > 0) {
            bid = sequence.removeFirst();
        }
        if (cash < bid) {
            bid = cash;
            cash = 0;
        } else {
            cash -= bid;
        }
        return bid;
    }

    @Override
    public void bids(final int own, final int other) {

    }
}
