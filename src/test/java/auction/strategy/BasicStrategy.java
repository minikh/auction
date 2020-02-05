package auction.strategy;

public class BasicStrategy extends Strategy {

    private int otherPreviousBid;
    private int ownPreviousBid;
    private int previousDeltaBid;
    private final int step;

    public BasicStrategy(int step) {
        this.step = step;
    }

    @Override
    public int placeBid() {
        return changeBalance(Math.max(otherPreviousBid, ownPreviousBid) + previousDeltaBid + step);
    }

    @Override
    public void bids(int own, int other) {
        ownPreviousBid = own;
        otherPreviousBid = other;

        if (other > own) {
            previousDeltaBid = other - own + 1;
        } else {
            previousDeltaBid = 0;
        }
    }
}
