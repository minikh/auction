package auction;

public final class BotBidder implements Bidder {

    private int cash;
    private int otherPreviousBid;
    private int previousDeltaBid;

    @Override
    public void init(int quantity, int cash) {
        this.cash = cash;
    }

    @Override
    public int placeBid() {
        var bid = otherPreviousBid + previousDeltaBid + 1;

        if (cash < bid) {
            bid = cash;
            cash = 0;
        } else {
            cash -= bid;
        }
        System.out.println(String.format("%s. cash = %s bid = %s", this.getClass().getSimpleName(), cash, bid));
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
