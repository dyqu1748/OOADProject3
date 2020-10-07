public class FruitSauce extends RollDecorator{

    public FruitSauce(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Fruit sauce");
    }

    public double getCost(){
        if (roll.getClass() == PastryRoll.class){
            return(roll.getCost() + 0.04);
        }
        else{
            return(roll.getCost() + 0.09);
        }
    }

}
