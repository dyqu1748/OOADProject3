public class MintTopping extends RollDecorator{

    public MintTopping(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Mint topping");
    }

    public double getCost(){
        if (roll.getClass() == EggRoll.class){
            return(roll.getCost() + 0.4);
        }
        else{
            return(roll.getCost() + 0.5);
        }
    }

}
