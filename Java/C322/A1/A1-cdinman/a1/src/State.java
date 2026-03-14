//this is an abstract class that represents US states
public abstract class State {
    //name of state
    protected String name;

    //reference to the strategy obj
    protected SalesTaxBehavior taxBehavior;

    //return state name
    public String getName() {
        return name;
    }

    //set states name
    public void setName(String name) {
        this.name = name;
    }
    //Uses the strategy to compute and display the tax
    public void showTax(double value){
        double tax = taxBehavior.compute(value);
        System.out.printf("The sales tax on $%.2f in %s is $%.2f.%n", value, name, tax);
    }
}
