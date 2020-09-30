public class Filling extends RollDecorator {
    private Roll roll;

    public Filling(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", extra filling");
    }

    public double getCost(){
        return(roll.getCost() + 1);
    }
}
