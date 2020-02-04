package auction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotBidderTest {

    @Test
    void shouldWinRealBidder() {
        //given
        var quantity = 20;
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
        assertEquals(1, auction.whoWon());
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
        assertEquals(1, auction.whoWon());
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
        assertEquals(1, auction.whoWon());
    }
}
