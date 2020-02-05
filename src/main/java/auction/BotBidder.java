package auction;

import auction.strategy.AnalyticStrategy;
import auction.strategy.CashDivideStrategy;
import auction.strategy.Strategy;

import static auction.Props.IS_DEBUG;

public final class BotBidder implements Bidder {

    private Strategy strategy;

    @Override
    public void init(int quantity, int cash) {
        strategy = new AnalyticStrategy();
        strategy.init(quantity, cash);
    }

    @Override
    public int placeBid() {
        var bid = strategy.placeBid();
        var cash = strategy.cash();

        if (IS_DEBUG) {
            System.out.println(String.format("%s. cash = %s bid = %s", this.getClass().getSimpleName(), cash, bid));
        }
        return bid;
    }

    @Override
    public void bids(int own, int other) {
        strategy.bids(own, other);
    }
}
