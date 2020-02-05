package auction.strategy;

public class Position {
    private int bid1;
    private int bid2;

    public Position(int bid1, int bid2) {
        this.bid1 = bid1;
        this.bid2 = bid2;
    }

    public int getBid1() {
        return bid1;
    }

    public int getBid2() {
        return bid2;
    }
}


