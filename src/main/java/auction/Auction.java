package auction;

import org.jetbrains.annotations.NotNull;

import static auction.Props.IS_DEBUG;
import static auction.Props.ONE_POSITION;

public class Auction {

    private int quantityUnits;
    private int bid1Sum;
    private int bid2Sum;

    private int bid1Cash;
    private int bid2Cash;

    public Auction(int quantityUnits, int cash) {
        this.quantityUnits = quantityUnits;
        bid1Cash = cash;
        bid2Cash = cash;
    }

    void doBid(@NotNull Bidder bidder1, @NotNull Bidder bidder2) {
        var bid1 = bidder1.placeBid();
        bid1Cash -= bid1;
        var bid2 = bidder2.placeBid();
        bid2Cash -= bid2;

        if (bid1 > bid2) {
            bid1Sum += ONE_POSITION;
        } else if (bid2 > bid1) {
            bid2Sum += ONE_POSITION;
        } else {
            bid1Sum += ONE_POSITION / 2;
            bid2Sum += ONE_POSITION / 2;
        }

        bidder1.bids(bid1, bid2);
        bidder2.bids(bid2, bid1);
        quantityUnits -= ONE_POSITION;

        if (IS_DEBUG) {
            var msg = String.format("Result: %s : %s | %s : %s \n",
                    bidder1.getClass().getSimpleName(), bidder2.getClass().getSimpleName(), bid1Sum, bid2Sum);
            System.out.println(msg);
        }
    }

    public Result whoWon() {
        if (bid1Sum > bid2Sum) return Result.REAL_BOT;
        if (bid2Sum > bid1Sum) return Result.TEST_BOT;

        if (bid1Cash > bid2Cash) return Result.REAL_BOT;
        if (bid2Cash > bid1Cash) return Result.TEST_BOT;

        return Result.NOBODY_WON;
    }

    public int getQuantityUnits() {
        return quantityUnits;
    }

    public int getBid1Sum() {
        return bid1Sum;
    }

    public int getBid2Sum() {
        return bid2Sum;
    }
}
