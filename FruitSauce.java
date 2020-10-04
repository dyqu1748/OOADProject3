public class FruitSauce extends RollDecorator{
    private Roll roll;

    public FruitSauce(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", fruit sauce");
    }

    public double getCost(){
        if (roll.getClass() == PastryRoll.class){
            return(roll.getCost() + 0.2);
        }
        else{
            return(roll.getCost() + 0.1);
        }
    }
}
