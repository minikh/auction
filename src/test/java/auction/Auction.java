package auction;

import static auction.Props.IS_DEBUG;
import static auction.Props.ONE_POSITION;

public final class Auction {

    private final int quantityUnits;
    private final int cash;

    private Auctioneer auctioneer1;
    private Auctioneer auctioneer2;

    public Auction(final int quantityUnits, final int cash) {
        this.quantityUnits = quantityUnits;
        this.cash = cash;
    }

    public void setRealStrategy(final Bidder strategy) {
        auctioneer1 = new Auctioneer(strategy, quantityUnits, cash);
    }

    public void setTestStrategy(final Bidder strategy) {
        auctioneer2 = new Auctioneer(strategy, quantityUnits, cash);
    }

    public void runGame() {
        var remains = quantityUnits;

        while (remains > 0) {
            doBids();
            remains -= ONE_POSITION;
        }
    }

    private void doBids() {
        final var bid1 = auctioneer1.getBid();
        final var bid2 = auctioneer2.getBid();

        if (bid1 > bid2) {
            auctioneer1.addUnit(ONE_POSITION);
        } else if (bid2 > bid1) {
            auctioneer2.addUnit(ONE_POSITION);
        } else {
            auctioneer1.addUnit(ONE_POSITION / 2);
            auctioneer2.addUnit(ONE_POSITION / 2);
        }

        auctioneer1.bids(bid1, bid2);
        auctioneer2.bids(bid2, bid1);

        printResult();
    }

    public Result whoWon() {
        if (auctioneer1.getCurrentLotsCount() > auctioneer2.getCurrentLotsCount()) return Result.REAL_BOT;
        if (auctioneer1.getCurrentLotsCount() < auctioneer2.getCurrentLotsCount()) return Result.TEST_BOT;

        if (auctioneer1.getSpentCash() < auctioneer2.getSpentCash()) return Result.REAL_BOT;
        if (auctioneer1.getSpentCash() > auctioneer2.getSpentCash()) return Result.TEST_BOT;

        return Result.TIE;
    }

    private void printResult() {
        if (IS_DEBUG) {
            final var msg = String.format("Result: %s : %s | %s : %s \n",
                    auctioneer1.name(), auctioneer2.name(), auctioneer1.getCurrentLotsCount(), auctioneer2.getCurrentLotsCount());
            System.out.println(msg);
        }
    }

    private final static class Auctioneer {
        private final int startBalance;
        private final Bidder strategy;

        private int currentLotsAmount;
        private int spentCash;

        public Auctioneer(final Bidder strategy, final int quantity, final int cash) {
            this.strategy = strategy;
            this.startBalance = cash;
            strategy.init(quantity, cash);
        }

        public void addUnit(final int count) {
            currentLotsAmount += count;
        }

        public int getBid() {
            final int bid = strategy.placeBid();

            spendCash(bid);

            if (IS_DEBUG) {
                final var msg =
                        String.format("%s. cash remain = %s bid = %s", String.format("%-30s", name()), startBalance - getSpentCash(), bid);
                System.out.println(msg);
            }

            return bid;
        }

        public void bids(final int bid1, final int bid2) {
            strategy.bids(bid1, bid2);
        }

        public String name() {
            return strategy.getClass().getSimpleName();
        }

        public int getCurrentLotsCount() {
            return currentLotsAmount;
        }

        public int getSpentCash() {
            return spentCash;
        }

        private void spendCash(int cash) {
            spentCash += cash;
        }
    }
}
