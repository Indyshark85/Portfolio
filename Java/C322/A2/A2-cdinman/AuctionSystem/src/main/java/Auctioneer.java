import java.util.ArrayList;
import java.util.List;

public class Auctioneer implements Subject{
    private List<Observer> observers = new ArrayList<>();
    private double minBid;

    public void setMinBid(double value){
        this.minBid = value;
        notifyObservers();
    }

    public double getMinBid() {
        return minBid;
    }

    @Override
    public void registerObserver(Observer o){
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o){
        observers.remove(o);
    }

    @Override
    public void notifyObservers(){
        for (Observer o: observers){
            o.update(minBid);
        }
    }
}
