package auction.strategy;

import static auction.Props.IS_DEBUG;

public class AnalyticStrategy extends Strategy {

    private int otherPreviousBid;
    private int previousDeltaBid;
    private int currentQuantity;

    @Override
    public void init(int quantity, int cash) {
        super.init(quantity, cash);
        currentQuantity = quantity;
    }

    @Override
    public int placeBid() {
        var bid1 = cash() / (currentQuantity / 2);
        var bid2 = otherPreviousBid + previousDeltaBid + 1;
        var bid = changeBalance(Math.max(bid1, bid2));

        if (IS_DEBUG) {
            System.out.println(String.format("%s. cash = %s bid = %s", this.getClass().getSimpleName(), cash(), bid));
        }
        return bid;
    }

    @Override
    public void bids(int own, int other) {
        currentQuantity -= 2;
        otherPreviousBid = other;

        if (other > own) {
            previousDeltaBid = other - own + 1;
        } else {
            previousDeltaBid = 0;
        }
    }
}
