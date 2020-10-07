public class ShrimpFilling extends RollDecorator {

    public ShrimpFilling(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Shrimp filling");
    }

    //Cost of shrimp filling will vary between egg and spring rolls
    public double getCost(){
        if (roll.getClass() == EggRoll.class){
            return(roll.getCost() + .75);
        }
        else{
            return(roll.getCost() + 1);
        }
    }

}
