package auction.bots

import auction.Bidder
import auction.Props
import kotlin.math.max

/**
 * This is a test strategy for bidding
 */
class AnalyticStrategy : Bidder {
    private var otherPreviousBid = 0
    private var previousDeltaBid = 0
    private var currentQuantity = 0
    private var cash = 0
    private var otherCurrentCash = 0

    override fun init(quantity: Int, cash: Int) {
        this.cash = cash
        otherCurrentCash = cash
        currentQuantity = quantity
    }

    override fun placeBid(): Int {
        val bidder1CurrCash = cash - otherCurrentCash
        val bid1 = bidder1CurrCash / (currentQuantity / Props.ONE_LOT)
        val bid2 = otherPreviousBid + previousDeltaBid + 1
        var bid = max(bid1, bid2)
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
        otherCurrentCash -= other
        currentQuantity -= Props.ONE_LOT
        otherPreviousBid = other
        previousDeltaBid = if (other > own) other - own + 1 else 0
    }
}
