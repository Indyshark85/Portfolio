//Indiana uses the SevenPercent strategy.
public class Indiana extends State{
    public Indiana(){
        this.name = "Indiana";
        this.taxBehavior = new SevenPercent();
    }
}
