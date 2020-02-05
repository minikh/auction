package auction;

import java.util.ArrayList;
import java.util.List;

import static auction.Props.IS_DEBUG;
import static auction.Props.ONE_POSITION;

/**
 * This is the main strategy for bidding
 */
public final class DynamicBidStrategy implements Bidder {

    private int rounds;
    private int cash;
    private final List<Integer> ownBids = new ArrayList<>();
    private final List<Integer> otherBids = new ArrayList<>();

    @Override
    public void init(int quantity, int cash) {
        this.rounds = quantity / ONE_POSITION;
        this.cash = cash;
    }

    @Override
    public int placeBid() {
        final int myMoney = ownMoney();
        final int hisMoney = otherMoney();

        int baseBid = myMoney / remainRound();

        //todo think about how to calculate coefficients dynamically
        //these magic numbers has been found empirically
        if (myMoney > hisMoney && amIWinning()) {
            //if I have more money than another and I am winning, I can make the baseBid less
            baseBid = (int) (baseBid * 0.7);
        } else if (myMoney > hisMoney && !amIWinning()) {
            //if I have more money than another and I am not winning, I can make the baseBid more
            baseBid = (int) (baseBid * 1.2);
        } else if (myMoney < hisMoney && amIWinning()) {
            //if I have less money than another and I am winning, I can make the baseBid less
            baseBid = (int) (baseBid * 0.6);
        } else if (myMoney < hisMoney && !amIWinning()) {
            //if I have less money than another and I am not winning, I can make the baseBid more less
            baseBid = (int) (baseBid * 0.2);
        }

        final var bid = Math.min(myMoney, baseBid);
        if (IS_DEBUG) {
            System.out.println(String.format("%s. cash = %s bid = %s", this.getClass().getSimpleName(), cash, bid));
        }
        return bid;
    }

    @Override
    public void bids(final int own, final int other) {
        ownBids.add(own);
        otherBids.add(other);
    }

    private boolean amIWinning() {
        int index = 0;
        int winCount = 0;
        while (ownBids.size() > index) {
            if (ownBids.get(index) > otherBids.get(index)) {
                winCount++;
            } else {
                winCount--;
            }
            index++;
        }
        return winCount > 0;
    }

    private int ownMoney() {
        //fixme here we can think about optimisation
        return cash - ownBids.stream().reduce(0, Integer::sum);
    }

    private int otherMoney() {
        //fixme here we can think about optimisation
        return cash - otherBids.stream().reduce(0, Integer::sum);
    }

    private int remainRound() {
        return rounds - ownBids.size();
    }
}
