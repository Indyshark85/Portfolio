// Alaska uses the NoTax strategy.
public class Alaska extends State{
    public Alaska(){
        this.name = "Alaska";
        this.taxBehavior = new NoTax();
    }
}
