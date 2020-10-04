public class FruitFilling extends RollDecorator {
    private Roll roll;

    public FruitFilling(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", fruit filling");
    }

    public double getCost(){
        if (roll.getClass() == PastryRoll.class){
            return(roll.getCost() +1.25);
        }
        else{
            return(roll.getCost() + .75);
        }
    }
}
