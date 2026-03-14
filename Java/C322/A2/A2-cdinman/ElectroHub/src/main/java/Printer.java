public class Printer implements Device{
    @Override
    public double getCost(){
        return 120.0;
    }

    @Override
    public String getDescription(){
        return "Printer";
    }
}
