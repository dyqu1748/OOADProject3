public class BaconTopping extends RollDecorator {
    private Roll roll;

    public BaconTopping(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", bacon topping");
    }

    public double getCost(){
        return(roll.getCost() + .5);
    }
}
