package auction;

import org.jetbrains.annotations.NotNull;

public class Auction {

    private int quantityUnits;
    private int bid1Sum;
    private int bid2Sum;

    private int bid1Cash;
    private int bid2Cash;

    public Auction(int quantityUnits, int cash1, int cash2) {
        this.quantityUnits = quantityUnits;
        bid1Cash = cash1;
        bid2Cash = cash2;
    }

    void doBid(@NotNull Bidder bidder1, @NotNull Bidder bidder2) {
        var bid1 = bidder1.placeBid();
        bid1Cash -= bid1;
        var bid2 = bidder2.placeBid();
        bid2Cash -= bid2;

        if (bid1 > bid2) {
            bid1Sum += 2;
        } else if (bid2 > bid1) {
            bid2Sum += 2;
        } else {
            bid1Sum++;
            bid2Sum++;
        }

        bidder1.bids(bid1, bid2);
        bidder2.bids(bid2, bid1);
        quantityUnits -= 2;

        var msg = String.format("Result: %s : %s | %s : %s \n",
                bidder1.getClass().getSimpleName(), bidder2.getClass().getSimpleName(), bid1Sum, bid2Sum);
        System.out.println(msg);
    }

    public int whoWon() {
        if (bid1Sum > bid2Sum) return 1;
        if (bid2Sum > bid1Sum) return 2;

        if (bid1Cash > bid2Cash) return 1;
        if (bid2Cash > bid1Cash) return 2;

        return 0;
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
