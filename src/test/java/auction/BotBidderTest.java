package auction;

import auction.bots.TestBotBidder;
import auction.strategy.BruteForceStrategy;
import org.junit.jupiter.api.Test;

import static auction.Props.ONE_POSITION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotBidderTest {

    @Test
    void shouldWinRealBidderOrNobodyWonIsPossible() {
        //given
        var quantity = 46;
        var cash = 20000;

        var realBidder = new BotBidder();
        realBidder.init(quantity, cash);
        var testBidder = new TestBotBidder(10);
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertTrue(Result.REAL_BOT == auction.whoWon() || Result.NOBODY_WON == auction.whoWon(),
                "Real bot won or nobody won");
    }

    @Test
    void shouldWinRealBidderWhenCashLessThanTwoOrMore() {
        //given
        var quantity = 4000;
        var cash = 1000;

        var realBidder = new BotBidder();
        realBidder.init(quantity, cash);
        var testBidder = new TestBotBidder(3);
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon(), "Real bot won or nobody won");
    }

    @Test
    void shouldWinRealBidderAllTimes() {
        //given
        var firstQuantity = 10;
        var cash = 2000;

        var wonCount = 0;
        var looseCount = 0;
        var bothCount = 0;

        for (int quantity = firstQuantity; quantity <= 5 * cash; quantity += ONE_POSITION) {
            var realBidder = new BotBidder();
            realBidder.init(quantity, cash);
            var testBidder = new TestBotBidder(4);
            testBidder.init(quantity, cash);

            var auction = new Auction(quantity, cash);

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
                    System.out.println("Loose when quantity = " + quantity);
                    break;
                default:
                    bothCount++;
                    System.out.println("Nobodu won when quantity = " + quantity);
                    break;
            }
        }
        assertTrue(wonCount >= 0, "Real bot won " + wonCount + " times");
        assertTrue(bothCount >= 0, "Nobody won " + bothCount + " times");
        assertTrue(bothCount + wonCount > 0, "Test bot playd " + (bothCount + wonCount) + " times");
        assertEquals(0, looseCount, "Test bot won " + looseCount + " times");
    }

    @Test
    void shouldWinRealBidderEvenTheyHaveLittleMoney() {
        //given
        var quantity = 10000;
        var cash = 100;

        var realBidder = new BotBidder();
        realBidder.init(quantity, cash);
        var testBidder = new TestBotBidder(3);
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon(), "Real bot won or nobody won");
    }

    @Test
    void shouldWinRealBidderOppositeOfBrutForceBot() {
        //given
        var quantity = 100;
        var cash = 20000;

        var realBidder = new BotBidder();
        realBidder.init(quantity, cash);
        var testBidder = new TestBotBidder(new BruteForceStrategy(3));
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon(), "Real bot won or nobody won");
    }

    @Test
    void shouldWinRealBidderAllTimesOppositeOfBrutForceBot() {
        //given
        var firstQuantity = 10;
        var cash = 2000;

        var wonCount = 0;
        var looseCount = 0;
        var bothCount = 0;

        for (int quantity = firstQuantity; quantity <= 5 * cash; quantity += ONE_POSITION) {
            var realBidder = new BotBidder();
            realBidder.init(quantity, cash);
            var testBidder = new TestBotBidder(new BruteForceStrategy(1));
            testBidder.init(quantity, cash);

            var auction = new Auction(quantity, cash);

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
        assertTrue(wonCount >= 0, "Real bot won " + wonCount + " times");
        assertTrue(bothCount >= 0, "Nobody won " + bothCount + " times");
        assertTrue(bothCount + wonCount > 0, "Test bot plaid " + (bothCount + wonCount) + " times");
        assertEquals(0, looseCount, "Test bot won " + looseCount + " times");
    }
}
