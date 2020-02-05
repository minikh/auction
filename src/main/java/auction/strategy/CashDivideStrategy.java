package auction.strategy;

import static auction.Props.ONE_POSITION;

public class CashDivideStrategy extends Strategy {

    private int bid;

    @Override
    public void init(int quantity, int cash) {
        super.init(quantity, cash);
        bid = cash / (quantity / ONE_POSITION);
    }

    @Override
    public int placeBid() {
        return changeBalance(this.bid);
    }

    @Override
    public void bids(int own, int other) {

    }
}
