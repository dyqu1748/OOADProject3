public class ShrimpFilling extends RollDecorator {
    private Roll roll;

    public ShrimpFilling(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", shrimp filling");
    }

    public double getCost(){
        if (roll.getClass() == EggRoll.class){
            return(roll.getCost() + 1);
        }
        else{
            return(roll.getCost() + 1.25);
        }
    }
}
