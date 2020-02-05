package auction;

import auction.bots.TestBotBidder;
import auction.strategy.BruteForceStrategy;
import org.junit.jupiter.api.Test;

import java.util.Random;

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
        var testBidder = new TestBotBidder(3);
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);

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
        var testBidder = new TestBotBidder(3);
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);

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
        var testBidder = new TestBotBidder(3);
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
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
        var testBidder = new TestBotBidder(new BruteForceStrategy(3));
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);

        //when
        while (auction.getQuantityUnits() > 0) {
            auction.doBid(realBidder, testBidder);
        }

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon());
    }

    @Test
    void shouldWinRealBidderAllTimesOppositeOfBrutForceBot() {
        //given
        var firstQuantity = 10;
        var cash = 2000;

        var wonCount = 0;
        var looseCount = 0;
        var bothCount = 0;

        for (int quantity = firstQuantity; quantity <= 5 * cash; quantity += 2) {
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
}
