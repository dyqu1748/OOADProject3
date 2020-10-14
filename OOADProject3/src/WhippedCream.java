public class WhippedCream extends RollDecorator {

    public WhippedCream(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Whipped cream");
    }

    //Cost of whipped cream will vary between jelly and pastry rolls.
    public double getCost(){
        if (roll.getClass() == PastryRoll.class){
            return(roll.getCost() + 0.25);
        }
        else{
            return(roll.getCost() + 0.18);
        }
    }

}
