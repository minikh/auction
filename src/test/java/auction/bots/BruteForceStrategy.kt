package auction.bots

import auction.Bidder
import auction.Props
import java.util.*

/**
 * This is a test strategy for bidding
 */
class BruteForceStrategy(private val step: Int) : Bidder {

    private val sequence = LinkedList<Int>()
    private var cash = 0

    override fun init(quantity: Int, cash: Int) {
        this.cash = cash
        var bid = cash / (quantity / Props.ONE_LOT)
        while (this.cash > 0) {
            sequence.add(bid)
            this.cash -= bid
            bid += step
        }
    }

    override fun placeBid(): Int {
        var bid = 0
        if (sequence.size > 0) {
            bid = sequence.removeFirst()
        }
        if (cash < bid) {
            bid = cash
            cash = 0
        } else {
            cash -= bid
        }
        return bid
    }

    override fun bids(own: Int, other: Int) {}

}
