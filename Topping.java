public class Topping extends RollDecorator {
    private Roll roll;

    public Topping(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", extra filling");
    }

    public double getCost(){
        return(roll.getCost() + .5);
    }
}
