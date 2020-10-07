public class FruitFilling extends RollDecorator {

    public FruitFilling(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Fruit filling");
    }

    public double getCost(){
        if (roll.getClass() == PastryRoll.class){
            return(roll.getCost() +.35);
        }
        else{
            return(roll.getCost() + .2);
        }
    }

}
