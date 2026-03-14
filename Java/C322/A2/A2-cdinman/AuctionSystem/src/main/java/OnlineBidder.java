public class OnlineBidder extends Bidder{
    @Override
    protected double calculateBid(double minBid){
        return minBid * 2.0;
    }

}
