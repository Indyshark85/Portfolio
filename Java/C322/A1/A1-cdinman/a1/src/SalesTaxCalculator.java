//Main application class.
//This class reads command-line arguments and selects the correct state strategy.
public class SalesTaxCalculator {
    public static void main(String[] args) {

        // Check that the correct number of arguments is provided
        if (args.length < 2) {
            System.out.println("Usage: java SalesTaxCalculator <State> <Amount>");
            return;
        }

        //Read state name and sale amount from arguments
        String stateName = args[0];
        double amount = Double.parseDouble(args[1]);

        //Reference to the state object
        State state;

        //Choose the correct state based on input
        if (stateName.equalsIgnoreCase("Indiana")) {
            state = new Indiana();
        } else if (stateName.equalsIgnoreCase("Alaska")) {
            state = new Alaska();
        } else {
            System.out.println("Invalid state");
            return;
        }

        //Display the sales tax
        state.showTax(amount);
    }
}
