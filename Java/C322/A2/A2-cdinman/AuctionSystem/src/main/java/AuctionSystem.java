public class AuctionSystem {
    public static void main(String[] args) {
        //CREATE RAAAAAA
        Auctioneer auctioneer = new Auctioneer();

        InPersonBidder  inPersonBidder = new InPersonBidder();
        OnlineBidder onlineBidder = new OnlineBidder();
        PhoneBidder phoneBidder = new PhoneBidder();

        //time to take these to the BMV to register them
        auctioneer.registerObserver(inPersonBidder);
        auctioneer.registerObserver(onlineBidder);
        auctioneer.registerObserver(phoneBidder);

        double minBid = 500;

        //run for i rounds
        for(int i = 0; i<3; i++){
            auctioneer.setMinBid(minBid);

            double highest = Math.max(
                    inPersonBidder.getLastBid(),
                    Math.max(onlineBidder.getLastBid(),phoneBidder.getLastBid())
            );
            minBid = highest;
        }

        System.out.printf("The winning bid pays $%.2f%n", minBid);
    }
}
