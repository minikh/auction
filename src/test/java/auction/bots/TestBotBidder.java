package auction.bots;

import auction.Bidder;
import auction.strategy.BasicStrategy;
import auction.strategy.Strategy;
import static auction.Props.IS_DEBUG;

public final class TestBotBidder implements Bidder {

    private final Strategy strategy;

    public TestBotBidder() {
        strategy = new BasicStrategy();
    }

    public TestBotBidder(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void init(int quantity, int cash) {
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
