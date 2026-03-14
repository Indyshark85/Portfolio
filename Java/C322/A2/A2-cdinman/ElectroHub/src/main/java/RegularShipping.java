public class RegularShipping extends DeviceDecorator{
    public RegularShipping(Device device){
        super(device);
    }
    @Override
    public double getCost(){
        return super.getCost() + 10.0;
    }
    @Override
    public String getDescription(){
        return super.getDescription() + ", Regular Shipping";
    }

}
