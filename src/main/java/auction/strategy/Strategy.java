package auction.strategy;

import auction.Bidder;

public abstract class Strategy implements Bidder {

    private int cash;

    @Override
    public void init(int quantity, int cash) {
        this.cash = cash;
    }

    protected int changeBalance(int bid) {
        if (cash < bid) {
            bid = cash;
            cash = 0;
        } else {
            cash -= bid;
        }
        return bid;
    }

    public int cash() {
        return cash;
    }
}
