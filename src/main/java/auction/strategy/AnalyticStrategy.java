package auction.strategy;

import static auction.Props.IS_DEBUG;

public class AnalyticStrategy extends Strategy {

    private int otherPreviousBid;
    private int previousDeltaBid;

    @Override
    public int placeBid() {
        var bid = changeBalance(otherPreviousBid + previousDeltaBid + 1);

        if (IS_DEBUG) {
            System.out.println(String.format("%s. cash = %s bid = %s", this.getClass().getSimpleName(), cash(), bid));
        }
        return bid;
    }

    @Override
    public void bids(int own, int other) {
        otherPreviousBid = other;

        if (other > own) {
            previousDeltaBid = other - own + 1;
        } else {
            previousDeltaBid = 0;
        }
    }
}
