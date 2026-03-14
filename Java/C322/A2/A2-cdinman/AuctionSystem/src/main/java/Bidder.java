public abstract class Bidder  implements Observer{
    protected double lastBid;

    public double getLastBid() {
        return lastBid;
    }

    protected abstract double calculateBid(double minBid);

    @Override
    public void update(double minBid){
        lastBid = calculateBid(minBid);
    }
}
