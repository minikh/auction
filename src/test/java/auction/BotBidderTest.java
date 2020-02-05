package auction;

import auction.bots.BasicStrategy;
import auction.bots.AnalyticStrategy;
import auction.bots.BruteForceStrategy;
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

        var realBidder = new DynamicBidStrategy();
        var testBidder = new BasicStrategy(10);

        var auction = new Auction(quantity, cash);
        auction.setRealStrategy(realBidder);
        auction.setTestStrategy(testBidder);

        //when
        auction.runGame();

        //then
        assertTrue(Result.REAL_BOT == auction.whoWon() || Result.TIE == auction.whoWon(),
                "Real bot won or nobody won");
    }

    @Test
    void shouldWinRealBidderWhenCashLessThanTwoOrMore() {
        //given
        var quantity = 4000;
        var cash = 1000;

        var realBidder = new DynamicBidStrategy();
        realBidder.init(quantity, cash);
        var testBidder = new BasicStrategy(3);
        testBidder.init(quantity, cash);

        var auction = new Auction(quantity, cash);
        auction.setRealStrategy(realBidder);
        auction.setTestStrategy(testBidder);

        //when
        auction.runGame();

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon(), "Real bot won");
    }

    @Test
    void shouldWinRealBidderAllTimes() {
        //given
        var firstQuantity = 10;
        var cash = 200;

        var wonCount = 0;
        var looseCount = 0;
        var bothCount = 0;

        for (int quantity = firstQuantity; quantity <= 2 * cash; quantity += ONE_POSITION) {
            var realBidder = new DynamicBidStrategy();
            var testBidder = new BasicStrategy(4);

            var auction = new Auction(quantity, cash);
            auction.setRealStrategy(realBidder);
            auction.setTestStrategy(testBidder);

            //when
            auction.runGame();

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
        assertEquals(0, looseCount, "Test bot won " + looseCount + " times");
        assertTrue(wonCount >= 0, "Real bot won " + wonCount + " times");
        assertEquals(0, bothCount, "Nobody won " + bothCount + " times");
    }

    @Test
    void shouldWinRealBidderEvenTheyHaveLittleMoney() {
        //given
        var quantity = 10000;
        var cash = 100;

        var realBidder = new DynamicBidStrategy();
        var testBidder = new BasicStrategy(3);

        var auction = new Auction(quantity, cash);
        auction.setRealStrategy(realBidder);
        auction.setTestStrategy(testBidder);

        //when
        auction.runGame();

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon(), "Real bot won");
    }

    @Test
    void shouldWinRealBidderOppositeOfBrutForceBot() {
        //given
        var quantity = 100;
        var cash = 20000;

        var realBidder = new DynamicBidStrategy();
        var testBidder = new BruteForceStrategy(3);

        var auction = new Auction(quantity, cash);
        auction.setRealStrategy(realBidder);
        auction.setTestStrategy(testBidder);

        //when
        auction.runGame();

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon(), "Real bot won");
    }

    @Test
    void shouldWinRealBidderAllTimesOppositeOfBrutForceBot() {
        //given
        var firstQuantity = 10;
        var cash = 200;

        var wonCount = 0;
        var looseCount = 0;
        var bothCount = 0;

        for (int quantity = firstQuantity; quantity <= 5 * cash; quantity += ONE_POSITION) {
            var realBidder = new DynamicBidStrategy();
            var testBidder = new BruteForceStrategy(1);

            var auction = new Auction(quantity, cash);
            auction.setRealStrategy(realBidder);
            auction.setTestStrategy(testBidder);

            //when
            auction.runGame();

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
        assertEquals(0, bothCount, "Nobody won " + bothCount + " times");
        assertEquals(0, looseCount, "Test bot won " + looseCount + " times");
    }

    @Test
    void shouldWinDynamicBidStrategy() {
        //given
        var quantity = 200;
        var cash = 20000;
        var auction = new Auction(quantity, cash);

        var realBidder = new DynamicBidStrategy();
        var testBidder = new AnalyticStrategy();

        auction.setRealStrategy(realBidder);
        auction.setTestStrategy(testBidder);

        //when
        auction.runGame();

        //then
        assertEquals(Result.REAL_BOT, auction.whoWon(), "Real bot won");
    }
}
