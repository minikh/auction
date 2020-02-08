package auction.bots;

import auction.Bidder;

import static auction.Props.IS_DEBUG;
import static auction.Props.ONE_LOT;

/**
 * This is a test strategy for bidding
 */
public final class AnalyticStrategy implements Bidder {

    private int otherPreviousBid;
    private int previousDeltaBid;
    private int currentQuantity;
    private int cash;
    private int otherCurrentCash;

    @Override
    public void init(final int quantity, final int cash) {
        this.cash = cash;
        otherCurrentCash = cash;
        currentQuantity = quantity;
    }

    @Override
    public int placeBid() {
        final var bidder1CurrCash = cash - otherCurrentCash;

        final var bid1 = bidder1CurrCash / (currentQuantity / ONE_LOT);
        final var bid2 = otherPreviousBid + previousDeltaBid + 1;

        var bid = Math.max(bid1, bid2);
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
        otherCurrentCash -= other;
        currentQuantity -= ONE_LOT;
        otherPreviousBid = other;

        if (other > own) {
            previousDeltaBid = other - own + 1;
        } else {
            previousDeltaBid = 0;
        }
    }
}
