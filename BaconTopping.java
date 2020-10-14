public class BaconTopping extends RollDecorator {

    public BaconTopping(Roll roll){
        this.roll = roll;
    }

    public String getDescription(){
        return (roll.getDescription() + ", Bacon topping");
    }

    public double getCost(){
        return(roll.getCost() + 1.25);
    }

}
