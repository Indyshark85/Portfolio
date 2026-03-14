public class OvernightShipping extends DeviceDecorator{
    public OvernightShipping(Device device){
        super(device);
    }
    @Override
    public double getCost(){
        return super.getCost() + 5.0;
    }
    @Override
    public String getDescription(){
        return super.getDescription() + ", Overnight";
    }
}
