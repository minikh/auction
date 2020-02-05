package auction.strategy;

public class BasicStrategy extends Strategy {

    private int otherPreviousBid;
    private int ownPreviousBid;
    private int previousDeltaBid;

    @Override
    public int placeBid() {
        return changeBalance(Math.max(otherPreviousBid, ownPreviousBid) + previousDeltaBid + 1);
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
