import java.util.ArrayList;

public class BoulderRollStore extends RollStore {

    public BoulderRollStore(int maxStock, StockAnnouncer sa){
        String[] custTypes = {"Casual", "Business", "Catering"};
        String[] rollTypes = {"Spring", "Egg", "Pastry", "Sausage", "Jelly"};
        this.setMaxStock(maxStock);
        this.initDailyCashSales(custTypes);
        this.initDailyOutageImpacts(custTypes);
        this.initRollsSold(rollTypes);
        for (String type: rollTypes){
            this.setStock(type,maxStock);
        }
        this.addPropertyChangeListener(sa);
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
        //Add all requested extras to the roll provided
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
                default:
                    //Invalid extra type inputted. Print out error statement and do not add anything to the roll.
                    System.out.println("Invalid extra type");
            }
        }
        return roll;
    }
}
