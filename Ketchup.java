public class Ketchup extends RollDecorator {

    public Ketchup(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Ketchup");
    }

    public double getCost(){
        return(roll.getCost() + 0.2);
    }

}
