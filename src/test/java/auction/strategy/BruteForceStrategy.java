package auction.strategy;

import java.util.LinkedList;

public class BruteForceStrategy extends Strategy {

    private final LinkedList<Integer> sequence = new LinkedList<>();

    @Override
    public void init(int quantity, int cash) {
        super.init(quantity, cash);

        var bid = cash / (quantity / 2);
        while (cash > 0) {
            sequence.add(bid);
            cash -= bid;
            bid += 1;
        }
    }

    @Override
    public int placeBid() {
        var firstVal = 0;
        if (sequence.size() > 0) {
            firstVal = sequence.removeFirst();
        }
        return changeBalance(firstVal);
    }

    @Override
    public void bids(int own, int other) {

    }
}
