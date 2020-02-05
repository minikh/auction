package auction.strategy;

public class CashDivideStrategy extends Strategy {

    private int bid;

    @Override
    public void init(int quantity, int cash) {
        super.init(quantity, cash);
        bid = cash / (quantity / 2);
    }

    @Override
    public int placeBid() {
        return changeBalance(this.bid);
    }

    @Override
    public void bids(int own, int other) {

    }
}
