public class CheeseFilling extends RollDecorator {
    private Roll roll;

    public CheeseFilling(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", cheese filling");
    }

    public double getCost(){
        return(roll.getCost() + 1);
    }
}
