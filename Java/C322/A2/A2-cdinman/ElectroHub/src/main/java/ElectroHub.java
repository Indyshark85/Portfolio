public class ElectroHub {
    public static void main(String[] args) {
        if (args.length < 1) return ;

        Device order = null;

        String deviceName = "";

        //device
        switch (args[0]){
            case "screen":
                order = new Screen();
                deviceName = "Screen";
                break;
            case "printer":
                order = new Printer();
                deviceName = "Printer";
                break;
            case "keyboard":
                order = new Keyboard();
                deviceName = "Keyboard";
                break;
        }
        StringBuilder description = new StringBuilder(deviceName);

        //process those pesky middle args
        int i =1;
        while (i<args.length && !args[i].contains("tip")){
            switch (args[i]){
                case "local_pickup":
                    description.append(", Local pickup");
                    break;
                case "regular_shipping":
                    order= new RegularShipping(order);
                    description.append(", Regular shipping");
                    break;
                case "overnight":
                    order = new OvernightShipping(order);
                    description.append(", Overnight");
                    break;
            }
            i++;
        }

        //tips stuffs
        if (i < args.length){
            switch (args[i]){
                case "no_tip":
                    order = new NoTip(order);
                    description.append(", No tip");
                    break;
                case "10_tip":
                    order = new TenPercentTip(order);
                    description.append(", 10% tip");
                    break;
                case "15_tip":
                    order = new FifteenPercentTip(order);
                    description.append(", 15% tip");
                    break;
            }
        }

        double total = order.getCost();
        System.out.printf(
                "Total cost for %s is $%.2f.%n",
                description.toString(), total);

    }
}
