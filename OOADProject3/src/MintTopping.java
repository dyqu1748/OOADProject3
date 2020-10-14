public class MintTopping extends RollDecorator{

    public MintTopping(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Mint topping");
    }

    //Mint price will vary between egg and spring rolls
    public double getCost(){
        if (roll.getClass() == EggRoll.class){
            return(roll.getCost() + 0.10);
        }
        else{
            return(roll.getCost() + 0.15);
        }
    }

}
