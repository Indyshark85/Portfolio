public class InPersonBidder extends Bidder{
    @Override
    protected double calculateBid(double minBid){
        return minBid * 1.5;
    }
}
