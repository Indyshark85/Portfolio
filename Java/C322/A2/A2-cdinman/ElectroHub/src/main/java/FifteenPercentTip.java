public class FifteenPercentTip extends DeviceDecorator{
    public FifteenPercentTip(Device device){
        super(device);
    }

    @Override
    public double getCost(){
        return super.getCost()*1.15;
    }

    @Override
    public String getDescription(){
        return super.getDescription() + ", 15% Tip";
    }
}
