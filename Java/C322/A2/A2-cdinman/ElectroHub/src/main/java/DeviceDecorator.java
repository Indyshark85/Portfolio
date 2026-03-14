public abstract class DeviceDecorator implements Device{
    protected Device device;

    public DeviceDecorator(Device device){
        this.device = device;
    }

    @Override
    public double getCost(){
        return device.getCost();
    }

    @Override
    public String getDescription(){
        return device.getDescription();
    }

}
