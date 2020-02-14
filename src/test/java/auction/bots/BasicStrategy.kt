package auction.bots

import auction.Bidder
import auction.Props
import kotlin.math.max

/**
 * This is a test strategy for bidding
 */
class BasicStrategy(private val step: Int) : Bidder {

    private var otherPreviousBid = 0
    private var ownPreviousBid = 0
    private var previousDeltaBid = 0
    private var cash = 0

    override fun init(quantity: Int, cash: Int) {
        this.cash = cash
    }

    override fun placeBid(): Int {
        var bid = max(otherPreviousBid, ownPreviousBid) + previousDeltaBid + step
        if (cash < bid) {
            bid = cash
            cash = 0
        } else {
            cash -= bid
        }
        if (Props.IS_DEBUG) {
            println(String.format("%s. cash = %s bid = %s", this.javaClass.simpleName, cash, bid))
        }
        return bid
    }

    override fun bids(own: Int, other: Int) {
        ownPreviousBid = own
        otherPreviousBid = other
        previousDeltaBid = if (other > own) other - own + 1 else 0
    }
}
