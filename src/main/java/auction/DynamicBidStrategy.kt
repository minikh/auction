package auction

import java.util.*
import kotlin.math.min

/**
 * This is the main strategy for bidding
 */
class DynamicBidStrategy : Bidder {

    private var rounds = 0
    private var cash = 0
    private val ownBids: MutableList<Int> = ArrayList()
    private val otherBids: MutableList<Int> = ArrayList()

    override fun init(quantity: Int, cash: Int) {
        rounds = quantity / Props.ONE_LOT
        this.cash = cash
    }

    override fun placeBid(): Int {
        val myMoney = ownMoney()
        val hisMoney = otherMoney()
        var baseBid = myMoney / remainRound()

        //todo think about how to calculate coefficients dynamically
        //these magic numbers has been found empirically
        baseBid = when {
            //if I have more money than another and I am winning, I can make the baseBid less
            myMoney > hisMoney && amIWinning() -> (baseBid * 0.7).toInt()
            //if I have more money than another and I am not winning, I can make the baseBid more
            myMoney > hisMoney && !amIWinning() -> (baseBid * 2.0).toInt()
            //if I have less money than another and I am winning, I can make the baseBid less
            myMoney < hisMoney && amIWinning() -> (baseBid * 0.6).toInt()
            //if I have less money than another and I am not winning, I can make the baseBid more less
            myMoney < hisMoney && !amIWinning() -> (baseBid * 0.2).toInt()
            else -> baseBid
        }
        val bid = min(myMoney, baseBid)
        if (Props.IS_DEBUG) {
            println(String.format("%s. cash = %s bid = %s", this.javaClass.simpleName, cash, bid))
        }
        return bid
    }

    override fun bids(own: Int, other: Int) {
        ownBids.add(own)
        otherBids.add(other)
    }

    private fun amIWinning(): Boolean {
        var index = 0
        var winCount = 0
        while (ownBids.size > index) {
            if (ownBids[index] > otherBids[index]) {
                winCount++
            } else {
                winCount--
            }
            index++
        }
        return winCount > 0
    }

    private fun ownMoney(): Int { //fixme here we can think about optimisation
        return cash - ownBids.sum()
    }

    private fun otherMoney(): Int { //fixme here we can think about optimisation
        return cash - otherBids.sum()
    }

    private fun remainRound(): Int {
        return rounds - ownBids.size
    }
}
