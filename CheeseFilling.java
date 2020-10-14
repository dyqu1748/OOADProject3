public class CheeseFilling extends RollDecorator {

    public CheeseFilling(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Cheese filling");
    }

    public double getCost(){
        return(roll.getCost() + .60);
    }

}
