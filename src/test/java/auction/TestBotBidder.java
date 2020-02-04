package auction;

public final class TestBotBidder implements Bidder {

    private int cash;
    private int otherPreviousBid;
    private int ownPreviousBid;
    private int previousDeltaBid;

    @Override
    public void init(int quantity, int cash) {
        this.cash = cash;
    }

    @Override
    public int placeBid() {
        var bid = Math.max(otherPreviousBid, ownPreviousBid) + previousDeltaBid + 10;

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
        ownPreviousBid = own;
        otherPreviousBid = other;

        if (other > own) {
            previousDeltaBid = other - own;
        } else {
            previousDeltaBid = 0;
        }
    }
}
