public class SoySauce extends RollDecorator{

    public SoySauce(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Soy sauce");
    }

    public double getCost(){
        if (roll.getClass() == EggRoll.class){
            return(roll.getCost() + 0.15);
        }
        else{
            return(roll.getCost() + 0.25);
        }
    }

}
