package auction.strategy;

import static auction.Props.IS_DEBUG;
import static auction.Props.ONE_POSITION;

public class AnalyticStrategy extends Strategy {

    private int otherPreviousBid;
    private int previousDeltaBid;
    private int currentQuantity;
    private int cashOnStart;
    private int otherCurrentCash;

    @Override
    public void init(int quantity, int cash) {
        super.init(quantity, cash);
        cashOnStart = cash;
        otherCurrentCash = cash;
        currentQuantity = quantity;
    }

    @Override
    public int placeBid() {
        var bidder1CurrCash = cashOnStart - otherCurrentCash;

        var bid1 = bidder1CurrCash / (currentQuantity / ONE_POSITION);
        var bid2 = otherPreviousBid + previousDeltaBid + 1;

        var bid = changeBalance(Math.max(bid1, bid2));
//        var bid = changeBalance(bid2);

        if (IS_DEBUG) {
            System.out.println(String.format("%s. cash = %s bid = %s", this.getClass().getSimpleName(), cash(), bid));
        }
        return bid;
    }

    @Override
    public void bids(int own, int other) {
        otherCurrentCash -= other;
        currentQuantity -= ONE_POSITION;
        otherPreviousBid = other;

        if (other > own) {
            previousDeltaBid = other - own + 1;
        } else {
            previousDeltaBid = 0;
        }
    }
}
