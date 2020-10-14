//Implement decorator pattern in the form of RollDecorator. RollDecorator will allow us to add more toppings to existing rolls (add new behavior).
public abstract class RollDecorator extends Roll {
    //Have roll as a protected attribute so that only the decorators can modify it
    protected Roll roll;

    public abstract String getDescription();

}
