public class TenPercentTip extends DeviceDecorator{
    public TenPercentTip(Device device){
        super(device);
    }

    @Override
    public double getCost() {
        return super.getCost()*1.10;
    }

    @Override
    public String getDescription(){
        return super.getDescription() + ", 10% tip";
    }
}
