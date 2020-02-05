package auction;

import auction.bots.TestBotBidder;
import auction.strategy.BruteForceStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotBidderTest {

    @Test
    void shouldWinRealBidder() {
        //given
        var quantity = 46;
        var cash = 20000;

        var realBidder = new BotBidder();
        realBidder.init(quantity, cash);
        var testBidder = new TestBotBidder();
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon());
    }

    @Test
    void shouldWinRealBidderWhenCashLessThanTwoOrMore() {
        //given
        var quantity = 4000;
        var cash = 1000;

        var realBidder = new BotBidder();
        realBidder.init(quantity, cash);
        var testBidder = new TestBotBidder();
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon());
    }

    @Test
    void shouldWinRealBidderAllTimes() {
        //given
        var firstQuantity = 10;
        var cash = 2000;

        var wonCount = 0;
        var looseCount = 0;
        var bothCount = 0;

        for (int quantity = firstQuantity; quantity <= 5 * cash; quantity += 2) {
            var realBidder = new BotBidder();
            realBidder.init(quantity, cash);
            var testBidder = new TestBotBidder();
            testBidder.init(quantity, cash);

            var auction = new Auction(quantity, cash, cash);

            //when
            while (auction.getQuantityUnits() > 0) {
                auction.doBid(realBidder, testBidder);
            }

            //then
            switch (auction.whoWon()) {
                case REAL_BOT:
                    wonCount++;
                    break;
                case TEST_BOT:
                    looseCount++;
                    break;
                default:
                    bothCount++;
                    break;
            }
        }
        assertTrue(wonCount >= 0);
        assertTrue(bothCount >= 0);
        assertTrue(bothCount + wonCount > 0);
        assertEquals(0, looseCount);
    }

    @Test
    void shouldWinRealBidderEvenTheyHaveLittleMoney() {
        //given
        var quantity = 10000;
        var cash = 100;

        var realBidder = new BotBidder();
        realBidder.init(quantity, cash);
        var testBidder = new TestBotBidder();
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon());
    }

    @Test
    void shouldWinFirstBidderBecauseItHasMachMoney() {
        //given
        var quantity = 20;
        var cash1 = 1000;
        var cash2 = 50;

        var bidder1 = new BotBidder();
        bidder1.init(quantity, cash1);
        var bidder2 = new BotBidder();
        bidder2.init(quantity, cash2);

        var auction = new Auction(quantity, cash1, cash2);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(bidder1, bidder2);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon());
    }

    @Test
    void shouldWinRealBidderOppositeOfBrutForceBot() {
        //given
        var quantity = 100;
        var cash = 20000;

        var realBidder = new BotBidder();
        realBidder.init(quantity, cash);
        var testBidder = new TestBotBidder(new BruteForceStrategy());
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon());
    }
}
