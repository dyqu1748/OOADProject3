public class WhippedCream extends RollDecorator {
    private Roll roll;

    public WhippedCream(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", whipped cream");
    }

    public double getCost(){
        if (roll.getClass() == PastryRoll.class){
            return(roll.getCost() + 0.25);
        }
        else{
            return(roll.getCost() + 0.15);
        }
    }
}
