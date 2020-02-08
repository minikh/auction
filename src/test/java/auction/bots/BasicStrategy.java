package auction.bots;

import auction.Bidder;

import static auction.Props.IS_DEBUG;

/**
 * This is a test strategy for bidding
 */
public final class BasicStrategy implements Bidder {

    private int otherPreviousBid;
    private int ownPreviousBid;
    private int previousDeltaBid;
    private final int step;
    private int cash;

    public BasicStrategy(int step) {
        this.step = step;
    }

    @Override
    public void init(final int quantity, final int cash) {
        this.cash = cash;
    }

    @Override
    public int placeBid() {
        var bid = Math.max(otherPreviousBid, ownPreviousBid) + previousDeltaBid + step;
        if (cash < bid) {
            bid = cash;
            cash = 0;
        } else {
            cash -= bid;
        }

        if (IS_DEBUG) {
            System.out.println(String.format("%s. cash = %s bid = %s", this.getClass().getSimpleName(), cash, bid));
        }
        return bid;
    }

    @Override
    public void bids(final int own, final int other) {
        ownPreviousBid = own;
        otherPreviousBid = other;

        if (other > own) {
            previousDeltaBid = other - own + 1;
        } else {
            previousDeltaBid = 0;
        }
    }
}
