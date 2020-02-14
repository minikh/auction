package auction

class Auction(private val quantityUnits: Int, private val cash: Int) {
    private lateinit var auctioneer1: Auctioneer
    private lateinit var auctioneer2: Auctioneer

    fun setRealStrategy(strategy: Bidder) {
        auctioneer1 = Auctioneer(strategy, quantityUnits, cash)
    }

    fun setTestStrategy(strategy: Bidder) {
        auctioneer2 = Auctioneer(strategy, quantityUnits, cash)
    }

    fun runGame() {
        var remains = quantityUnits
        while (remains > 0) {
            doBids()
            remains -= Props.ONE_LOT
        }
    }

    private fun doBids() {
        val bid1 = auctioneer1.bid
        val bid2 = auctioneer2.bid
        when {
            bid1 > bid2 -> auctioneer1.addUnit(Props.ONE_LOT)
            bid2 > bid1 -> auctioneer2.addUnit(Props.ONE_LOT)
            else -> {
                auctioneer1.addUnit(Props.ONE_LOT / 2)
                auctioneer2.addUnit(Props.ONE_LOT / 2)
            }
        }
        auctioneer1.bids(bid1, bid2)
        auctioneer2.bids(bid2, bid1)
        printResult()
    }

    fun whoWon(): Result {
        return when {
            auctioneer1.currentLotsCount > auctioneer2.currentLotsCount -> Result.REAL_BOT
            auctioneer1.currentLotsCount < auctioneer2.currentLotsCount -> Result.TEST_BOT
            auctioneer1.spentCash < auctioneer2.spentCash -> Result.REAL_BOT
            auctioneer1.spentCash > auctioneer2.spentCash -> Result.TEST_BOT
            else -> Result.TIE
        }
    }

    private fun printResult() {
        if (Props.IS_DEBUG) {
            val msg =
                "Result: ${auctioneer1.name()} : ${auctioneer2.name()} | ${auctioneer1.currentLotsCount} : ${auctioneer2.currentLotsCount}"
            println(msg)
        }
    }

    private class Auctioneer(private val strategy: Bidder, quantity: Int, private val startBalance: Int) {
        var currentLotsCount = 0
            private set
        var spentCash = 0
            private set

        fun addUnit(count: Int) {
            currentLotsCount += count
        }

        val bid: Int
            get() {
                val bid = strategy.placeBid()
                spendCash(bid)
                if (Props.IS_DEBUG) {
                    val msg = "${name()}. cash remain = ${startBalance - spentCash} = $bid"
                    println(msg)
                }
                return bid
            }

        fun bids(bid1: Int, bid2: Int) {
            strategy.bids(bid1, bid2)
        }

        fun name(): String = strategy.javaClass.simpleName

        private fun spendCash(cash: Int) {
            spentCash += cash
        }

        init {
            strategy.init(quantity, startBalance)
        }
    }

}
