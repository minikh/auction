A product x QU (quantity units) will be auctioned under 2 parties. The parties have each y MU (monetary units) for auction. 
They offer then simultaneously an arbitrary number of its MU on the first 2 QU of the product. 
After that, the bids will be visible to both. The 2 QU of the product is awarded to who has offered the most MU; 
if both bid the same, then both get 1 QU. Both bidders must pay their amount - including the defeated. 
A bid of 0 MU is allowed. Bidding on each 2 QU is repeated until the supply of x QU is fully auctioned. 
Each bidder aims to get a larger amount than its competitor.

In an auction wins the program that is able to get more QU than the other. With a tie, the program that retains more MU wins. 
Write a program that can participate in such an auction and competes with one of our programs. Please explain its strategy.

The bidder interface:

package auction;

public interface Bidder {

    /**
     * Initializes the bidder with the production quantity and the allowed cash limit.
     *
     * @param quantity the quantity
     * @param cash     the cash limit
     */
    void init(int quantity, int cash);

    /**
     * Retrieves the next bid for the product, which may be zero.
     *
     * @return the next bid
     */
    int placeBid();

    /**
     * Shows the bids of the two bidders.
     *
     * @param own   the bid of this bidder
     * @param other the bid of the other bidder
     */
    void bids(int own, int other);
}
