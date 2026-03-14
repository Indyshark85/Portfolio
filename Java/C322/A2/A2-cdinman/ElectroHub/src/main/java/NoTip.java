public class NoTip extends DeviceDecorator{
    public NoTip(Device device){
        super(device);
    }

    @Override
    public String getDescription(){
        return super.getDescription() + ", No Tip";
    }
}
