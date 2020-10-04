public class Ketchup extends RollDecorator {
    private Roll roll;

    public Ketchup(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", ketchup");
    }

    public double getCost(){
        return(roll.getCost() + 0.2);
    }
}
