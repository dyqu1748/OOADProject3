public class SoySauce extends RollDecorator{

    public SoySauce(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Soy sauce");
    }

    //Cost of soy sauce will vary between egg and spring rolls
    public double getCost(){
        if (roll.getClass() == EggRoll.class){
            return(roll.getCost() + 0.05);
        }
        else{
            return(roll.getCost() + 0.08);
        }
    }

}
