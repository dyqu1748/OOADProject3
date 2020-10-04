import java.util.ArrayList;

public class BoulderRollStore extends RollStore {

    public BoulderRollStore(int maxStock){
        String[] rollTypes = {"Spring", "Egg", "Pastry", "Sausage", "Jelly"};
        this.setMaxStock(maxStock);
        for (String type: rollTypes){
            this.setStock(type,maxStock);
        }
        this.openStore();
    }

    public Roll createRoll(String type){
        //Create the roll of the requested type.
        switch (type) {
            case "Spring":
                return new SpringRoll();
            case "Egg":
                return new EggRoll();
            case "Pastry":
                return new PastryRoll();
            case "Sausage":
                return new SausageRoll();
            case "Jelly":
                return new JellyRoll();
            default:
                //Invalid roll type inputted. Print out error statement and return null.
                System.out.println("Invalid roll type");
                return null;
        }
    }

    public Roll addExtra(Roll roll, ArrayList<String> extra){
        //Add requested extras to the roll
        for (String ex: extra){
            switch(ex){
                case "SoySauce":
                    roll = new SoySauce(roll);
                    break;
                case "FruitSauce":
                    roll = new FruitSauce(roll);
                    break;
                case "Ketchup":
                    roll = new Ketchup(roll);
                    break;
                case "ShrimpFilling":
                    roll = new ShrimpFilling(roll);
                    break;
                case "FruitFilling":
                    roll = new FruitFilling(roll);
                    break;
                case "CheeseFilling":
                    roll = new CheeseFilling(roll);
                    break;
                case "MintTopping":
                    roll = new MintTopping(roll);
                    break;
                case "WhippedCream":
                    roll = new WhippedCream(roll);
                    break;
                case "BaconTopping":
                    roll = new BaconTopping(roll);
                    break;
            }
        }
        return roll;
    }
}
