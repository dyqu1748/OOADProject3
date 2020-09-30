public class Sauce extends RollDecorator{
    private Roll roll;

    public Sauce(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", extra sauce");
    }

    public double getCost(){
        return(roll.getCost() + 0.2);
    }
}
